package model;

import java.time.LocalDate;

/** Represents an income transaction associated with a client. */
public class Income extends Transaction {
  private int clientId;

  public Income(int id, LocalDate date, double amount, String description, int clientId) {
    super(id, date, amount, description);
    this.clientId = clientId;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  @Override
  public String toString() {
    return String.format(
        "Income{id=%d, date=%s, amount=%.2f, description='%s', clientId=%d}",
        id, date, amount, description, clientId);
  }
}
