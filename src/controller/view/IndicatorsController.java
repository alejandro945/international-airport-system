package controller.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import controller.DashboardController;

public class IndicatorsController {

    @FXML
    private BarChart<?, ?> bar;

    @FXML
    private TableView<?> migrationTbl;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> aprprovedCol;

    @FXML
    private TableColumn<?, ?> wantedCol;

    @FXML
    private TableColumn<?, ?> minorCol;

    @FXML
    private TableColumn<?, ?> covidCol;

    @FXML
    private TableColumn<?, ?> actionCol;

    private final DashboardController dController;

    public IndicatorsController(DashboardController dController) {
        this.dController = dController;
    }

    public DashboardController getDController() {
        return dController;
    }

    @FXML
    void exportInfo(ActionEvent event) {

    }

    @FXML
    void importInfo(ActionEvent event) {

    }

}
