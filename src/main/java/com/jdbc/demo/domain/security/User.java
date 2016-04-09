package com.jdbc.demo.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Users")
@NamedNativeQueries({
		@NamedNativeQuery(name = "user.all", query = "Select * from Users", resultClass = User.class), })
public class User {

	@Id
	@Column(name = "user_id")
	@SequenceGenerator(sequenceName = "USER_ID_SEQ", name = "UserIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserIdSequence")
	private Integer id;

	@Column(name = "login", unique = true, nullable = false)
	private String login;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "UsersRolesData", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_roles_id") })
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = id;
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (!(object instanceof User))
			return false;
		User another = (User) object;
		if (id != another.id)
			return false;
		if (login == null) {
			if (another.login != null)
				return false;
		} else if (!login.equals(another.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", userRoles=" + userRoles + "]";
	}

}