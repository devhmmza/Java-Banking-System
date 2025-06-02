public class CurrentAccount extends Account {
    public CurrentAccount(String accountHolderName, String accountNumber, double balance) {
        super(accountHolderName, accountNumber, balance);
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }
}
