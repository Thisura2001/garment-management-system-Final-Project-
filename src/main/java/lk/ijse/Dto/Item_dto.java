package lk.ijse.Dto;

public class Item_dto {
    String code;
    String description;
    int qty;
    String unitPrice;

    public Item_dto() {
    }

    public Item_dto(String code, String description, int qty, String unitPrice) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item_dto{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", unitPrice='" + unitPrice + '\'' +
                '}';
    }
}
