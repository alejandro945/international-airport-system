package controller.crud;

import java.io.IOException;

import com.jfoenix.controls.*;

import controller.Constant;
import controller.DashboardController;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.*;
import route.Route;

public class UserController {

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
    private Label modalName;

    private Airport airport;
    private DashboardController dController;
    private User selected;
    private Stage modal;

    public UserController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
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
                        Button edit = new Button("Edit");
                        delete.getStylesheets().add(Route.CRUD.getRoute());
                        edit.getStylesheets().add(Route.CRUD.getRoute());
                        delete.setOnAction((ActionEvent event) -> {
                            selected = userTbl.getSelectionModel().getSelectedItem();
                            airport.deleteUser(selected);
                        });
                        edit.setOnAction((ActionEvent event) -> {
                            selected = userTbl.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource(Route.USER_MODAL.getRoute()));
                            loader.setController(this);
                            try {
                                loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            prepareEdition(selected);
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
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

    private void prepareEdition(User selected) {
        txtId.setText(String.valueOf(selected.getId()));
        txtName.setText(selected.getName());
        txtLast.setText(selected.getLastName());
        txtEmail.setText(selected.getEmail());
        txtRole.setValue(selected.getRole());
    }

    @FXML
    public void cancelModal(ActionEvent event) {
        modal.close();
    }

    @FXML
    public void saveUser(ActionEvent event) {
        if (validateFields()) {
            String msg = airport.createUser(txtName.getText(), txtLast.getText(), Long.parseLong(txtId.getText()),
                    txtEmail.getText(), txtPass.getText());
            dController.geAirportController().createAlert(msg, Route.SUCCESS);
        }
        getData();
    }

    public boolean validateFields() {
        boolean render = false;
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty() || txtLast.getText().isEmpty()
                || txtEmail.getText().isEmpty() || txtPass.getText().isEmpty()
                || txtRole.getSelectionModel().getSelectedItem() == null) {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        } else {
            render = true;
        }
        return render;
    }

    public void initRoles() {
        txtRole.getItems().addAll(UserRole.values());
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    public void newUser(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Route.USER_MODAL.getRoute()));
        fxmlLoader.setController(this);
        Parent modal = fxmlLoader.load();
        Scene scene = new Scene(modal);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        setModal(stage);
        initRoles();
        stage.show();
    }
}
