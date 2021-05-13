package controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OAuthAuthenticator {
    private JSONObject accessedJsonData;

    private boolean gotData = false;

    private String accessToken;
    private String accessCode;

    private String clientID;
    private String redirectUri;
    private String clientSecret;

    private Stage stage;

    public OAuthAuthenticator(String clientID, String redirectUri, String clientSecret) {
        this.clientID = clientID;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
    }

    private String GOOGLE_apiScope = "https://www.googleapis.com/auth/userinfo.profile";

    String getWebUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?scope=" + GOOGLE_apiScope
                + "&access_type=offline&redirect_uri=" + getRedirectUri() + "&response_type=code&client_id="
                + getClientID();
    }

    String getApiTokenUrl() {
        return "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + getAccessToken();
    }

    String getApiAccessUrl() {
        return "https://www.googleapis.com/oauth2/v4/token";
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    String getApiAccessParams() {
        return "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret="
                + getClientSecret() + "&grant_type=authorization_code&code=" + getAccessCode();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public boolean hasFinishedSuccessfully() {
        return gotData;
    }

    public JSONObject getJsonData() {
        if (gotData) {
            return accessedJsonData;
        } else {
            return null;

        }
    }

    private void closeStage() {
        stage.close();
    }

    private void notifyLoginViewCompleted() {
        if (gotData) {
            System.out.println("logueado");
        }
    }

    public void startLogin() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        WebView root = new WebView();
        WebEngine engine = root.getEngine();
        engine.load(getWebUrl());
        engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            public void handle(WebEvent<String> event) {
                WebEngine we = (WebEngine) event.getSource();
                closeStage();
                String location = we.getLocation();
                if (location.contains("code") && location.startsWith(getRedirectUri())) {
                    gotData = true;
                    accessCode = location.substring(location.indexOf("code=") + 5);
                    accessToken = doGetAccessTokenRequest(accessCode);
                    String returnedJson = doGetAccountInfo(accessToken);
                    accessedJsonData = new JSONObject(returnedJson);
                    System.out.println(returnedJson);
                    notifyLoginViewCompleted();
                }
            }
        });
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String doGetAccountInfo(String accessToken) {
        try {
            HttpURLConnection connection2 = null;
            URL url2 = new URL(getApiTokenUrl());
            connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection2.setDoInput(true);
            connection2.setDoOutput(true);

            int reponseCode2 = connection2.getResponseCode();

            if (reponseCode2 == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in2 = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();
                while ((inputLine2 = in2.readLine()) != null) {
                    if (inputLine2.length() > 8) {
                        String[] render = inputLine2.split(" ");
                        if (render[2].equals("\"id\"" + ":") || render[2].equals("\"given_name\"" + ":")
                                || render[2].equals("\"family_name\"" + ":") || render[2].equals("\"picture\"" + ":")) {
                            String redux = render[3].replaceAll("\"", "");
                            System.out.println(redux.substring(0, redux.length() - 1));
                        }
                    }
                    response2.append(inputLine2);
                }
                in2.close();
                connection2.disconnect();
                return response2.toString();
            } else {
                System.out.println("Error retrieving api data!: " + reponseCode2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String doGetAccessTokenRequest(String code) {
        try {
            URL url = new URL(getApiAccessUrl());
            String urlParams = getApiAccessParams();

            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + postDataLength);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);
            wr.flush();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Error getting access token for OAuth Login!");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            String fullResponse = response.toString();
            JSONObject json = new JSONObject(fullResponse);
            String accessToken = json.getString("access_token");
            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
