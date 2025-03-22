package Managers;

import java.util.Arrays;
import java.util.Comparator;

import Comparators.CompareBuyersByName;
import Enums.ExceptionsMessages;
import Exceptions.EmptyCartPayException;
import Factory.FactoryUser;
import Models.Address;
import Models.Buyer;

public class ManagerBuyer implements BuyerInterface {

	private String input;
	private String msg;
	
	private FactoryUser factoryUser;
	
	private final int SIZE_INCREASE = 2;
	
	private Buyer[] buyers;

	private int numberOfBuyers;

	private final Comparator<Buyer> comparatorBuyer;

	private static ManagerBuyer instance;

	private ManagerBuyer() {
		buyers = new Buyer[0];
		comparatorBuyer = new CompareBuyersByName();
		factoryUser= new FactoryUser();

	}

	public static ManagerBuyer getInstance() {
		if (instance == null)
			instance = new ManagerBuyer();
		return instance;
	}

	public Buyer[] getBuyers() {
		return buyers;
	}

	public int getNumberOfBuyers() {
		return numberOfBuyers;
	}

	public String isExistBuyer(String name) {
		for (int i = 0; i < numberOfBuyers; i++) {
			if (buyers[i].getUserName().equalsIgnoreCase(name))
				return "Buyer name already EXIST, please try again!";
		}
		return null;
	}

	public void addBuyer(Buyer buyer) {

		if (buyers.length == numberOfBuyers) {
			if (buyers.length == 0) {
				buyers = Arrays.copyOf(buyers, 1);
			}
			else
				buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
		}
		buyers[numberOfBuyers++] = buyer;
	}

	public String chooseValidBuyer(String indexInput) {
		try {
			int index = Integer.parseInt(indexInput);
			if (index > numberOfBuyers || index <= 0)
				throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_BUYER_INDEX.getExceptionMessage());
		} catch (IndexOutOfBoundsException e) {
			return e.getMessage();
		} catch (NumberFormatException e) {
			return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
		}
		return null;
	}

	public String buyersInfo() {
		if (numberOfBuyers == 0) {
			return "\nHaven't buyers yet, cannot be proceed. return to Menu.";
		}
		StringBuilder sb = new StringBuilder("\nBuyers info:\n--------------\n");
		Arrays.sort(buyers, 0, numberOfBuyers, comparatorBuyer);
		for (int i = 0; i < numberOfBuyers; i++) {
			sb.append(i + 1).append(") ");
			sb.append(buyers[i].toString());
		}
		return sb.toString();
	}

	public String buyersNames() {
	        StringBuilder sb = new StringBuilder("Buyer's:\n--------------\n");
	        for (int i = 0; i < numberOfBuyers; i++) {
	            sb.append(i + 1).append(") ").append(buyers[i].getUserName()).append("\n");
	        }
	        return sb.toString();
	        }

	public String pay(int buyerIndex) {
		try {
			if (buyers[buyerIndex].getCurrentCart().getNumOfProducts() == 0)
				throw new EmptyCartPayException(buyers[buyerIndex].getUserName());
		} catch (EmptyCartPayException e) {
			return e.getMessage();
		}
		buyers[buyerIndex].payAndMakeHistoryCart();
		return """
				 ____   _ __   ____  __ _____ _   _ _____                              \s
				|  _ \\ / \\\\ \\ / /  \\/  | ____| \\ | |_   _|                             \s
				| |_) / _ \\\\ V /| |\\/| |  _| |  \\| | | |                               \s
				|  __/ ___ \\| | | |  | | |___| |\\  | | |                               \s
				|_| /_/   \\_\\_|_|_|_ |_|_____|_|_\\_|_|_|  _____ ____  _____ _   _ _    \s
				            / ___|| | | |/ ___/ ___/ ___|| ____/ ___||  ___| | | | |   \s
				            \\___ \\| | | | |   \\___ \\___ \\|  _| \\___ \\| |_  | | | | |   \s
				             ___) | |_| | |___ ___) |__) | |___ ___) |  _| | |_| | |___\s
				            |____/ \\___/ \\____|____/____/|_____|____/|_|    \\___/|_____|""";
	}

	public void replaceCarts(int historyCartIndex, int buyerIndex) {
		buyers[buyerIndex].setCurrentCart(buyers[buyerIndex].getHistoryCart()[historyCartIndex]);
	}

	public int chooseBuyer() {
		System.out.println(buyersNames());
		
		while (true) {
			input = UserInput.stringInput("Please choose buyer from the list above:");
			if (input.equals("-1"))
				return -1;
			msg = chooseValidBuyer(input);
			if (msg != null) {
				System.out.println(msg);
			} else {
				break;
			}
		}
		return Integer.parseInt(input) - 1;
	}

	public void paymentForBuyer() {
		if (getNumberOfBuyers() == 0) {
			System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
			return;
		}
		System.out.println("Please choose buyer from list to process checkout: (Enter -1 to return main menu)");
		int buyerIndex = chooseBuyer();
		if (buyerIndex == -1)
			return;
		System.out.println(pay(buyerIndex));

	}

	public void updateCartHistory() {
		if (getNumberOfBuyers() == 0) {
			System.out.println("Haven't buyers yet, cannot be proceed. return to Menu.");
			return;
		}
		int buyerIndex = chooseBuyer();
		if (buyerIndex == -1)
			return;
		if (getBuyers()[buyerIndex].getHistoryCartsNum() == 0) {
			System.out.println("\nHistory cart's are empty for this buyer, cannot proceed. return to main menu.");
			return;
		}
		System.out.println(getBuyers()[buyerIndex].toString());
		
		do {
			input = UserInput.stringInput("Please choose cart number from history carts: \n If you have products in your current cart - they will be replaced.");
			if (input.equals("-1"))
				return;
			msg = isValidHistoryCartIndex(input, buyerIndex);
			if (msg != null) {
				System.out.println(msg);
			}
		} while (msg != null);
		int historyCartIndex = Integer.parseInt(input);
		replaceCarts(historyCartIndex - 1, buyerIndex);
		System.out.println("Your current cart update successfully.");

	}

	public String isValidHistoryCartIndex(String indexCartInput, int buyerIndex) {
		try {
			int indexCart = Integer.parseInt(indexCartInput);
			if (buyers[buyerIndex].getHistoryCartsNum() < indexCart || indexCart <= 0)
				throw new IndexOutOfBoundsException(
						ExceptionsMessages.INVALID_HISTORY_CART_INDEX.getExceptionMessage());
		} catch (NumberFormatException e) {
			return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
		} catch (IndexOutOfBoundsException e) {
			return e.getMessage();
		}
		return null;
	}
	
	public void addBuyerTest(String name, String password, Address address) {
		Buyer buyer = new Buyer(name, password, address);
		if (buyers.length == numberOfBuyers) {
			if (buyers.length == 0) {
				buyers = Arrays.copyOf(buyers, 1);
			}
			buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
		}
		buyers[numberOfBuyers++] = buyer;
	}

}
