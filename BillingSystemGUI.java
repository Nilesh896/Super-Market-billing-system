import javax.swing.*;
import java.awt.*;

import java.util.HashMap;
import java.util.Map;

public class BillingSystemGUI {
    private Map<String, Double> accounts = new HashMap<>();
    private Map<String, Double> stocks = new HashMap<>();
    private Map<String, Double> discounts = new HashMap<>();
    private JTextArea stockDisplayArea;

    public BillingSystemGUI() {
        initializeStocks();
        createAndShowGUI();
    }

    private void initializeStocks() {
        stocks.put("Milk", 1.5);
        stocks.put("Chips", 0.75);
        stocks.put("Chocolate", 2.0);
        stocks.put("Biscuits", 1.0);
        stocks.put("Juice", 2.5);
        stocks.put("Bread", 1.2);
        stocks.put("Eggs", 3.0);
        stocks.put("Butter", 2.8);

        discounts.put("Chips", 0.10); // 10% discount
        discounts.put("Chocolate", 0.20); // 20% discount
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Supermarket Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel title = new JLabel("Supermarket Billing System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        // Center Panel for Stock Display
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        stockDisplayArea = new JTextArea(10, 40);
        stockDisplayArea.setEditable(false);
        refreshStockDisplay();
        JScrollPane scrollPane = new JScrollPane(stockDisplayArea);
        centerPanel.add(new JLabel("Available Items:"), BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3, 10, 10));

        JButton createAccountButton = new JButton("Create Account");
        JButton viewBalanceButton = new JButton("View Balance");
        JButton purchaseButton = new JButton("Purchase Items");
        JButton updateStockButton = new JButton("Update Stock");
        JButton refreshStockButton = new JButton("Refresh Stock");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(createAccountButton);
        buttonPanel.add(viewBalanceButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(updateStockButton);
        buttonPanel.add(refreshStockButton);
        buttonPanel.add(exitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        createAccountButton.addActionListener(e -> createAccount());
        viewBalanceButton.addActionListener(e -> viewBalance());
        purchaseButton.addActionListener(e -> purchaseItems());
        updateStockButton.addActionListener(e -> updateStock());
        refreshStockButton.addActionListener(e -> refreshStockDisplay());
        exitButton.addActionListener(e -> System.exit(0));

        // Show Frame
        frame.setVisible(true);
    }

    private void createAccount() {
        String name = JOptionPane.showInputDialog("Enter Customer Name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer name cannot be empty.");
            return;
        }
        if (accounts.containsKey(name)) {
            JOptionPane.showMessageDialog(null, "Account already exists for this customer.");
            return;
        }
        String balanceStr = JOptionPane.showInputDialog("Enter Initial Balance:");
        try {
            double balance = Double.parseDouble(balanceStr);
            accounts.put(name, balance);
            JOptionPane.showMessageDialog(null, "Account created successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid balance amount.");
        }
    }

    private void viewBalance() {
        String name = JOptionPane.showInputDialog("Enter Customer Name:");
        if (accounts.containsKey(name)) {
            double balance = accounts.get(name);
            JOptionPane.showMessageDialog(null, "Balance: $" + balance);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }
    private void purchaseItems() {
        String customerName = JOptionPane.showInputDialog("Enter Customer Name:");
        if (customerName == null || customerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer name cannot be empty.");
            return;
        }
    
        if (!accounts.containsKey(customerName)) {
            JOptionPane.showMessageDialog(null, "Account not found! Please create an account first.");
            return;
        }
    
        StringBuilder receipt = new StringBuilder("Supermarket Purchase Receipt\n");
        receipt.append("Customer: ").append(customerName).append("\n\n");
        double totalCost = 0.0;
    
        while (true) {
            String itemName = JOptionPane.showInputDialog("Enter Item Name (or 'done' to finish):");
            if (itemName == null || "done".equalsIgnoreCase(itemName)) {
                break;
            }
    
            if (!stocks.containsKey(itemName)) {
                JOptionPane.showMessageDialog(null, "Item not in stock.");
                continue;
            }
    
            String quantityStr = JOptionPane.showInputDialog("Enter Quantity:");
            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = stocks.get(itemName);
                double discount = discounts.getOrDefault(itemName, 0.0);
                double discountedPrice = price - (price * discount);
                double cost = discountedPrice * quantity;
    
                receipt.append(String.format("%s x %d = $%.2f\n", itemName, quantity, cost));
                totalCost += cost;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid quantity.");
            }
        }
    
        receipt.append(String.format("\nTotal Cost: $%.2f\n", totalCost));
        receipt.append(String.format("Balance Before: $%.2f\n", accounts.get(customerName)));
    
        int paymentOption = JOptionPane.showConfirmDialog(null, "Would you like to pay using your account balance?");
        if (paymentOption == JOptionPane.YES_OPTION) {
            double currentBalance = accounts.get(customerName);
            if (currentBalance >= totalCost) {
                accounts.put(customerName, currentBalance - totalCost);
                receipt.append(String.format("New Balance: $%.2f\n", accounts.get(customerName)));
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient balance! Please pay with cash.");
                receipt.append("Payment Method: Cash\n");
            }
        } else {
            receipt.append("Payment Method: Cash\n");
        }
    
        JOptionPane.showMessageDialog(null, receipt.toString());
    }
    
    private void updateStock() {
        String itemName = JOptionPane.showInputDialog("Enter Item Name:");
        if (itemName == null || itemName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item name cannot be empty.");
            return;
        }

        String priceStr = JOptionPane.showInputDialog("Enter Item Price:");
        try {
            double price = Double.parseDouble(priceStr);
            stocks.put(itemName, price);
            refreshStockDisplay();
            JOptionPane.showMessageDialog(null, "Stock updated successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price.");
        }
    }

    private void refreshStockDisplay() {
        StringBuilder stockText = new StringBuilder();
        stocks.forEach((item, price) -> {
            double discount = discounts.getOrDefault(item, 0.0);
            double discountedPrice = price - (price * discount);
            stockText.append(String.format("%s: $%.2f (Discount: $%.2f)\n", item, discountedPrice, price * discount));
        });
        stockDisplayArea.setText(stockText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BillingSystemGUI::new);
    }
}
