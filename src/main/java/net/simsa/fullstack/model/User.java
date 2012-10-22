package net.simsa.fullstack.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	public static enum UserSort {
		ID, FIRSTNAME, LASTNAME, EMAIL
	}
	
	@GeneratedValue
	@Id
	Long id;
	String firstName;
	String lastName;
	String email;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Long getId()
	{
		return id;
	}

}
