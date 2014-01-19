package com.example.androidmvpsample;

public interface ICustomerView {
    void setId(long id);
    void setFirstName (String firstName);
    void setLastName (String lastName);
	void showDialog(String message); 
}
