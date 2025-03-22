package Managers;

import Enums.Category;
import Models.Address;

public class Factory {
    public void tests(Manager manager) {

        manager.addSeller("alon", "123");
        manager.addBuyer("dor", "2929884", new Address("azrieli", "19", "natanya", "israel"));
        manager.addSeller("ofek", "33344444");
        manager.addSeller("daniel", "1029394");
        manager.addBuyer("itay", "29129291", new Address("afeka", "111", "tel aviv", "israel"));
        //manager.addBuyer("dor","2929884",new Address("azrieli","19","natanya", "israel"));
        manager.addBuyer("tal", "6574774", new Address("shalom", "4", "ness-Ziona", "israel"));
        manager.addProductSeller(0, "Ball", 100, Category.values()[0], 20);
        manager.addProductSeller(1, "baLl", 100, Category.values()[0], 20);
        manager.addProductSeller(1, "TV", 1200, Category.values()[1], 50);
        manager.addProductSeller(2, "pen", 13, Category.values()[2], 0);
        manager.addProductSeller(2, "pEn", 13, Category.values()[2], 0);
        manager.addProductSeller(0, "coat", 350, Category.values()[3], 35);
        manager.addProductBuyer(0, 0, 0);
        manager.addProductBuyer(1, 0, 1);
        manager.addProductBuyer(2, 1, 0);
        manager.addProductBuyer(0, 2, 0);


    }
}