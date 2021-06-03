package controller.crud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import controller.Constant;
import controller.DashboardController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import model.*;
import route.Route;

public class UserController implements Initializable {

    @FXML
    private TableView<User> userTbl;

    @FXML
    private TableColumn<User, Long> idCol;

    @FXML
    private TableColumn<User, String> nameCol;

    @FXML
    private TableColumn<User, String> lastCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, UserRole> roleCol;

    @FXML
    private TableColumn<User, String> actionsCol;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtLast;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private JFXComboBox<UserRole> txtRole;

    @FXML
    private JFXComboBox<Airline> txtAirline;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnSave;
    @FXML
    private Label modalName;

    private Airport airport;
    private DashboardController dController;
    private User selected;
    private Stage modal;

    public UserController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }

    @FXML
    public void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    public void saveUser(ActionEvent event) {
        userAction(1);
    }

    @FXML
    public void editUser(ActionEvent event) {
        userAction(2);
    }

    @FXML
    public void isAirline(ActionEvent event) {
        if (txtRole.getValue() == UserRole.AIRLINE_ADMIN) {
            initAirlines();
            txtAirline.setVisible(true);
        } else {
            txtAirline.setVisible(false);
        }
    }

    @FXML
    public void newUser(ActionEvent event) throws IOException {
        showModal();
        modalName.setText("Create User");
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    public void showModal() throws IOException {
        Stage stage = dController.loadModal(Route.USER_MODAL, this);
        setModal(stage);
        stage.show();
        initRoles();
    }

    public void getData() {
        ObservableList<User> users = FXCollections.observableArrayList(airport.getUsers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        renderActions();
        userTbl.setItems(users);
    }

    private void renderActions() {
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFact = (TableColumn<User, String> param) -> {
            final TableCell<User, String> cell = new TableCell<User, String>() {
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
                            selected = (User) getTableRow().getItem();
                            dController.geAirportController().createAlert(airport.deleteUser(selected), Route.SUCCESS);
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = (User) getTableRow().getItem();
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

    public void prepareEdition(User selected) {
        txtId.setText(String.valueOf(selected.getId()));
        txtName.setText(selected.getName());
        txtLast.setText(selected.getLastName());
        txtEmail.setText(selected.getEmail());
        txtRole.setValue(selected.getRole());
        txtRole.setDisable(true);
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
        if (selected instanceof AirlineUser) {
            AirlineUser aUser = (AirlineUser) selected;
            txtAirline.setVisible(true);
            txtAirline.setValue(aUser.getAirline());
        }
    }

    public void userAction(int option) {
        String msg = "";
        if (validateFields() && txtAirline.getSelectionModel().getSelectedItem() != null) {
            if (option == 1) {
                msg = airport.createUser(txtName.getText(), txtLast.getText(), Long.parseLong(txtId.getText()),
                        txtEmail.getText(), txtPass.getText(), txtAirline.getValue());
            } else {
                msg = airport.editUser((AirlineUser) selected, txtName.getText(), txtLast.getText(),
                        Long.parseLong(txtId.getText()), txtEmail.getText(), txtPass.getText(), txtAirline.getValue());
            }
            dController.geAirportController().createAlert(msg, Route.SUCCESS);
            trimFileds();
        } else if (validateFields()) {
            if (option == 1) {
                msg = airport.createUser(txtName.getText(), txtLast.getText(), Long.parseLong(txtId.getText()),
                        txtEmail.getText(), txtPass.getText(), txtRole.getValue());
            } else {
                msg = airport.editUser(selected, txtName.getText(), txtLast.getText(), Long.parseLong(txtId.getText()),
                        txtEmail.getText(), txtPass.getText(), txtRole.getValue());
            }
            dController.geAirportController().createAlert(msg, Route.SUCCESS);
            trimFileds();
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
        airport.saveData();
        airport.loadData();
        getData();
    }

    public boolean validateFields() {
        boolean render = true;
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtLast.getText().isEmpty()
                || txtEmail.getText().isEmpty() || txtPass.getText().isEmpty()
                || txtRole.getSelectionModel().getSelectedItem() == null) {
            render = false;
        }
        return render;
    }

    public void trimFileds() {
        txtName.setText("");
        txtLast.setText("");
        txtId.setText("");
        txtEmail.setText("");
        txtPass.setText("");
        txtRole.setValue(null);
        txtRole.setDisable(false);
        txtAirline.setValue(null);
    }

    public void initRoles() {
        txtRole.getItems().addAll(UserRole.values());
    }

    public void initAirlines() {
        txtAirline.getItems().clear();
        txtAirline.getItems().addAll(airport.getAirlines());
    }

}
