package com.example.androidmvpsample;

public interface ICustomer {
	void setId(long id);

	void setFirstName(String firstName);

	void setLastName(String lastName);

	long getId();

	String getFirstName();

	String getLastName();
	
}
