import java.util.ArrayList;
import java.util.Scanner;

public class BankSystem {
    private ArrayList<User> users;
    private Scanner scanner;

    public BankSystem() {
        scanner = new Scanner(System.in);
        users = DataManager.loadData(); 
    }
    public ArrayList<User> getUsers() {
    return users;
}


    public void start() {
        while (true) {
            System.out.println("\n--- Welcome to the Bank System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Thank you for using the Bank System!");
                    DataManager.saveData(users); 
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("User registered successfully!");
        DataManager.saveData(users); 
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                System.out.println("Login successful!");
                userMenu(u);
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    private void userMenu(User user) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. View Accounts");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> createAccount(user);
                case 2 -> viewAccounts(user);
                case 3 -> deposit(user);
                case 4 -> withdraw(user);
                case 5 -> {
                    System.out.println("Logging out...");
                    DataManager.saveData(users); // Save data when logging out
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void createAccount(User user) {
        System.out.print("Enter account type (savings/current): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        Account account = null;
        if (type.equals("savings")) {
            account = new SavingAccount(user.getUsername(), accNum, balance);
        } else if (type.equals("current")) {
            account = new CurrentAccount(user.getUsername(), accNum, balance);
        } else {
            System.out.println("Invalid account type!");
            return;
        }

        user.addAccount(account);
        System.out.println(type + " account created!");
        DataManager.saveData(users); // Save data after creating account
    }

    private void viewAccounts(User user) {
        ArrayList<Account> accounts = user.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
        } else {
            for (Account a : accounts) {
                System.out.println("\n" + a);
            }
        }
    }

    private void deposit(User user) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Account account = user.getAccountByNumber(accNum);
        if (account != null) {
            account.deposit(amount);
            DataManager.saveData(users); // Save after deposit
        } else {
            System.out.println("Account not found!");
        }
    }

    private void withdraw(User user) {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Account account = user.getAccountByNumber(accNum);
        if (account != null) {
            account.withdraw(amount);
            DataManager.saveData(users); 
        } else {
            System.out.println("Account not found!");
        }
    }

    public static void main(String[] args) {
        BankSystem system = new BankSystem();
        system.start();
    }
}
