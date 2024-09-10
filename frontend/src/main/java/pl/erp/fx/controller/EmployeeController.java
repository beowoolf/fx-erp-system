package pl.erp.fx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.erp.fx.dto.EmployeeDto;
import pl.erp.fx.factory.PopupFactory;
import pl.erp.fx.rest.EmployeeRestClient;
import pl.erp.fx.table.EmployeeTableModel;
import pl.erp.fx.util.EmployeeUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeeController implements Initializable {

    private static final String ADD_EMPLOYEE_FXML = "/fxml/add-employee.fxml";
    private static final String VIEW_EMPLOYEE_FXML = "/fxml/view-employee.fxml";
    private static final String EDIT_EMPLOYEE_FXML = "/fxml/edit-employee.fxml";
    private static final String DELETE_EMPLOYEE_FXML = "/fxml/delete-employee.fxml";

    private final EmployeeRestClient employeeRestClient;
    private final PopupFactory popupFactory;

    private ObservableList<EmployeeTableModel> data;

    @FXML
    private TableView<EmployeeTableModel> employeeTableView;

    @FXML
    private Button addButton;

    @FXML
    private Button viewButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    public EmployeeController() {
        data = FXCollections.observableArrayList();
        employeeRestClient = new EmployeeRestClient();
        popupFactory = new PopupFactory();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAddEmployeeButton();
        initializeViewEmployeeButton();
        initializeEditEmployeeButton();
        initializeDeleteEmployeeButton();
        initializeRefreshButton();
        initializeTableView();
    }

    private void initializeDeleteEmployeeButton() {
        deleteButton.setOnAction(x -> {
            EmployeeTableModel selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                try {
                    Stage deleteEmployeeStage = createEmployeeCrudStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(DELETE_EMPLOYEE_FXML));
                    Scene scene = new Scene(loader.load(), 400, 200);
                    deleteEmployeeStage.setScene(scene);
                    DeleteEmployeeController controller = loader.getController();
                    controller.loadEmployeeData(selectedEmployee);
                    deleteEmployeeStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeEditEmployeeButton() {
        editButton.setOnAction(x -> {
            EmployeeTableModel selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                try {
                    Stage waitingPopup = popupFactory.createWaitingPopup("Loading employee data...");
                    waitingPopup.show();
                    Stage editEmployeeStage = createEmployeeCrudStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(EDIT_EMPLOYEE_FXML));
                    Scene scene = new Scene(loader.load(), 500, 400);
                    editEmployeeStage.setScene(scene);
                    EditEmployeeController controller = loader.getController();
                    controller.loadEmployeeData(selectedEmployee.getIdEmployee(), () -> {
                        waitingPopup.close();
                        editEmployeeStage.show();
                    });
                } catch (IOException e) {
                    throw new RuntimeException("Can't load fxml file: " + EDIT_EMPLOYEE_FXML);
                }
            }
        });
    }

    private Stage createEmployeeCrudStage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    private void initializeViewEmployeeButton() {
        viewButton.setOnAction(x -> {
            EmployeeTableModel employee = employeeTableView.getSelectionModel().getSelectedItem();
            if (employee == null)
                return;
            else {
                try {
                    Stage waitingPopup = popupFactory.createWaitingPopup("Loading employee data...");
                    waitingPopup.show();
                    Stage viewEmployeeStage = new Stage();
                    viewEmployeeStage.initStyle(StageStyle.UNDECORATED);
                    viewEmployeeStage.initModality(Modality.APPLICATION_MODAL);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_EMPLOYEE_FXML));
                    Scene scene = new Scene((BorderPane) loader.load(), 500, 400);
                    viewEmployeeStage.setScene(scene);
                    ViewEmployeeController controller = loader.<ViewEmployeeController>getController();
                    controller.loadEmployeeData(employee.getIdEmployee(), () -> {
                        waitingPopup.close();
                        viewEmployeeStage.show();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeRefreshButton() {
        refreshButton.setOnAction(x -> {
            loadEmployeeData();
        });
    }

    private void initializeAddEmployeeButton() {
        addButton.setOnAction((x) -> {
            Stage addEmployeeStage = new Stage();
            addEmployeeStage.initStyle(StageStyle.UNDECORATED);
            addEmployeeStage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent addEmployeeParent = FXMLLoader.load(getClass().getResource(ADD_EMPLOYEE_FXML));
                Scene scene = new Scene(addEmployeeParent, 500, 400);
                addEmployeeStage.setScene(scene);
                addEmployeeStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initializeTableView() {
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("firstName"));
        TableColumn lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("lastName"));
        TableColumn salaryColumn = new TableColumn("Salary");
        salaryColumn.setMinWidth(100);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("salary"));
        employeeTableView.getColumns().addAll(firstNameColumn, lastNameColumn, salaryColumn);
        loadEmployeeData();
        employeeTableView.setItems(data);
    }

    private void loadEmployeeData() {
        Thread thread = new Thread(() -> {
            List<EmployeeDto> employees = employeeRestClient.getEmployees();
            data.clear();
            data.addAll(employees.stream().map(EmployeeUtils::of).collect(Collectors.toList()));
        });
        thread.start();
    }

}
