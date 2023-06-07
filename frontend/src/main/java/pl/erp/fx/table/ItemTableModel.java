package pl.erp.fx.table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemTableModel {

    private final Long idItem;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty quantity;
    private final SimpleStringProperty quantityType;

    public ItemTableModel(Long idItem, String name, Double quantity, String quantityType) {
        this.idItem = idItem;
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.quantityType = new SimpleStringProperty(quantityType);
    }

    public Long getIdItem() {
        return idItem;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public String getQuantityType() {
        return quantityType.get();
    }

    public void setQuantityType(String quantityType) {
        this.quantityType.set(quantityType);
    }

    public SimpleStringProperty quantityTypeProperty() {
        return quantityType;
    }

}
