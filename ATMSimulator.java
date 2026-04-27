import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMSimulator {
    // Your original logic variables
    private static double balance = 10000.0;
    private static final int PIN = 1234;
    private static int attempts = 0;

    public static void main(String[] args) {
        // Create the main window
        JFrame frame = new JFrame("Simple ATM App");
        frame.setSize(350, 450);
        frame.setLayout(new CardLayout()); // Allows us to switch screens
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- SCREEN 1: LOGIN ---
        JPanel loginPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel titleLabel = new JLabel("Welcome to Simple ATM", SwingConstants.CENTER);
        JLabel instructionLabel = new JLabel("Enter 4-digit PIN:", SwingConstants.CENTER);
        JPasswordField pinField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(titleLabel);
        loginPanel.add(instructionLabel);
        loginPanel.add(pinField);
        loginPanel.add(loginButton);

        // --- SCREEN 2: MENU ---
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JLabel welcomeLabel = new JLabel("Select an Option", SwingConstants.CENTER);
        JButton btnBalance = new JButton("Check Balance");
        JButton btnDeposit = new JButton("Deposit Money");
        JButton btnWithdraw = new JButton("Withdraw Money");
        JButton btnExit = new JButton("Exit");

        menuPanel.add(welcomeLabel);
        menuPanel.add(btnBalance);
        menuPanel.add(btnDeposit);
        menuPanel.add(btnWithdraw);
        menuPanel.add(btnExit);

        // Add both screens to the frame
        frame.add(loginPanel, "Login");
        frame.add(menuPanel, "Menu");

        CardLayout cl = (CardLayout) (frame.getContentPane().getLayout());

        // LOGIN BUTTON LOGIC
        loginButton.addActionListener(e -> {
            String entered = new String(pinField.getPassword());
            if (entered.equals(String.valueOf(PIN))) {
                cl.show(frame.getContentPane(), "Menu");
            } else {
                attempts++;
                if (attempts >= 3) {
                    JOptionPane.showMessageDialog(frame, "Card Blocked!");
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(frame, "Incorrect PIN. Attempts left: " + (3 - attempts));
            }
        });

        // MENU BUTTON LOGIC
        btnBalance.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Current Balance: $" + balance));

        btnDeposit.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
            if (amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                balance += amount;
                JOptionPane.showMessageDialog(frame, "Successfully deposited $" + amount);
            }
        });

        btnWithdraw.addActionListener(e -> {
            String amountStr = JOptionPane.showInputDialog(frame, "Enter withdrawal amount:");
            if (amountStr != null) {
                double amount = Double.parseDouble(amountStr);
                if (amount <= balance) {
                    balance -= amount;
                    JOptionPane.showMessageDialog(frame, "Please collect your cash: $" + amount);
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds!");
                }
            }
        });

        btnExit.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }
}