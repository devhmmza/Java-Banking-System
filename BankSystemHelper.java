import java.util.ArrayList;

public class BankSystemHelper {
    private BankSystem bankSystem;
    private ArrayList<User> users;

    public BankSystemHelper() {
        bankSystem = new BankSystem();
        users = bankSystem.getUsers(); 
    }

    
    public boolean registerUser(String username, String password) {
        
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; 
            }
        }
        users.add(new User(username, password));
        DataManager.saveData(users); 
        return true;
    }

    public User loginUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }

    public boolean createAccount(User user, String type, String accNum, double balance) {
        Account account = null;
        if (type.equalsIgnoreCase("savings")) {
            account = new SavingAccount(user.getUsername(), accNum, balance);
        } else if (type.equalsIgnoreCase("current")) {
            account = new CurrentAccount(user.getUsername(), accNum, balance);
        } else {
            return false; 
        }
        user.addAccount(account);
        DataManager.saveData(users);
        return true;
    }

    public ArrayList<Account> getUserAccounts(User user) {
        return user.getAccounts();
    }

    public boolean deposit(User user, String accNum, double amount) {
        Account account = user.getAccountByNumber(accNum);
        if (account != null) {
            account.deposit(amount);
            DataManager.saveData(users);
            return true;
        }
        return false;
    }

    public boolean withdraw(User user, String accNum, double amount) {
        Account account = user.getAccountByNumber(accNum);
        if (account != null) {
            account.withdraw(amount);
            DataManager.saveData(users);
            return true;
        }
        return false;
    }
}
