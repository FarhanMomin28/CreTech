import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {
        AccountManager manager = new AccountManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Banking Application ====");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add new customer and account");
            System.out.println("2. View customer details by ID");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Balance Inquiry");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial account balance: ");
                    double balance;
                    try {
                        balance = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid balance.");
                        break;
                    }
                    int id = manager.addCustomerWithAccount(name, balance);
                    System.out.println("Customer and account added successfully! Customer ID: " + id);
                    break;

                case 2:
                    System.out.print("Enter customer ID: ");
                    int viewId = Integer.parseInt(scanner.nextLine());
                    manager.getCustomerDetails(viewId);
                    break;

                case 3:
                    System.out.print("Enter customer ID: ");
                    int depId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter amount to deposit: ");
                    double depAmt = Double.parseDouble(scanner.nextLine());
                    manager.deposit(depId, depAmt);
                    break;

                case 4:
                    System.out.print("Enter customer ID: ");
                    int witId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter amount to withdraw: ");
                    double witAmt = Double.parseDouble(scanner.nextLine());
                    manager.withdraw(witId, witAmt);
                    break;

                case 5:
                    System.out.print("Enter customer ID: ");
                    int balId = Integer.parseInt(scanner.nextLine());
                    manager.checkBalance(balId);
                    break;

                case 6:
                    manager.close();
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
