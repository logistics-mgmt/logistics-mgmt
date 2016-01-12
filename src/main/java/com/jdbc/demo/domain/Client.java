package com.jdbc.demo.domain;

import javax.persistence.*;

/**
 * Created by Mateusz on 22-Oct-15.
 */

@Entity
@NamedNativeQueries({
        @NamedNativeQuery(name = "client.all", query = "Select * from Client", resultClass = Client.class),
})
public class Client {

    @Id
    @SequenceGenerator(sequenceName = "CLIENT_ID_SEQ", name = "ClientIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ClientIdSequence")
    @Column(name = "id_Client")
    private long id;
    private String name;
    private String NIP;

    @Column(name = "account_number")
    private String bankAccountNumber;

    @ManyToOne
    @JoinColumn(name="id_Address")
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
        int result = (int)id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (NIP != null ? NIP.hashCode() : 0);
        result = 31 * result + (bankAccountNumber != null ? bankAccountNumber.hashCode() : 0);
        result = 31 * result + address.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s, Adres: %s", name, address);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(nullable = false)
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
