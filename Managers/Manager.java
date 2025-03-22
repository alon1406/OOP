package Managers;

import Comparators.CompareBuyersByName;
import Comparators.CompareSellersByProductsNumber;
import Enums.Category;
import Enums.ExceptionsMessages;
import Exceptions.*;
import Models.*;

import java.util.*;

public class Manager implements Manageable {
    private final int SIZE_INCREASE = 2;
    private Seller[] sellers;
    private int numberOfSellers;
    private Buyer[] buyers;
    private int numberOfBuyers;
    private final Categories categoriesArrays;
    private final Comparator<Seller> comparatorSeller;
    private final Comparator<Buyer> comparatorBuyer;
    private Product[] allProducts;
    private int logicProductsSize = 0;


    public Manager() {
        buyers = new Buyer[0];
        sellers = new Seller[0];
        categoriesArrays = new Categories();
        comparatorSeller = new CompareSellersByProductsNumber();
        comparatorBuyer = new CompareBuyersByName();
        allProducts = new Product[0];

    }


    public Product[] getallProducts() {
        return allProducts;
    }

    public Seller[] getSellers() {
        return sellers;
    }

    public Buyer[] getBuyers() {
        return buyers;
    }

    public int getNumberOfSellers() {
        return numberOfSellers;
    }

    public int getNumberOfBuyers() {
        return numberOfBuyers;
    }

    public int getLogicProductsSize() {
        return logicProductsSize;
    }

