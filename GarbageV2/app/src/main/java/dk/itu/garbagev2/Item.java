package dk.itu.garbagev2;

public class Item {
    private String name;
    private String category;

    public Item(String name, String category) {
        this.name = name.toLowerCase();
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }
}
