package controller.view;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedAreaChart;
import controller.DashboardController;

public class IndicatorsController {

    @FXML
    private BarChart<?, ?> bar;

    @FXML
    private LineChart<?, ?> line;

    @FXML
    private AreaChart<?, ?> Area;

    @FXML
    private StackedAreaChart<?, ?> stacked;
    
    private final DashboardController dController;

    public IndicatorsController(DashboardController dController) {
        this.dController = dController;
    }

}
