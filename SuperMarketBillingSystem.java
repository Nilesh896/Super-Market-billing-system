import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SuperMarketBillingSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<Integer, Item> itemMap = new HashMap<>();
        itemMap.put(1, new Item("Apple", 5.0));
        itemMap.put(2, new Item("Banana", 10.0));
        itemMap.put(3, new Item("Chocolate", 7.5));

        System.out.println("Enter the number of items:");
        int n = scanner.nextInt();

        double totalBill = 0;

        for (int i = 0; i < n; i++) {
            System.out.println("Enter the item code:");
            int itemCode = scanner.nextInt();

            Item item = itemMap.get(itemCode);
            if (item == null) {
                System.out.println("Invalid item code.");
                i--;
                continue;
            }

            System.out.println("Enter the quantity:");
            int quantity = scanner.nextInt();

            double bill = item.getPrice() * quantity;
            totalBill += bill;

            System.out.println("Bill for item " + item.getName() + " with quantity " + quantity + " is: " + bill);
        }

        System.out.println("Total bill: " + totalBill);
    }
}

class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}