package model;

import java.util.Objects;

/**
 * Represents a client in the accounting system. Clients are associated with income transactions.
 */
public class Client {
  private int id;
  private String name;
  private String cuit; // Tax identification number (Argentina)
  private String address;
  private String phone;

  public Client(int id, String name, String cuit, String address, String phone) {
    this.id = id;
    this.name = name;
    this.cuit = cuit;
    this.address = address;
    this.phone = phone;
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCuit() {
    return cuit;
  }

  public void setCuit(String cuit) {
    this.cuit = cuit;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Client client = (Client) o;
    return id == client.id && Objects.equals(cuit, client.cuit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cuit);
  }

  @Override
  public String toString() {
    return String.format("Client{id=%d, name='%s', cuit='%s', phone='%s'}", id, name, cuit, phone);
  }
}
