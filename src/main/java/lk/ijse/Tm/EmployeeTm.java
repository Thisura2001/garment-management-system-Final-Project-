package lk.ijse.Tm;

public class EmployeeTm {
private String emp_id;
private String name;
private String address;
private Integer tel;

    public EmployeeTm() {
    }

    public EmployeeTm(String emp_id, String name, String address, Integer tel) {
        this.emp_id = emp_id;
        this.name = name;
        this.address = address;
        this.tel = tel;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
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

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "emp_id='" + emp_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel=" + tel +
                '}';
    }
}
