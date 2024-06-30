import java.util.Scanner;

class Account {
    private String accountNumber;
    private double balance;
    private int pin;

    public Account(String accountNumber, int pin) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.pin = pin;
    }

    public void deposit(double amount, int enteredPin) {
        if (verifyPin(enteredPin)) {
            balance += amount;
            System.out.println("Deposit of $" + amount + " successful.");
        } else {
            System.out.println("Incorrect PIN. Deposit failed.");
        }
    }

    public void withdraw(double amount, int enteredPin) {
        if (verifyPin(enteredPin)) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrawal of $" + amount + " successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Incorrect PIN. Withdrawal failed.");
        }
    }

    public void applyForLoan(double loanAmount, int enteredPin) {
        if (verifyPin(enteredPin)) {
            balance += loanAmount;
            System.out.println("Loan of $" + loanAmount + " credited to your account.");
        } else {
            System.out.println("Incorrect PIN. Loan application failed.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean verifyPin(int enteredPin) {
        return pin == enteredPin;
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, int pin, double interestRate) {
        super(accountNumber, pin);
        this.interestRate = interestRate;
    }
}

class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, int pin, double overdraftLimit) {
        super(accountNumber, pin);
        this.overdraftLimit = overdraftLimit;
    }
}

public class BankingSystem {
    private static final String ACCOUNT_PREFIX = "ACC";
    private static int accountNumberCounter = 1000; // Starting account number

    private static String generateAccountNumber() {
        return ACCOUNT_PREFIX + accountNumberCounter++;
    }

    private static boolean validateDefaultCredentials(String enteredAccountNumber, int enteredPin) {
        return enteredAccountNumber.equals("AC100") && enteredPin == 1234;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to the Banking System");

        // Ask user if they have an account in the bank
        System.out.println("Do you have an account in this bank? (yes/no):");
        String hasAccount = scanner.nextLine();

        String accountNumber;
        int pin;
        if (hasAccount.equalsIgnoreCase("yes")) {
            // Prompt user for account number
            System.out.println("Enter your account number:");
            accountNumber = scanner.nextLine();

            // Prompt user for PIN
            System.out.println("Enter your PIN:");
            pin = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            
            // Validate entered credentials
            if (!validateDefaultCredentials(accountNumber, pin)) {
                System.out.println("Wrong account number or PIN. Please enter valid credentials.");
                scanner.close();
                return;
            }
        } else if (hasAccount.equalsIgnoreCase("no")) {
            // Generate a new account number
            accountNumber = generateAccountNumber();
            System.out.println("Your new account number is: " + accountNumber);

            // Prompt user to set PIN
            System.out.println("Set your PIN:");
            pin = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            scanner.close();
            return;
        }

        // Prompt user to choose account type
        System.out.println("Choose account type (1. Savings, 2. Current):");
        int accountType = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Create account based on user choice
        Account account = null;
        switch (accountType) {
            case 1:
                account = new SavingsAccount(accountNumber, pin, 3.5); // Example interest rate: 3.5%
                break;
            case 2:
                account = new CurrentAccount(accountNumber, pin, 1000); // Example overdraft limit: 1000
                break;
            default:
                System.out.println("Invalid account type.");
                System.exit(1);
        }

        int choice;
        // Display menu and process user choices
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Apply for Loan");
            System.out.println("5. Exit");
            System.out.println("Enter the operation you want to do:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter deposit amount:");
                    double depositAmount = scanner.nextDouble();
                    System.out.println("Enter your PIN:");
                    int enteredPinDeposit = scanner.nextInt();
                    account.deposit(depositAmount, enteredPinDeposit);
                    break;
                case 2:
                    System.out.println("Enter withdrawal amount:");
                    double withdrawAmount = scanner.nextDouble();
                    System.out.println("Enter your PIN:");
                    int enteredPinWithdraw = scanner.nextInt();
                    account.withdraw(withdrawAmount, enteredPinWithdraw);
                    break;
                case 3:
                    System.out.println("Enter your PIN:");
                    int enteredPinBalance = scanner.nextInt();
                    if (account.verifyPin(enteredPinBalance)) {
                        System.out.println("Current balance: $" + account.getBalance());
                    } else {
                        System.out.println("Incorrect PIN.");
                    }
                    break;
                case 4:
                    System.out.println("Enter loan amount:");
                    double loanAmount = scanner.nextDouble();
                    System.out.println("Enter your PIN:");
                    int enteredPinLoan = scanner.nextInt();
                    account.applyForLoan(loanAmount, enteredPinLoan);
                    break;
                case 5:
                    System.out.println("Thank you for using the Banking System.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        scanner.close(); // Close scanner
   }
}
