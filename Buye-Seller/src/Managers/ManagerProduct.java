package Managers;

import java.util.*;

import Enums.Category;
import Enums.ExceptionsMessages;
import Models.Categories;
import Models.Product;

public class ManagerProduct implements ProductInterface {


    private Set<Observer> set = new HashSet<>();

    private final Categories categoriesArrays;

    Product[] allProducts;

    private int logicProductsSize = 0;

    private final int SIZE_INCREASE = 2;

    private static ManagerProduct instance;

    public static ManagerProduct getInstance() {
        if (instance == null)
            instance = new ManagerProduct();
        return instance;
    }

    private ManagerProduct() {

        categoriesArrays = new Categories();

        allProducts = new Product[0];


    }

    public void attach(Observer o) {
        set.add(o);
    }

    public void myNotify(String msg) {
        for (Observer o : set)
            o.update(msg);
    }

    public int getLogicProductsSize() {
        return logicProductsSize;
    }

    public Product[] getAllProducts() {
        return allProducts;
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


    public void increaseArray() {

        if (allProducts.length == logicProductsSize) {
            if (allProducts.length == 0) {
                allProducts = Arrays.copyOf(allProducts, 1);
            } else {
                allProducts = Arrays.copyOf(allProducts, allProducts.length * SIZE_INCREASE);
            }
        }


    }

    public void updateLogicSize() {

        logicProductsSize++;


    }

    public String productsByCategory() {
        if (ManagerSeller.getInstance().getNumberOfSellers() == 0) {
            return "\nHaven't sellers yet - no products available, cannot be proceed. return to Menu.";
        }
        return categoriesArrays.toString();
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

    public ListIterator<String> listIterator() {
        return new MyListIterator(0);
    }

    public Iterator<String> iterator() {
        return new ConcreteIterator();
    }

    private class ConcreteIterator implements Iterator<String> {
        int cur = 0;

        @Override
        public boolean hasNext() {
            if (cur < logicProductsSize) {
                return true;

            } else {

                myNotify("My Iterator Ended  ");
                return false;
            }
        }

        @Override
        public String next() {
            if (!hasNext())
                throw new NoSuchElementException();
            String tmp = allProducts[cur].getProductName().toLowerCase();

            cur++;
            return tmp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

    }


    private class MyListIterator extends ConcreteIterator implements ListIterator<String> {

        MyListIterator(int index) {
            cur = index;
        }

        @Override
        public boolean hasNext() {
            if (cur < logicProductsSize) {
                return true;

            } else {

                myNotify("My ListIterator Ended  ");
                return false;
            }


        }


        @Override
        public boolean hasPrevious() {
            if (cur > 0) {
                return true;

            } else {

                myNotify("My ListIterator Ended  ");
                return false;
            }


        }


        @Override
        public String previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            cur--;

            String tmp = allProducts[cur].getProductName().toLowerCase();
            return tmp;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();

        }

        @Override
        public void set(String e) {
            throw new UnsupportedOperationException();

        }

        @Override
        public void add(String e) {
            throw new UnsupportedOperationException();

        }

    }

}
