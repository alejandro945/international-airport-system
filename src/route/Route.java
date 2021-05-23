package route;

public enum Route {

    LOGIN("/ui/screens/login.fxml"), DASHBOARD("/ui/screens/dashboard.fxml"), MAINPANE("screens/mainPane.fxml"),
    STYLES("assets/styles/vendor.css"),
    ICON("C:/Users/alejo/Desktop/APO II/TAREAS INTEGRADORAS/international-airport-system/src/ui/assets/img/ico.jpeg");

    private String route;

    private Route(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
