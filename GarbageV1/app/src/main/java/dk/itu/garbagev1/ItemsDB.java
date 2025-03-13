package dk.itu.garbagev1;

import java.util.ArrayList;
import java.util.List;

public class ItemsDB {
    private final List<Item> items;

    public ItemsDB(){
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
            if (item.getName().equals(itemName.toLowerCase())) {
                return itemName+" should be placed in: " + item.getCategory();
            }
        }
        return itemName+" should be placed in: not found";
    }
}

