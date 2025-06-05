package model;

import java.time.LocalDate;

public class Expense extends Transaction {
    private int supplierId;

    public Expense(int id, LocalDate date, double amount, String description, int supplierId) {
        super(id, date, amount, description);
        this.supplierId = supplierId;
    }

    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
}
