package com.example.androidmvpsample;

import com.example.androidmvpsample.models.Customer;

public class CustomerPresenter {

	private ICustomerView mCustomerView;
	private ICustomer mCustomer;
	
	public CustomerPresenter(ICustomerView view){
		mCustomerView = view;
		mCustomer = new Customer();
	}
	
    public void saveCustomer(long id, String firstName, String lastName) {
        mCustomer.setId(id);
    	mCustomer.setFirstName(firstName);
        mCustomer.setLastName(lastName);
    }

    public void loadCustomer(long id) {
    	mCustomer.setId(id);
    	mCustomer.setFirstName("user" + id + " first name");
    	mCustomer.setLastName("user" + id + " last name");
        mCustomerView.setId(mCustomer.getId());
        mCustomerView.setFirstName(mCustomer.getFirstName());
        mCustomerView.setLastName(mCustomer.getLastName());
    }
    
    public void showDialog(){
    	String message = this.getDialogMessage(mCustomer);
    	mCustomerView.showDialog(message);
    }
    
    private String getDialogMessage(ICustomer customer){
    	if(customer == null || customer.getId() == 0){
    		return "customer is null";
    	}

    	String message = "";
    	switch((int)(mCustomer.getId() % 2)){
    	case 0:
    		message = "even number";
    		break;
    	case 1:
    		message = "odd number";
    		break;
    	}
    	return message;
    }
}
