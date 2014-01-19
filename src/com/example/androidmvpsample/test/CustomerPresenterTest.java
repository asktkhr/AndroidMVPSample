package com.example.androidmvpsample.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import com.example.androidmvpsample.CustomerPresenter;
import com.example.androidmvpsample.ICustomer;
import com.example.androidmvpsample.ICustomerView;

public class CustomerPresenterTest {

	@Before
	public void setUp() throws Exception {
		isCalledSetIdMethod = false;
		isCalledSetFirstNameMethod = false;
		isCalledSetLastNameMethod = false;
		isCalledShowDialogMethod = false;
	}

	@Test
	public void testCustomerPresenter() {
		ICustomerView view = ICustomerViewMockBuilder.create();

		try {
			CustomerPresenter presenter = new CustomerPresenter(view);
			assertNotNull(presenter);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveCustomer() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		CustomerPresenter presenter = new CustomerPresenter(null);
		presenter.saveCustomer(1, "firstname", "lastname");

		Class<? extends CustomerPresenter> c = presenter.getClass();
		Field mCustomer = c.getDeclaredField("mCustomer");
		mCustomer.setAccessible(true);
		ICustomer customer = (ICustomer) mCustomer.get(presenter);
		
		assertEquals("mCustomer.getId() is ", 1, customer.getId());
		assertEquals("mCustomer.getFirstName() is ", "firstname", customer.getFirstName());
		assertEquals("mCustomer.getLastNmae() is", "lastname", customer.getLastName());
	}

	static boolean isCalledSetIdMethod;
	static boolean isCalledSetFirstNameMethod;
	static boolean isCalledSetLastNameMethod;
	static boolean isCalledShowDialogMethod;

	@Test
	public void testLoadCustomer() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {

		ICustomer expectCustomer = ICustomerBuilder.create(2, "user2 first name", "user2 last name");

		ICustomerView view = ICustomerViewMockBuilder.create(
				expectCustomer.getId(),
				expectCustomer.getFirstName(),
				expectCustomer.getLastName(),
				null);

		CustomerPresenter presenter = new CustomerPresenter(view);
		Class c = presenter.getClass();

		Field mCustomer = c.getDeclaredField("mCustomer");
		mCustomer.setAccessible(true);

		ICustomer customer = (ICustomer) mCustomer.get(presenter);

		presenter.loadCustomer(2);
		
		assertEquals("mCustomer.getId() is",expectCustomer.getId(), customer.getId());
		assertEquals("mCustomer.getFirstName() is ", expectCustomer.getFirstName(), customer.getFirstName());
		assertEquals("mCustomer.getLastNmae() is", expectCustomer.getLastName(), customer.getLastName());

		assertTrue("ICustomerView is called setId()", isCalledSetIdMethod);
		assertTrue("ICustomerView is called setFirstName()", isCalledSetFirstNameMethod);
		assertTrue("ICustomerView is called setLastName()", isCalledSetLastNameMethod);
	}
	

	@Test
	public void testShowDialogWithCustomerIdOdd(){
		ICustomer expectCustomer = ICustomerBuilder.create(1, "firstName1", "lastName1");
		ICustomerView view = ICustomerViewMockBuilder.create(
				expectCustomer.getId(),
				expectCustomer.getFirstName(),
				expectCustomer.getLastName(),
				"odd number"
				);
		CustomerPresenter presenter = new CustomerPresenter(view);
		presenter.saveCustomer(expectCustomer.getId(), expectCustomer.getFirstName(), expectCustomer.getLastName());
		
		presenter.showDialog();
		
		assertTrue("ICustomerView is called showDialog()", isCalledShowDialogMethod);
	}
	
	@Test
	public void testShowDialogWithCustomerIdEven(){
		ICustomer expectCustomer = ICustomerBuilder.create(2, "firstName2", "lastName2");
		ICustomerView view = ICustomerViewMockBuilder.create(
				expectCustomer.getId(),
				expectCustomer.getFirstName(),
				expectCustomer.getLastName(),
				"even number"
				);
		CustomerPresenter presenter = new CustomerPresenter(view);
		presenter.saveCustomer(expectCustomer.getId(), expectCustomer.getFirstName(), expectCustomer.getLastName());
		
		presenter.showDialog();
		
		assertTrue("ICustomerView is called showDialog()", isCalledShowDialogMethod);
	}
	
	@Test
	public void testShowDialogWithCustomerNull(){
		ICustomerView view = ICustomerViewMockBuilder.create(
				0,
				null,
				null,
				"customer is null"
				);
		
		CustomerPresenter presenter = new CustomerPresenter(view);

		presenter.showDialog();
		
		assertTrue("ICustomerView is called showDialog()", isCalledShowDialogMethod);
	}
	
	private static class ICustomerViewMockBuilder {
		public static ICustomerView create() {
			return ICustomerViewMockBuilder.create(0, "", "", "");
		}

		public static ICustomerView create(final long expectId, final String expectFirstName, final String expectLastName, final String expectDialogMessage) {
			ICustomerView view = new ICustomerView() {

				@Override
				public void setId(long id) {
					assertEquals(expectId, id);
					isCalledSetIdMethod = true;
				}

				@Override
				public void setFirstName(String firstName) {
					assertEquals(expectFirstName, firstName);
					isCalledSetFirstNameMethod = true;
				}

				@Override
				public void setLastName(String lastName) {
					assertEquals(expectLastName, lastName);
					isCalledSetLastNameMethod = true;
				}

				@Override
				public void showDialog(String message) {
					assertEquals(expectDialogMessage, message);
					isCalledShowDialogMethod = true;
				}
			};
			return view;
		}
	}
	
	private static class ICustomerBuilder{
		public static ICustomer create(long id, String firstName, String lastName){
			ICustomer customer = new ICustomer() {
				private long id;
				private String firstName;
				private String lastName;

				@Override
				public void setId(long id) {
					this.id = id;
				}

				@Override
				public void setFirstName(String firstName) {
					this.firstName = firstName;
				}

				@Override
				public void setLastName(String lastName) {
					this.lastName = lastName;
				}

				@Override
				public long getId() {
					return this.id;
				}

				@Override
				public String getFirstName() {
					return this.firstName;
				}

				@Override
				public String getLastName() {
					return this.lastName;
				}
			};
			customer.setId(id);
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			return customer;
		}
	}
}
