package lk.ijse.Dto;

public class RowMaterial_dto {
    String row_id;
    String name;
    String unitPrice;
    int qty;

    public RowMaterial_dto() {
    }

    public RowMaterial_dto(String row_id, String name, String unitPrice, int qty) {
        this.row_id = row_id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getRow_id() {
        return row_id;
    }

    public void setRow_id(String row_id) {
        this.row_id = row_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "RowMaterial_dto{" +
                "row_id='" + row_id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qty=" + qty +
                '}';
    }
}
