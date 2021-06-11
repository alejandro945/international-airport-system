package controller.crud;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.Constant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
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
    private TableColumn<?, ?> reportCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private Button btnEdit;

    @FXML
    private Label modalName;

    @FXML
    private Button btnSave;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtLast;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXComboBox<String> cbType;

    @FXML
    private TableColumn<Collaborator, String> actionsCol;

    private Airport airport;
    private DashboardController dController;
    private Airline airline;
    private Collaborator selected;
    private Stage modal;

    public AirlineEmployeesController(Airport airport, DashboardController dController, Airline airline) {
        this.airport = airport;
        this.dController = dController;
        this.airline = airline;
    }

    @FXML
    public void newEmployee(ActionEvent event) throws IOException {
        if (modal == null) {
            showModal();
            modalName.setText("Create Employee");
            btnEdit.setVisible(false);
            btnSave.setVisible(true);
        }
    }

    public void setModal(Stage modal) {
        this.modal = modal;

    }

    @FXML
    public void cancelModal(ActionEvent event) {
        modal.close();
        setModal(null);
    }

    @FXML
    public void exportInfo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File selectedFile = fc.showSaveDialog(null);
        if (selectedFile != null) {
            dController.alert(Route.SUCCESS, Constant.EXPORT_SUCCESS);
            try {
                airline.exportDataEmployees(selectedFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                dController.alert(Route.ERROR, Constant.FILE_NOT_FOUND);
            }
        }
    }

    @FXML
    public void importInfo(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Resource File");
        File selectedFile = fc.showOpenDialog(null);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));
        if (selectedFile != null) {
            dController.alert(Route.SUCCESS, Constant.IMPORT_SUCCESS);
            try {
                airline.importDataEmployees(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                dController.alert(Route.WARNING, Constant.IOEXCEPTION);
            }
            airport.saveData();
            airport.loadData();
            getData();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    @FXML
    public void editEmployee(ActionEvent event) {
        String type = "";
        if (validateFields()) {
            selected.setName(txtName.getText());
            selected.setLastName(txtLast.getText());
            selected.setId(Long.parseLong(txtId.getText()));
            if (selected instanceof Pilot) {
                type = "Pilot";
            } else {
                type = "Advisor";
            }
            dController.geAirportController().createAlert(type + " was successfully edited.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    @FXML
    public void saveEmployee(ActionEvent event) {
        String type = "";
        if (validateFields()) {
            if (cbType.getValue().equals("Pilot")) {
                airline.getPilots()
                        .add(new Pilot(txtName.getText(), txtLast.getText(), Long.parseLong(txtId.getText()), airline));
                type = "Pilot";
            } else {
                airline.addAdvisor(
                        new Advisor(txtName.getText(), txtLast.getText(), Long.parseLong(txtId.getText()), airline));
                type = "Advisor";
            }
            dController.geAirportController().createAlert(type + " was successfully added.", Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
    }

    private void getData() {
        ObservableList<Collaborator> employees = FXCollections.observableArrayList(airline.getEmployees());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        renderActions();
        employeesTbl.setItems(employees);
    }

    public boolean validateFields() {
        boolean render = true;
        if (txtName.getText().isEmpty() || txtLast.getText().isEmpty() || txtId.getText().isEmpty()
                || cbType.getSelectionModel().getSelectedItem() == null) {
            render = false;
        }
        return render;
    }

    public void initTypes() {
        cbType.getItems().clear();
        cbType.getItems().addAll("Pilot", "Advisor");
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
                            String type = "";
                            selected = (Collaborator) getTableRow().getItem();
                            if (selected instanceof Pilot) {
                                airline.getPilots().remove((Pilot) selected);
                                type = "Pilot";
                            } else if (selected instanceof Advisor) {
                                airline.removeAdvisor((Advisor) selected);
                                type = "Advisor";
                            }
                            dController.geAirportController().createAlert(type + " was removed successfully.",
                                    Route.SUCCESS);
                            getData();
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (Collaborator) getTableRow().getItem();
                            try {
                                showModal();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // modalName.setText("Edit User");
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
        Stage stage = dController.loadModal(Route.AL_EMPLOYEES_MODAL, this);
        setModal(stage);
        stage.show();
        initTypes();
    }

    public void prepareEdition(Collaborator selected) {
        txtName.setText(selected.getName());
        txtLast.setText(selected.getLastName());
        txtId.setText(String.valueOf(selected.getId()));
        cbType.setValue(selected.getType());
        cbType.setDisable(true);
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }
}