    public String validProductIndex(int sellerIndex, String productIndexInput) {
        try {
            int productIndex = Integer.parseInt(productIndexInput);
            if (productIndex <= 0 || productIndex > sellers[sellerIndex].getNumOfProducts())
                throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_PRODUCT_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validPrice(String priceInput) {
        try {
            double price = Double.parseDouble(priceInput);
            if (price <= 0)
                throw new InputMismatchException(ExceptionsMessages.INVALID_PRICE_VALUE.getExceptionMessage());
        } catch (NullPointerException e) {
            return ExceptionsMessages.PRICE_EMPTY.getExceptionMessage();
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_PRICE_INPUT.getExceptionMessage();
        } catch (InputMismatchException e) {
            return e.getMessage();
        }
        return null;
    }

    public String validCategoryIndex(String categoryInput) {
        try {
            int categoryChoice = Integer.parseInt(categoryInput);
            if (categoryChoice <= 0 || categoryChoice > Category.values().length)
                throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_CATEGORY_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return null;
    }

    public String isExistSeller(String name) {
        for (int i = 0; i < numberOfSellers; i++) {
            if (sellers[i].getUserName().equalsIgnoreCase(name)) return "Seller name already EXIST, please try again!";
        }
        return null;
    }

    public String isExistBuyer(String name) {
        for (int i = 0; i < numberOfBuyers; i++) {
            if (buyers[i].getUserName().equalsIgnoreCase(name)) return "Buyer name already EXIST, please try again!";
        }
        return null;
    }

    public String isValidHistoryCartIndex(String indexCartInput, int buyerIndex) {
        try {
            int indexCart = Integer.parseInt(indexCartInput);
            if (buyers[buyerIndex].getHistoryCartsNum() < indexCart || indexCart <= 0)
                throw new IndexOutOfBoundsException(ExceptionsMessages.INVALID_HISTORY_CART_INDEX.getExceptionMessage());
        } catch (NumberFormatException e) {
            return ExceptionsMessages.INVALID_NUMBER_CHOICE.getExceptionMessage();
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
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

    public void addSeller(String username, String password) {
        Seller seller = new Seller(username, password);
        if (sellers.length == numberOfSellers) {
            if (sellers.length == 0) {
                sellers = Arrays.copyOf(sellers, 1);
            }
            sellers = Arrays.copyOf(sellers, sellers.length * SIZE_INCREASE);
        }
        sellers[numberOfSellers++] = seller;
    }

    public void addBuyer(String username, String password, Address address) {
        Buyer buyer = new Buyer(username, password, address);
        if (buyers.length == numberOfBuyers) {
            if (buyers.length == 0) {
                buyers = Arrays.copyOf(buyers, 1);
            }
            buyers = Arrays.copyOf(buyers, buyers.length * SIZE_INCREASE);
        }
        buyers[numberOfBuyers++] = buyer;
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

    public String productsByCategory() {
        if (numberOfSellers == 0) {
            return "\nHaven't sellers yet - no products available, cannot be proceed. return to Menu.";
        }
        return categoriesArrays.toString();
    }

    public String sellersNames() {
        StringBuilder sb = new StringBuilder("Seller's:\n--------------\n");
        for (int i = 0; i < numberOfSellers; i++) {
            sb.append(i + 1).append(") ").append(sellers[i].getUserName()).append("\n");
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

    public void addProductBuyer(int buyerIndex, int sellerIndex, int productIndex) {
        Product p1;
        if (sellers[sellerIndex].getProducts()[productIndex] instanceof ProductSpecialPackage) {
            p1 = new ProductSpecialPackage(sellers[sellerIndex].getProducts()[productIndex], ((ProductSpecialPackage) sellers[sellerIndex].getProducts()[productIndex]).getSpecialPackagePrice());
        } else {
            p1 = new Product(sellers[sellerIndex].getProducts()[productIndex]);
        }
        buyers[buyerIndex].getCurrentCart().addProductToCart(p1);
    }

    public void addProductSeller(int sellerIndex, String productName, double productPrice, Category c, double specialPackagePrice) {
        Product p1;
        if (specialPackagePrice == 0) {
            p1 = new Product(productName, productPrice, c);
        } else {
            p1 = new ProductSpecialPackage(productName, productPrice, c, specialPackagePrice);
        }
        sellers[sellerIndex].addProduct(p1);

        if (allProducts.length == logicProductsSize) {
            if (allProducts.length == 0) {
                allProducts = Arrays.copyOf(allProducts, 1);
            }
            allProducts = Arrays.copyOf(allProducts, allProducts.length * SIZE_INCREASE);

        }

        allProducts[logicProductsSize++] = p1;
        addToCategoryArray(p1);
    }

    public void addToCategoryArray(Product p) {
        switch (p.getCategory()) {
            case ELECTRONIC:
                categoriesArrays.addElectronic(p);
                break;
            case CHILDREN:
                categoriesArrays.addChild(p);
                break;
            case CLOTHES:
                categoriesArrays.addClothes(p);
                break;
            case OFFICE:
                categoriesArrays.addOffice(p);
                break;
            default:
                break;
        }
    }

    public void printArray() {
        for (int i = 0; i < logicProductsSize; i++) {
            System.out.println(allProducts[i].getProductName());
        }
    }

    public Map<String, Integer> toLinkedHashMap() {
        Map<String, Integer> map = new HashMap<>();
        for (Product p : allProducts) {
            if (p != null) {
                if (map.containsKey(p.getProductName().toLowerCase()))
                    map.put(p.getProductName().toLowerCase(), map.get(p.getProductName().toLowerCase()) + 1);
                else
                    map.put(p.getProductName().toLowerCase(), 1);
            }
        }
        return map;
    }

    public ArrayList<String> toArrayList(Map<String, Integer> map) {
        ArrayList<String> arr = new ArrayList<>();
        Set<String> set = map.keySet();
        Iterator<String> it2 = set.iterator();
        while (it2.hasNext()) {
            arr.add(it2.next());
        }
        return arr;
    }

    public void printByListIterator(List<?> lst) {
        ListIterator<?> it = lst.listIterator(lst.size());
        while (it.hasPrevious()) {
            System.out.println(it.previous());
            it.next();
            System.out.println(it.previous());
        }
    }

    public SortedSet<String> ToTreeSet() {
        SortedSet<String> sortedSet = new TreeSet<>((o1, o2) -> {
            String lowerO1 = o1.toLowerCase();
            String lowerO2 = o2.toLowerCase();

            if (lowerO1.equals(lowerO2)) {
                return 0;
            }
            int lengthDiff = lowerO1.length() - lowerO2.length();
            if (lengthDiff != 0) {
                return lengthDiff;
            }
            return lowerO1.compareTo(lowerO2);
        });
        for (int i = 0; i < logicProductsSize; i++) {
            sortedSet.add(allProducts[i].getProductName().toUpperCase());
        }
        return sortedSet;
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

}


