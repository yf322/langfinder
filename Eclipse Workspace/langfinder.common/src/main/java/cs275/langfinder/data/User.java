package cs275.langfinder.data;

import java.util.Date;

public class User {
	Integer id;
	String email;
	String passwordHash;
	boolean confirmed;
	String firstName;
	String lastName;
	Date lastActiveUTC;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getLastActiveUTC() {
		return lastActiveUTC;
	}
	public void setLastActiveUTC(Date lastActiveUTC) {
		this.lastActiveUTC = lastActiveUTC;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public boolean getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
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
	public User() {
		super();
	}
	public User(Integer id, String email, String passwordHash,
			boolean confirmed, String firstName, String lastName) {
		super();
		this.id = id;
		this.email = email;
		this.passwordHash = passwordHash;
		this.confirmed = confirmed;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
