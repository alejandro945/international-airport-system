package controller.view;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MapController {

    private final DashboardController dController;

    public MapController(DashboardController dController) {
        this.dController = dController;
    }

    @FXML
    private ImageView map;

    @FXML
    private ImageView plane;
    
  
}
