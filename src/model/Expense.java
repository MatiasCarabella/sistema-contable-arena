package model;

import java.time.LocalDate;

/**
 * Represents an expense transaction associated with a supplier.
 */
public class Expense extends Transaction {
    private int supplierId;

    public Expense(int id, LocalDate date, double amount, String description, int supplierId) {
        super(id, date, amount, description);
        this.supplierId = supplierId;
    }

    public int getSupplierId() { 
        return supplierId; 
    }
    
    public void setSupplierId(int supplierId) { 
        this.supplierId = supplierId; 
    }

    @Override
    public String toString() {
        return String.format("Expense{id=%d, date=%s, amount=%.2f, description='%s', supplierId=%d}",
            id, date, amount, description, supplierId);
    }
}
