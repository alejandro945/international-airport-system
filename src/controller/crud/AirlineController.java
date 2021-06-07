package controller.crud;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import controller.Constant;
import controller.DashboardController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Airline;
import model.Airport;
import route.Route;

public class AirlineController implements Initializable {
    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtId;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<Airline> airlineTbl;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> logoCol;

    @FXML
    private TableColumn<Airline, String> actionsCol;

    private Airport airport;
    private DashboardController dController;
    private Airline selected;
    private Stage modal;

    public AirlineController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @FXML
    void newAirline(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.AIRLINE_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    void addLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Archivo");
        File file = fileChooser.showOpenDialog(txtName.getScene().getWindow());
    }

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    void editAirline(ActionEvent event) {
        if (validateFields()) {
            selected.setAirlineName(txtName.getText());
            dController.geAirportController().createAlert("Airline was successfully edited.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    void saveAirline(ActionEvent event) {
        if (validateFields()) {
            airport.getAirlines().add(new Airline(txtName.getText(), "RUTA DEL LOGO"));
            dController.geAirportController().createAlert("Airline was successfully added.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    private void getData() {
        ObservableList<Airline> airlines = FXCollections.observableArrayList(airport.getAirlines());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        logoCol.setCellValueFactory(new PropertyValueFactory<>("logo"));
        renderActions();
        airlineTbl.setItems(airlines);
    }

    private void renderActions() {
        Callback<TableColumn<Airline, String>, TableCell<Airline, String>> cellFact = (
                TableColumn<Airline, String> param) -> {
            final TableCell<Airline, String> cell = new TableCell<Airline, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Button delete = new Button("Delete");
                        delete.setId("delete");
                        Button edit = new Button("Edit");
                        edit.setId("edit");
                        delete.getStylesheets().add(Route.CRUD.getRoute());
                        edit.getStylesheets().add(Route.CRUD.getRoute());
                        delete.setOnAction((ActionEvent event) -> {
                            selected = (Airline) getTableRow().getItem();
                            airport.getAirlines().remove(selected);
                            dController.geAirportController().createAlert("Airline was deleted successfully.", Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (Airline) getTableRow().getItem();
                            try {
                                showModal();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            modalName.setText("Edit User");
                            prepareEdition(selected);
                        });
                        HBox managebtn = new HBox(edit, delete);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(delete, new Insets(2, 2, 0, 3));
                        HBox.setMargin(edit, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionsCol.setCellFactory(cellFact);
    }

    public void showModal() throws IOException {
        Stage stage = dController.loadModal(Route.AIRLINE_MODAL, this);
        setModal(stage);
        stage.show();
    }
    
    private boolean validateFields() {
        boolean render = true;
        if (txtName.getText().isEmpty()) {
            render = false;
        }
        return render;
    }
    
    public void prepareEdition(Airline selected) {
        txtName.setText(selected.getAirlineName());
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }
}
