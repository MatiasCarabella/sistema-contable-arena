package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract base class for financial transactions. Represents common attributes for both income and
 * expenses.
 */
public abstract class Transaction {
  protected int id;
  protected LocalDate date;
  protected double amount;
  protected String description;

  public Transaction(int id, LocalDate date, double amount, String description) {
    this.id = id;
    this.date = date;
    this.amount = amount;
    this.description = description;
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return String.format(
        "%s{id=%d, date=%s, amount=%.2f, description='%s'}",
        getClass().getSimpleName(), id, date, amount, description);
  }
}
