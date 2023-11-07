package lk.ijse.Tm;

public class CustomerTm {
private String cus_id;
private String cus_name;
private String cus_address;
private String cus_tel;

    public CustomerTm() {
    }

    public CustomerTm(String cus_id, String cus_name, String cus_address, String cus_tel) {
        this.cus_id = cus_id;
        this.cus_name = cus_name;
        this.cus_address = cus_address;
        this.cus_tel = cus_tel;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }

    public String getCus_tel() {
        return cus_tel;
    }

    public void setCus_tel(String cus_tel) {
        this.cus_tel = cus_tel;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "cus_id='" + cus_id + '\'' +
                ", cus_name='" + cus_name + '\'' +
                ", cus_address='" + cus_address + '\'' +
                ", cus_tel='" + cus_tel + '\'' +
                '}';
    }
}
