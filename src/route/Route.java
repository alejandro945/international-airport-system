package route;

public enum Route {

    LOGIN("/ui/screens/login.fxml"), DASHBOARD("/ui/screens/dashboard.fxml"), MAINPANE("screens/mainPane.fxml"),
    STYLES("assets/styles/vendor.css"), ICON("src/ui/assets/img/ico.jpeg"), ALERT("/ui/assets/styles/vendor.css"),
    SPINNER("/ui/screens/spinner.fxml"), SUCCESS("/ui/assets/img/success.gif"), ERROR("/ui/assets/img/error.gif"),
    WARNING("/ui/assets/img/warning.gif");

    private String route;

    private Route(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
