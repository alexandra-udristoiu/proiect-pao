package entity;

public class CustomerEntity {
	
	private String email;
	
	private String lastName;
	
	private String firstName;
	
	private String address;
	
	private String phoneNumber;
	
	public CustomerEntity(String email, String lastName, String firstName, String address, String phoneNumber) {
		this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
