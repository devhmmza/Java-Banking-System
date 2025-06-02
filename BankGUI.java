import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankGUI {
    private BankSystemHelper helper;
    private JFrame frame;
    private User currentUser;

    public BankGUI() {
        helper = new BankSystemHelper();
        showMainMenu();
    }

    private void showMainMenu() {
        frame = new JFrame("Bank System - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));

        JLabel welcome = new JLabel("Welcome to the Bank System", SwingConstants.CENTER);
        JButton registerBtn = new JButton("Register");
        JButton loginBtn = new JButton("Login");
        JButton exitBtn = new JButton("Exit");

        registerBtn.addActionListener(e -> showRegisterScreen());
        loginBtn.addActionListener(e -> showLoginScreen());
        exitBtn.addActionListener(e -> frame.dispose());

        frame.add(welcome);
        frame.add(registerBtn);
        frame.add(loginBtn);
        frame.add(exitBtn);

        frame.setVisible(true);
    }

    private void showRegisterScreen() {
        JFrame regFrame = new JFrame("Register");
        regFrame.setSize(300, 200);
        regFrame.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        registerBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (helper.registerUser(username, password)) {
                JOptionPane.showMessageDialog(regFrame, "User registered successfully!");
                regFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(regFrame, "Username already exists.");
            }
        });

        backBtn.addActionListener(e -> regFrame.dispose());

        regFrame.add(userLabel);
        regFrame.add(userField);
        regFrame.add(passLabel);
        regFrame.add(passField);
        regFrame.add(new JLabel());
        regFrame.add(new JLabel());
        regFrame.add(registerBtn);
        regFrame.add(backBtn);

        regFrame.setVisible(true);
    }

    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(4, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            currentUser = helper.loginUser(username, password);
            if (currentUser != null) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.");
            }
        });

        backBtn.addActionListener(e -> loginFrame.dispose());

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(passLabel);
        loginFrame.add(passField);
        loginFrame.add(new JLabel());
        loginFrame.add(new JLabel());
        loginFrame.add(loginBtn);
        loginFrame.add(backBtn);

        loginFrame.setVisible(true);
    }

    private void showDashboard() {
        JFrame dashFrame = new JFrame("User Dashboard");
        dashFrame.setSize(400, 300);
        dashFrame.setLayout(new GridLayout(6, 1));

        JLabel welcome = new JLabel("Welcome, " + currentUser.getUsername(), SwingConstants.CENTER);
        JButton createAccBtn = new JButton("Create Account");
        JButton viewAccBtn = new JButton("View Accounts");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        createAccBtn.addActionListener(e -> showCreateAccount());
        viewAccBtn.addActionListener(e -> showViewAccounts());
        depositBtn.addActionListener(e -> showDepositScreen());
        withdrawBtn.addActionListener(e -> showWithdrawScreen());
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            dashFrame.dispose();
        });

        dashFrame.add(welcome);
        dashFrame.add(createAccBtn);
        dashFrame.add(viewAccBtn);
        dashFrame.add(depositBtn);
        dashFrame.add(withdrawBtn);
        dashFrame.add(logoutBtn);

        dashFrame.setVisible(true);
    }

    private void showCreateAccount() {
        JFrame createFrame = new JFrame("Create Account");
        createFrame.setSize(300, 250);
        createFrame.setLayout(new GridLayout(5, 2));

        JLabel typeLabel = new JLabel("Type (savings/current):");
        JTextField typeField = new JTextField();
        JLabel numLabel = new JLabel("Account Number:");
        JTextField numField = new JTextField();
        JLabel balLabel = new JLabel("Initial Balance:");
        JTextField balField = new JTextField();
        JButton createBtn = new JButton("Create");
        JButton backBtn = new JButton("Back");

        createBtn.addActionListener(e -> {
            String type = typeField.getText();
            String num = numField.getText();
            double bal = Double.parseDouble(balField.getText());
            if (helper.createAccount(currentUser, type, num, bal)) {
                JOptionPane.showMessageDialog(createFrame, "Account created!");
                createFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(createFrame, "Invalid account type!");
            }
        });

        backBtn.addActionListener(e -> createFrame.dispose());

        createFrame.add(typeLabel);
        createFrame.add(typeField);
        createFrame.add(numLabel);
        createFrame.add(numField);
        createFrame.add(balLabel);
        createFrame.add(balField);
        createFrame.add(new JLabel());
        createFrame.add(new JLabel());
        createFrame.add(createBtn);
        createFrame.add(backBtn);

        createFrame.setVisible(true);
    }

    private void showViewAccounts() {
        JFrame viewFrame = new JFrame("Your Accounts");
        viewFrame.setSize(400, 300);
        JTextArea area = new JTextArea();
        area.setEditable(false);
        StringBuilder info = new StringBuilder();
        for (Account a : helper.getUserAccounts(currentUser)) {
            info.append(a.toString()).append("\n\n");
        }
        area.setText(info.toString().isEmpty() ? "No accounts found." : info.toString());
        JScrollPane scroll = new JScrollPane(area);
        viewFrame.add(scroll);
        viewFrame.setVisible(true);
    }

    private void showDepositScreen() {
        JFrame depFrame = new JFrame("Deposit");
        depFrame.setSize(300, 200);
        depFrame.setLayout(new GridLayout(4, 2));

        JLabel accLabel = new JLabel("Account Number:");
        JTextField accField = new JTextField();
        JLabel amtLabel = new JLabel("Amount:");
        JTextField amtField = new JTextField();
        JButton depBtn = new JButton("Deposit");
        JButton backBtn = new JButton("Back");

        depBtn.addActionListener(e -> {
            String accNum = accField.getText();
            double amount = Double.parseDouble(amtField.getText());
            if (helper.deposit(currentUser, accNum, amount)) {
                JOptionPane.showMessageDialog(depFrame, "Deposit successful!");
                depFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(depFrame, "Account not found!");
            }
        });

        backBtn.addActionListener(e -> depFrame.dispose());

        depFrame.add(accLabel);
        depFrame.add(accField);
        depFrame.add(amtLabel);
        depFrame.add(amtField);
        depFrame.add(new JLabel());
        depFrame.add(new JLabel());
        depFrame.add(depBtn);
        depFrame.add(backBtn);

        depFrame.setVisible(true);
    }

    private void showWithdrawScreen() {
        JFrame withFrame = new JFrame("Withdraw");
        withFrame.setSize(300, 200);
        withFrame.setLayout(new GridLayout(4, 2));

        JLabel accLabel = new JLabel("Account Number:");
        JTextField accField = new JTextField();
        JLabel amtLabel = new JLabel("Amount:");
        JTextField amtField = new JTextField();
        JButton withBtn = new JButton("Withdraw");
        JButton backBtn = new JButton("Back");

        withBtn.addActionListener(e -> {
            String accNum = accField.getText();
            double amount = Double.parseDouble(amtField.getText());
            if (helper.withdraw(currentUser, accNum, amount)) {
                JOptionPane.showMessageDialog(withFrame, "Withdrawal successful!");
                withFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(withFrame, "Account not found or insufficient funds!");
            }
        });

        backBtn.addActionListener(e -> withFrame.dispose());

        withFrame.add(accLabel);
        withFrame.add(accField);
        withFrame.add(amtLabel);
        withFrame.add(amtField);
        withFrame.add(new JLabel());
        withFrame.add(new JLabel());
        withFrame.add(withBtn);
        withFrame.add(backBtn);

        withFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankGUI::new);
    }
}
