package dk.itu.garbagev3;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ItemsDB {

    private static ItemsDB sItemsDB;
    private final HashMap<String, Item> itemsMap = new HashMap<>();

    public static ItemsDB get() {
        if (sItemsDB == null) sItemsDB = new ItemsDB();
        return sItemsDB;
    }

    public void fillItemsDB(Context context, String filename) {
        // context cannot be empty
        if(context == null){
            throw new IllegalStateException("context must be set first");
        }
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));

            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String category = parts[1].trim();
                    itemsMap.put(name, new Item(name, category));
                    //itemsMap.put(name.toLowerCase(), category.toLowerCase());
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            Log.e("ItemsDB", "An error occurred: " + e.getMessage());
        }
    }

    // search for category
    public String findCategory(String itemName) {
        Item item = itemsMap.get(itemName.toLowerCase());
        //String category = itemsMap.get(itemName.toLowerCase());
        if (item != null) {
            return itemName + " should be placed in: " + item.getCategory();//+category
        }
        return itemName + " should be placed in: not found";
    }

    // add new items
    public void addItem(String what, String where) {
        itemsMap.put(what.toLowerCase(), new Item(what, where.toLowerCase()));
        //itemsMap.put(what.toLowerCase(), where.toLowerCase());
    }
}

