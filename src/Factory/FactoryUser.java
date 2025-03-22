package Factory;

import Models.Address;
import Models.Buyer;
import Models.Seller;

public class FactoryUser {
	

	public Seller addSellerFromUser(String username, String password) {

		return new Seller(username, password);

	}

	public Buyer addBuyerFromUser(String name, String password, Address address) {


		return new Buyer(name, password, address);
	}
	
	
	
	public Address createAddress(String street, String houseNumber, String city ,String state) {
		
	
		return new Address(street,houseNumber,city,state);
		
		
	}

}
