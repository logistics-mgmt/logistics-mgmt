package com.jdbc.demo.domain.psql;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;

/**
 * Created by Mateusz on 22-Oct-15.
 */

@Entity
@NamedNativeQueries({
		@NamedNativeQuery(name = "address.all", query = "Select * from Address", resultClass = Address.class), })
public class Address implements Serializable {

	@Id
	@SequenceGenerator(sequenceName = "ADDRESS_ID_SEQ", name = "AddressIdSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AddressIdSequence")
	@Column(name = "id_Address")
	private long id;

	private String town;
	private String street;
	private String code;

	@Column(nullable = false, name = "house_number")
	private String houseNumber;

	private String country;

	public Address() {

	}

	public Address(long id, String town, String street, String code, String houseNumber, String country) {
		this.id = id;
		this.town = town;
		this.street = street;
		this.code = code;
		this.houseNumber = houseNumber;
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Address address = (Address) o;

		if (id != address.id)
			return false;
		if (!town.equals(address.town))
			return false;
		if (!street.equals(address.street))
			return false;
		if (!code.equals(address.code))
			return false;
		if (!houseNumber.equals(address.houseNumber))
			return false;
		return country.equals(address.country);

	}

	@Override
	public int hashCode() {
		int result = (int) id;
		result = 31 * result + town.hashCode();
		result = 31 * result + street.hashCode();
		result = 31 * result + code.hashCode();
		result = 31 * result + houseNumber.hashCode();
		result = 31 * result + country.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return String.format("%s %s, %s %s, %s", street, houseNumber, code, town, country);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	@Column(nullable = false)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.trim();
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	@Column(nullable = false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
