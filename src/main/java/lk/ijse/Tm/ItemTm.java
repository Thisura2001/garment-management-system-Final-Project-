package lk.ijse.Tm;

public class ItemTm {
    private String code;
    private String description;
    private Integer qty_on_hand;
    private String unit_price;

    public ItemTm() {
    }

    public ItemTm(String code, String description, Integer qty_on_hand, String unit_price) {
        this.code = code;
        this.description = description;
        this.qty_on_hand = qty_on_hand;
        this.unit_price = unit_price;
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

    public Integer getQty_on_hand() {
        return qty_on_hand;
    }

    public void setQty_on_hand(Integer qty_on_hand) {
        this.qty_on_hand = qty_on_hand;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", qty_on_hand=" + qty_on_hand +
                ", unit_price='" + unit_price + '\'' +
                '}';
    }
}
