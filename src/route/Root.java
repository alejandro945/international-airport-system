package route;

public enum Root {
    BAR("/ui/screens/bars/"), AIRLINE("/ui/screens/views/airline-admin/"), AIRPORT("/ui/screens/views/airport-admin/"),
    CLIENT("/ui/screens/views/client/"), TOWER("/ui/screens/views/control-tower-supervisor/"),
    GLOBAL("/ui/screens/views/global/"), MIGRATION("/ui/screens/views/migration-agent/");

    private String root;

    private Root(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }
}
