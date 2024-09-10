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
import pl.erp.fx.dto.ItemDto;
import pl.erp.fx.rest.ItemRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewItemController implements Initializable {

    private final ItemRestClient itemRestClient;
    @FXML
    private BorderPane viewItemBorderPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox<String> quantityTypeComboBox;
    @FXML
    private Button okButton;

    public ViewItemController() {
        itemRestClient = new ItemRestClient();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeControls();
        initializeOkButton();
    }

    public void loadItemData(Long idItem) {
        Thread thread = new Thread(() -> {
            ItemDto itemDto = itemRestClient.getItem(idItem);
            Platform.runLater(() -> {
                nameTextField.setText(itemDto.getName());
                quantityTextField.setText(itemDto.getQuantity().toString());
                quantityTypeComboBox.setItems(FXCollections.observableArrayList(itemDto.getQuantityType()));
                quantityTypeComboBox.getSelectionModel().select(0);
            });
        });
        thread.start();
    }

    private void initializeControls() {
        nameTextField.setEditable(false);
        quantityTextField.setEditable(false);
        quantityTypeComboBox.setEditable(false);
    }

    private void initializeOkButton() {
        okButton.setOnAction(x -> {
            getStage().close();
        });
    }

    private Stage getStage() {
        return (Stage) viewItemBorderPane.getScene().getWindow();
    }

}
