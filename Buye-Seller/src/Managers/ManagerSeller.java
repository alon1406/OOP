package Managers;


import java.util.Arrays;
import java.util.Comparator;

import Comparators.CompareSellersByProductsNumber;
import Enums.ExceptionsMessages;
import Factory.FactoryUser;
import Models.Seller;



public class ManagerSeller implements SellerInterface {

	private String input;
	private String msg;
    private final int SIZE_INCREASE = 2;
	
	private int numberOfSellers;

	 private final Comparator<Seller> comparatorSeller;
	 
	 private Seller[] sellers;
	 
	 private static ManagerSeller instance;
	 
	 
	 
	 public static ManagerSeller getInstance() {
			if(instance == null)
				instance = new ManagerSeller();
			return instance;
		}



	private ManagerSeller() {
		sellers= new Seller[0];
		comparatorSeller= new CompareSellersByProductsNumber();
        FactoryUser fUser = new FactoryUser();
	}



	public Seller[] getSellers() {
        return sellers;
    }
	
	 public int getNumberOfSellers() {
	        return numberOfSellers;
	    }
	 
	 
	 public String isExistSeller(String name) {
	        for (int i = 0; i < numberOfSellers; i++) {
	            if (sellers[i].getUserName().equalsIgnoreCase(name)) return "Seller name already EXIST, please try again!";
	        }
	        return null;
	    }
	 
	 
	 
	 public String chooseValidSeller(String indexInput) {
	        try {
	            int index = Integer.parseInt(indexInput);
	            if (index > numberOfSellers || index <= 0)
	                throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_SELLER_INDEX.getExceptionMessage());
	        } catch (IndexOutOfBoundsException e) {
	            return e.getMessage();
	        } catch (NumberFormatException e) {
	            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
	        }
	        return null;
	    }
	 
	 
	 public void addSeller(Seller seller) {
	     
	        if (sellers.length == numberOfSellers) {
	            if (sellers.length == 0) {
	                sellers = Arrays.copyOf(sellers, 1);
	            }
				else
	            	sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
	        }
	        sellers[numberOfSellers++] = seller;
	    }
	 
	 
	 public String sellersInfo() {
	        if (numberOfSellers == 0) {
	            return "\nHaven't sellers yet, cannot be proceed. return to Menu.";
	        }
	        StringBuilder sb = new StringBuilder("\nSellers info:\n--------------\n");
	        Arrays.sort(sellers, 0, numberOfSellers, comparatorSeller);
	        for (int i = 0; i < numberOfSellers; i++) {
	            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append(":");
	            sb.append(sellers[i].toString()).append("\n");
	        }
	        return sb.toString();
	    }

	 
	 public String sellersNames() {
	        StringBuilder sb = new StringBuilder("Seller's:\n--------------\n");
	        for (int i = 0; i < numberOfSellers; i++) {
	            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append("\n");
	        }
	        return sb.toString();
	    }
	 

    
	 public int chooseSeller() {
			System.out.println(sellersNames());
			
			while (true) {
				input = UserInput.stringInput("Please choose seller from the list above: ");
				if (input.equals("-1"))
					return -1;
				msg = chooseValidSeller(input);
				if (msg != null) {
					System.out.println(msg);
				} else {
					break;
				}
			}
			return Integer.parseInt(input) - 1;
		}



	

	 
	
	 
	 
	
}


