package dk.itu.garbagev5;

import java.util.HashMap;

public class ItemsDB {

    private static ItemsDB sItemsDB;
    private final HashMap<String, String> itemsMap = new HashMap<>();

    private ItemsDB() { fillItemsDB(); }
    public static ItemsDB get() {
        if (sItemsDB == null) sItemsDB = new ItemsDB();
        return sItemsDB;
    }

    // search for category
    public String findCategory(String itemName) {
        String category = itemsMap.get(itemName.toLowerCase());
        if (category != null) {
            return itemName + " should be placed in: " + category;//
        }
        return itemName + " should be placed in: not found";
    }

    // add new items
    public void addItem(String what, String where) {
        itemsMap.put(what.toLowerCase(), where.toLowerCase());
    }

    // Item list
    public String listItems(){
        String r = "";
        for (HashMap.Entry <String, String> item: itemsMap.entrySet())
            r = r + "\n "+item.getKey() + " in "+item.getValue();
        return r;
    }

    private void fillItemsDB(){
        itemsMap.put("newspaper","Paper");
        itemsMap.put("can","Metal");
        itemsMap.put("magazine", "Paper");
        itemsMap.put("milk carton","Food");
        itemsMap.put("stone box", "Cardboard");
    }

}
