package controller.crud;

import controller.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Airline;
import model.Airport;
import model.Collaborator;
import model.Pilot;
import route.Route;

public class AirlineEmployeesController implements Initializable {

    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<Collaborator> employeesTbl;

    @FXML
    private TableColumn<?, ?> firstNameCol;

    @FXML
    private TableColumn<?, ?> lastNameCol;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<Collaborator, String> actionsCol;

    private Airport airport;
    private DashboardController dController;
    private Airline airline;
    private Collaborator selected;
    private Stage modal;

    public AirlineEmployeesController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
        this.airline = airline;
    }

    @FXML
    void newEmployee(ActionEvent event) throws IOException {
        Stage stage = dController.loadModal(Route.AL_EMPLOYEES, this);
        setModal(stage);
        stage.show();
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    void cancelModal(ActionEvent event) {
        modal.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    @FXML
    void editEmployee(ActionEvent event) {
        if (validateFields()) {
            /*selected.setName(txt.getText());
            selected.setPlaneWeight(Integer.parseInt(txtWeight.getText()));
            selected.setCapacity(Integer.parseInt(txtCapacity.getText()));*/
            dController.geAirportController().createAlert("Employee was successfully edited.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    void saveEmployee(ActionEvent event) {
        if (validateFields()) {
            //airline.getAircraft().add(new Collaborator(txtCode.getText(), Integer.parseInt(txtWeight.getText()), Integer.parseInt(txtCapacity.getText())));
            dController.geAirportController().createAlert("Collaborator was successfully added.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    private void getData() {
        ObservableList<Collaborator> employees = FXCollections.observableArrayList(airline.getEmployees());
        /*codeCol.setCellValueFactory(new PropertyValueFactory<>("planeCode"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("planeWeight"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));*/
        renderActions();
        employeesTbl.setItems(employees);
    }

    public boolean validateFields() {
        boolean render = true;
        /*if (txtCapacity.getText().isEmpty() || txtWeight.getText().isEmpty() || txtCode.getText().isEmpty()) {
            render = false;
        }*/
        return render;
    }

    private void renderActions() {
        Callback<TableColumn<Collaborator, String>, TableCell<Collaborator, String>> cellFact = (
                TableColumn<Collaborator, String> param) -> {
            final TableCell<Collaborator, String> cell = new TableCell<Collaborator, String>() {
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
                            selected = (Collaborator) getTableRow().getItem();
                            if(selected instanceof Pilot) {
                                airline.getPilots().remove(selected);
                            } else {
                                // Remove advisor from binary tree.
                            }
                            dController.geAirportController().createAlert("Employee was removed successfully.", Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (Collaborator) getTableRow().getItem();
                            try {
                                showModal();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //modalName.setText("Edit User");
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
        Stage stage = dController.loadModal(Route.AIRCRAFT_MODAL, this);
        setModal(stage);
        stage.show();
    }

    public void prepareEdition(Collaborator selected) {
        /*txtCode.setText(selected.getPlaneCode());
        txtWeight.setText(String.valueOf(selected.getPlaneWeight()));
        txtCapacity.setText(String.valueOf(selected.getCapacity()));
        btnEdit.setVisible(true);
        btnSave.setVisible(false);*/
    }
}
