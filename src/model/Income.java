package model;

import java.time.LocalDate;

public class Income extends Transaction {
    private int clientId;

    public Income(int id, LocalDate date, double amount, String description, int clientId) {
        super(id, date, amount, description);
        this.clientId = clientId;
    }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
}
