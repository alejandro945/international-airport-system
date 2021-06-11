package route;

public enum Root {
    BAR("/ui/screens/bars/"), CLIENT("/ui/screens/views/client/"),
    TOWER("/ui/screens/views/control-tower-supervisor/"), MIGRATION("/ui/screens/views/migration-agent/"),
    USER("/ui/screens/views/"), IMG("src/ui/assets/img/"), CRUD("/ui/screens/cruds/"),
    STYLE_GRID("/ui/assets/styles/vendor.css");

    private String root;

    private Root(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }
}
