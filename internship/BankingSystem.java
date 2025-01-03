import java.util.Scanner;
import java.io.*;

class Account{
    private String accountHolderName;
    private String accountNumber;
    private double balance;

    public Account(String accountHoldeName,String accountNumber,double initialBalance){
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    public void deposit(double amount){
        if(amount>0){
            balance += amount;
            System.out.println("Amount deposited successfully.");
        }else{
            System.out.println("Deposit amount must be greater than zero.");
        }
    }
    public void withdrawAmount(double amount){
        if(amount > 0 && amount <= balance){
            balance -= amount;
            System.out.println("Amount withdrawn successfully.");
        } else if(amount > balance){
            System.out.println("Insufficient balance.");
        } else{
            System.out.println("Withdrawal amount must be greater that zero.");
        }
    }
    public double getBalance(){
        return balance;
    }
    public String getAccountDetails(){
        return "Account Holder : "+ accountHolderName + "\nAccount Number: " + accountNumber + "\nBalance: " + balance;
    }
    public void saveAccountData(){
        try (FileWriter writer = new FileWriter(accountNumber + ".txt")){
            writer.write(getAccountDetails());
        } catch(IOException e){
            System.out.println("Error saving account data.");
        }
    }
}
public class BankingSystem {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter account holder name:");
        String name = sc.nextLine();

        System.out.println("Enter account number:");
        String accountNumber = sc.nextLine();

        System.out.println("Enter initial balance:");
        double initialBalance = sc.nextDouble();

        Account account = new Account(name,accountNumber,initialBalance);

        boolean exit = false;

        while(!exit){
            System.out.println("\n--- Banking System Menu ---");
            System.err.println("1. Deposit");
            System.out.println("2. withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Save Account Data");
            System.out.println("5. Exit");
            System.out.println("Enter your choice:");
            
            int choice = sc.nextInt();

            switch(choice){
                case 1:
                 System.out.println("Enter amount to deposit:");
                 double depositAmount = sc.nextDouble();
                 account.deposit(depositAmount);
                 System.out.println("Current Balance:"+account.getBalance());
                 break;
                
                case 2:
                 System.out.println("Enter amount to Withdraw:");
                 double withdrawAmount = sc.nextDouble();
                 account.withdrawAmount(withdrawAmount);
                 System.out.println("Current Balance:"+account.getBalance());
                 break;

                case 3:
                  System.out.println("Current Balance:"+account.getBalance());
                  break;

                case 4:
                 account.saveAccountData();
                 System.out.println("Account data saved successfull.");
                 break;  
                
                case 5:
                 exit = true;
                 System.out.println("Exiting Bankint System. Goodbye!");
                 break;
                 
                default:
                System.out.println("Invalid choice. Please try again."); 
            }
        }
        sc.close();
    }
}
