package dk.itu.garbagev2;

import java.util.ArrayList;
import java.util.List;

public class ItemsDB {

    private static class InstanceHolder {
        private static final ItemsDB sItemsDB = new ItemsDB();
    }

    public static ItemsDB get() {
        return InstanceHolder.sItemsDB;
    }

    private final List<Item> items;

    ItemsDB(){
        items = new ArrayList<>();

        items.add(new Item("milk carton", "Food Carton"));
        items.add(new Item("newspapers", "Paper"));
        items.add(new Item("plastic bottle", "Plastic"));
        items.add(new Item("glass bottle", "Glass"));
        items.add(new Item("orange peel", "Food"));
        items.add(new Item("can", "Metal"));
        items.add(new Item("batteries","Hazardous"));
        items.add(new Item("clothes","Textile"));
    }

    public String findCategory(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return itemName+" should be placed in: " + item.getCategory();
            }
        }
        return itemName+" should be placed in: not found";
    }
    public void addItem(String what, String where){
        String new_where = where.substring(0, 1).toUpperCase() + where.substring(1).toLowerCase();
        items.add(new Item(what, new_where));
    }

}

