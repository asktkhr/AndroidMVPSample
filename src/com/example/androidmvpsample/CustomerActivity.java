package com.example.androidmvpsample;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CustomerActivity extends Activity implements ICustomerView, OnClickListener {
	
	private EditText mIdEditText, mFirstNameEditText, mLastNameEditText;
	private Button mSaveButton, mLoadButton;
	private CustomerPresenter mCustomerPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		
		mIdEditText = (EditText)findViewById(R.id.id_edit_text);
		mFirstNameEditText = (EditText)findViewById(R.id.first_name_edit_text);
		mLastNameEditText = (EditText)findViewById(R.id.last_name_edit_text);
		
		mSaveButton = (Button)findViewById(R.id.save_button);
		mLoadButton = (Button)findViewById(R.id.load_button);
		mSaveButton.setOnClickListener(this);
		mLoadButton.setOnClickListener(this);
		
		mCustomerPresenter = new CustomerPresenter(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.save_button:
			long id = Long.parseLong(mIdEditText.getText().toString());
			String firstName = mFirstNameEditText.getText().toString();
			String lastName = mLastNameEditText.getText().toString();
			mCustomerPresenter.saveCustomer(id, firstName, lastName);
			break;
		case R.id.load_button:
			mCustomerPresenter.loadCustomer(Long.parseLong(mIdEditText.getText().toString()));
			break;
		}
	}

	@Override
	public void setId(long id) {
		mIdEditText.setText(String.valueOf(id));
	}

	@Override
	public void setLastName(String lastName) {
		mLastNameEditText.setText(lastName);
	}

	@Override
	public void setFirstName(String firstName) {
		mFirstNameEditText.setText(firstName);
	}

	@Override
	public void showDialog(String message) {
		(new AlertDialog.Builder(this))
		.setMessage(message)
		.create()
		.show();
	}

}
