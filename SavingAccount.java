public class SavingAccount extends Account{
    public SavingAccount(String accountHolderName, String accountNumber, double balance){
        super(accountHolderName, accountNumber, balance);
    }
    @Override
    public String getAccountType(){
        return "Savings Account";
    }
}