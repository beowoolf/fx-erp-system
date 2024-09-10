package pl.erp.fx.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.erp.fx.dto.ItemSaveDto;
import pl.erp.fx.dto.QuantityTypeDto;
import pl.erp.fx.dto.WarehouseDto;
import pl.erp.fx.rest.ItemRestClient;
import pl.erp.fx.rest.QuantityTypeRestClient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    private final ItemRestClient itemRestClient;
    private final QuantityTypeRestClient quantityTypeRestClient;
    @FXML
    private BorderPane addItemBorderPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<QuantityTypeDto> quantityTypeComboBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    private WarehouseDto selectedWarehouseDto;

    public AddItemController() {
        itemRestClient = new ItemRestClient();
        quantityTypeRestClient = new QuantityTypeRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeSaveButton();
    }

    private void initializeSaveButton() {
        saveButton.setOnAction(x -> {
            String name = nameTextField.getText();
            Double quantity = Double.parseDouble(quantityTextField.getText());
            Long idQuantityType = quantityTypeComboBox.getSelectionModel().getSelectedItem().getIdQuantityType();
            Long idWarehouse = selectedWarehouseDto.getIdWarehouse();
            ItemSaveDto dto = new ItemSaveDto(null, name, quantity, idQuantityType, idWarehouse);
            Thread thread = new Thread(() -> {
                itemRestClient.saveItem(dto, () -> {
                    Platform.runLater(() -> {
                        getStage().close();
                    });
                });
            });
            thread.start();
        });
    }

    public void loadQuantityTypes() {
        Thread thread = new Thread(() -> {
            List<QuantityTypeDto> quantityTypes = quantityTypeRestClient.getQuantityTypes();
            Platform.runLater(() -> {
                quantityTypeComboBox.setItems(FXCollections.observableArrayList(quantityTypes));
            });
        });
        thread.start();
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction((x) -> {
            getStage().close();
        });
    }

    private Stage getStage() {
        return (Stage) addItemBorderPane.getScene().getWindow();
    }

    public void setWarehouseDto(WarehouseDto selectedWarehouseDto) {
        this.selectedWarehouseDto = selectedWarehouseDto;
    }

}
