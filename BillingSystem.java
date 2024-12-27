
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
 
public class BillingSystem {
    private Map<String, Double> Accounts;
    private Map<String, Integer> Stocks;

 
    public BillingSystem() {
        Accounts = new HashMap<>();
        Stocks = new HashMap<>();
        SetupStock();
 
        for ( Map.Entry<String, Integer> me : Stocks.entrySet() ) {
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
    }
 
    public void SetupStock() {
        Stocks.put("Milk", 10);
        Stocks.put("Chips", 20);
        Stocks.put("Chocolate", 5);
        Stocks.put("Biscuits", 30);
    }
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BillingSystem billingSystem = new BillingSystem();
 
        while (true) {
            System.out.println("\n\n=== Super Market Billing System ===");
            System.out.println("\t1. Create Customer Account");
            System.out.println("\t2. Fetch Customer Balance");
            System.out.println("\t3. Perform Billing");
            System.out.println("\t4. Exit");
            System.out.print("\nEnter your choice: ");
 
            int choice = scanner.nextInt(); scanner.nextLine();
 
            switch ( choice ) {
                case 1:
                    System.out.println("\n\n=== Creating New Customer Account ===");
 
                    System.out.print("\tEnter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("\tEnter Initial Balance: ");
                    double customerBalance = scanner.nextInt();
 
                    billingSystem.Accounts.put(customerName, customerBalance);
                    System.out.println("\tAccount Created!");
                    break;
                case 2: 
                    System.out.println("\n\n=== Fetching Account Balance ===");
 
                    System.out.print("\tEnter Customer Name: ");
                    String name = scanner.nextLine();
                    double balance = billingSystem.Accounts.getOrDefault(name, 0.0);
 
                    if ( balance == 0.0 ) {
                        System.out.println("\tAccount Not Found");
                    } else {
                        System.out.print("\tUser Balance is: "); System.out.println(balance);
                    }
                    break;
                
                case 3: 
                    System.out.println("\n\n=== Start Billing ===");
                    System.out.println("Enter `done` once all items have been entered.\n");
                    int itemCount = 0; double totalCost = 0.0; 
                    while (true) {
                        System.out.print("\tEnter item name: ");
                        String itemName = scanner.nextLine();
                        if ( billingSystem.Stocks.containsKey(itemName) ) {
                            System.err.print("\tEnter Quantity: ");
                            int quantity = scanner.nextInt(); scanner.nextLine();
                            itemCount += 1; totalCost += ( quantity * billingSystem.Stocks.get(itemName) );
                            System.out.println(totalCost);
                        } else if ( itemName.contains("done") ) {
                            break;
                        } else {
                            System.out.println("\n\t\t[!] Item not in stock [!]");
                        }
                    }
                    System.out.println("\nBilling Done! Select Payment Method: ");
                    System.out.println("\t1. Cashier");
                    System.out.println("\t2. Supermarket Account");
                    System.out.print("\nEnter your choice: ");
 
                    int paymentMode = scanner.nextInt(); scanner.nextLine();
                    
                    System.out.println("\tAmount: " + totalCost);
                    System.out.println("\tItems Purchased: " + itemCount);
 
                    switch ( paymentMode ) {
                        case 1: 
                            System.out.println("\nPayment to be done at Cashier.");
                            break;
                        
                        case 2:
                            System.err.println("\nEnter User's Name: ");
                            String username = scanner.nextLine();
                            
                            if ( billingSystem.Accounts.containsKey(username) ) {
                                double userBalance = billingSystem.Accounts.get(username);
                                if ( userBalance > totalCost ) {
                                    System.out.println("\tBilling Complete!");
                                    billingSystem.Accounts.replace(username, (userBalance - totalCost));
                                    System.out.println("\tAmount deducted from user account.");
                                    break;
                                } else {
                                    System.out.println("\t[!] Insufficient Balance in account! Please pay with cash.");
                                    break;
                                }
                            } else {
                                System.out.println("\t[!] Account was not found! Please pay with cash.");
                                break;
                            }
                    }
                    break;
                
                default: 
                    continue;
            }
        }
        
    }
    
}