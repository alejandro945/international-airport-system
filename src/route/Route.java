package route;

public enum Route {

    SPLASH("/ui/screens/splash.fxml"), LOGIN("/ui/screens/login.fxml"), DASHBOARD("/ui/screens/dash.fxml"),
    MAINPANE("screens/mainPane.fxml"), ICON("src/ui/assets/img/ico.jpeg"), ALERT("/ui/assets/styles/vendor.css"),
    SPINNER("/ui/screens/spinner.fxml"), SUCCESS("/ui/assets/img/success.gif"), ERROR("/ui/assets/img/error.gif"),
    WARNING("/ui/assets/img/warning.gif"), USER_ICON("/ui/assets/img/icons/user.png"),
    AIRLINE_ADMIN(Root.BAR.getRoot() + "bar-airline.admin.fxml"),
    AIRPORT_ADMIN(Root.BAR.getRoot() + "bar-airport-admin.fxml"),
    CT_SUPERVISOR(Root.BAR.getRoot() + "bar-ct-supervisor.fxml"),
    MIGRATION_AGENT(Root.BAR.getRoot() + "bar-migration-agent.fxml"),
    COSTUMER(Root.BAR.getRoot() + "bar-costumer.fxml"), ACTIVE_FLIGHTS(Root.TOWER.getRoot() + "active-flights.fxml"),
    NEW_MAINTENANCE(Root.TOWER.getRoot() + "new-maintenance.fxml"),
    FLIGHTS_STATUS(Root.TOWER.getRoot() + "status.fxml"),
    UPCOMING_FLIGHTS(Root.TOWER.getRoot() + "upcoming-flights.fxml"),
    AIRLINE_EMPLOYEES(Root.AIRLINE.getRoot() + "airline-employees.fxml"),
    FLIGHTS(Root.AIRLINE.getRoot() + "flights.fxml"), AIRLINES(Root.AIRPORT.getRoot() + "airlines.fxml"),
    AP_DASH(Root.AIRPORT.getRoot() + "dashboard.fxml"), AP_EMPLOYEES(Root.AIRPORT.getRoot() + "employees.fxml"),
    NEW_AIRLINE(Root.AIRPORT.getRoot() + "new-airline.fxml"), MY_TRIPS(Root.CLIENT.getRoot() + "my-flights.fxml"),
    NEW_TRIP(Root.CLIENT.getRoot() + "new-flight.fxml"), PLAN_FLIGHT(Root.CLIENT.getRoot() + "plan-flight.fxml"),
    PROFILE_SETTING(Root.CLIENT.getRoot() + "profile-settings.fxml"),
    INDICATORS(Root.MIGRATION.getRoot() + "indicators.fxml"), NEW_EMPLOYEE(Root.GLOBAL.getRoot() + "new-employee.fxml"),
    PROFILE(Root.USER.getRoot() + "profile.fxml"), CARD(Root.CARD.getRoot() + "flight.fxml");

    private String route;

    private Route(String route) {
        this.route = route;
    }

    public String getRoute() {
        return route;
    }
}
