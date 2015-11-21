package com.jdbc.demo.domain;

/**
 * Created by Mateusz on 22-Oct-15.
 */
public class Client {

    private int id;
    private String name;
    private String NIP;
    private String bankAccountNumber;
    private Address address;

    public Client() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (!name.equals(client.name)) return false;
        if (NIP != null ? !NIP.equals(client.NIP) : client.NIP != null) return false;
        if (bankAccountNumber != null ? !bankAccountNumber.equals(client.bankAccountNumber) : client.bankAccountNumber != null)
            return false;
        return address.equals(client.address);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (NIP != null ? NIP.hashCode() : 0);
        result = 31 * result + (bankAccountNumber != null ? bankAccountNumber.hashCode() : 0);
        result = 31 * result + address.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", NIP='" + NIP + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", address=" + address +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
