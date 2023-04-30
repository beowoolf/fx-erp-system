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
import pl.erp.fx.dto.ItemEditViewDto;
import pl.erp.fx.dto.ItemSaveDto;
import pl.erp.fx.dto.QuantityTypeDto;
import pl.erp.fx.rest.ItemRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class EditItemController implements Initializable {

    private final ItemRestClient itemRestClient;
    @FXML
    private BorderPane editItemBorderPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<QuantityTypeDto> quantityTypeComboBox;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelButton;
    private Long idItemToEdit;

    public EditItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeEditButton();
    }

    private void initializeEditButton() {
        editButton.setOnAction(x -> {
            ItemSaveDto dto = new ItemSaveDto();
            String name = nameTextField.getText();
            double quantity = Double.parseDouble(quantityTextField.getText());
            Long idQuantityType = quantityTypeComboBox.getSelectionModel().getSelectedItem().getIdQuantityType();
            dto.setIdItem(idItemToEdit);
            dto.setName(name);
            dto.setQuantity(quantity);
            dto.setIdQuantityType(idQuantityType);
            itemRestClient.saveItem(dto, () -> getStage().close());
        });
    }

    public void loadItemData(Long idItem) {
        Thread thread = new Thread(() -> {
            ItemEditViewDto dto = itemRestClient.getEditItemData(idItem);
            Platform.runLater(() -> {
                idItemToEdit = dto.getIdItem();
                nameTextField.setText(dto.getName());
                quantityTextField.setText(dto.getQuantity().toString());
                quantityTypeComboBox.setItems(FXCollections.observableArrayList(dto.getQuantityTypeDtoList()));
                for (int i = 0; i < quantityTypeComboBox.getItems().size(); i++) {
                    QuantityTypeDto quantityTypeDto = quantityTypeComboBox.getItems().get(i);
                    if (quantityTypeDto.getIdQuantityType().equals(dto.getIdQuantityType())) {
                        quantityTypeComboBox.getSelectionModel().select(i);
                    }
                }
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
        return (Stage) editItemBorderPane.getScene().getWindow();
    }

}
