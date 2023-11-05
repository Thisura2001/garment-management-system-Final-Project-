package lk.ijse.Dto;

public class Employee_dto {
String Emp_id;
String name;
String address;
int tel;

    public Employee_dto() {
    }

    public Employee_dto(String emp_id, String name, String address, int tel) {
        Emp_id = emp_id;
        this.name = name;
        this.address = address;
        this.tel = tel;
    }

    public String getEmp_id() {
        return Emp_id;
    }

    public void setEmp_id(String emp_id) {
        Emp_id = emp_id;
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

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Employee_dto{" +
                "Emp_id='" + Emp_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel=" + tel +
                '}';
    }
}
