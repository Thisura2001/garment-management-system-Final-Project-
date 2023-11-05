package lk.ijse.Dto;

public class Supplier_dto {
    String sup_id;
    String name;
    String address;
    String tel;
    String type;

    public Supplier_dto() {
    }

    public Supplier_dto(String sup_id, String name, String address, String tel, String type) {
        this.sup_id = sup_id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.type = type;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Supplier_dto{" +
                "sup_id='" + sup_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
