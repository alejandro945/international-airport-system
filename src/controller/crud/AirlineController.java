package controller.crud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
import javafx.scene.image.Image;
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
    private ImageView logo;

    @FXML
    private AnchorPane userCrud;

    @FXML
    private TableView<Airline> airlineTbl;

    @FXML
    private TableColumn<Airline, String> nameCol;

    @FXML
    private TableColumn<Airline, String> logoCol;

    @FXML
    private TableColumn<Airline, String> actionsCol;

    private Airport airport;
    private DashboardController dController;
    private Airline selected;
    private Stage modal;
    private String pathRender;

    public AirlineController(Airport airport, DashboardController dController) {
        this.airport = airport;
        this.dController = dController;
        pathRender = "";
    }

    @FXML
    public void newAirline(ActionEvent event) throws IOException {
        if (modal == null) {
            Stage stage = dController.loadModal(Route.AIRLINE_MODAL, this);
            setModal(stage);
            stage.show();
        }
    }

    public void setModal(Stage modal) {
        this.modal = modal;
    }

    @FXML
    public void addLogo(ActionEvent event) {
        pathRender = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            logo.setImage(new Image(selectedFile.toURI().toString()));
            String path = selectedFile.getPath();
            String[] parts = path.split(Pattern.quote(File.separator));
            for (int i = 0; i < parts.length; i++) {
                int render = (parts.length) - (i + 1);
                if (render < 5) {
                    pathRender += parts[i] + getRegex();
                }
            }
        }
    }

    public String getRegex() {
        String regex = "";
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            regex = "\\";
        } else {
            regex = "/";
        }
        return regex;
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
                airport.exportDataAirlines(selectedFile.getAbsolutePath());
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
                airport.importDataAirlines(selectedFile.getAbsolutePath());
            } catch (IOException e) {
                dController.alert(Route.WARNING, Constant.IOEXCEPTION);
            }
            airport.saveData();
            airport.loadData();
            getData();
        }
    }

    @FXML
    public void editAirline(ActionEvent event) {
        if (validateFields()) {
            selected.setAirlineName(txtName.getText());
            selected.setLogo(pathRender);
            dController.geAirportController().createAlert("Airline was successfully edited.", Route.SUCCESS);
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
    void saveAirline(ActionEvent event) {
        if (validateFields()) {
            dController.geAirportController().createAlert(airport.createAirline(txtName.getText(), pathRender),
                    Route.SUCCESS);
            airport.saveData();
            airport.loadData();
            getData();
            modal.close();
            setModal(null);
        } else {
            dController.geAirportController().createAlert(Constant.EMPTY_FIELDS, Route.WARNING);
        }
        pathRender = "";
    }

    private void getData() {
        airlineTbl.setFixedCellSize(50);
        ObservableList<Airline> airlines = FXCollections.observableArrayList(airport.getAirlines());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        logoCol.setMinWidth(320);
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
                            dController.geAirportController().createAlert("Airline was deleted successfully.",
                                    Route.SUCCESS);
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
        if (txtName.getText().isEmpty() || pathRender.equals("")) {
            render = false;
        }
        return render;
    }

    public void prepareEdition(Airline selected) {
        txtName.setText(selected.getAirlineName());
        File file = new File(selected.getIcon());
        logo.setImage(new Image(("file:///" + file.getAbsolutePath())));
        pathRender = selected.getIcon();
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }
}
