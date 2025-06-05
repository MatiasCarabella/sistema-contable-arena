package model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private int id;
    private String name;
    private String cuit;
    private String address;
    private String phone;
    private List<Income> incomes = new ArrayList<>();

    public Client(int id, String name, String cuit, String address, String phone) {
        this.id = id;
        this.name = name;
        this.cuit = cuit;
        this.address = address;
        this.phone = phone;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<Income> getIncomes() { return incomes; }
    public void addIncome(Income income) { this.incomes.add(income); }
}
