import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private SessionFactory factory;

    public AccountManager() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    // Add customer
    public int addCustomerWithAccount(String customerName, double initialBalance) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Customer customer = new Customer();
        customer.setName(customerName);

        BankAccount account = new BankAccount();
        account.setBalance(initialBalance);
        account.setCustomer(customer);

        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(account);
        customer.setAccounts(accounts);

        session.save(customer); // cascade saves the account too

        tx.commit();
        session.close();

        return customer.getCustomerId(); // return ID to display to user
    }

    // View customer and account details
    public void getCustomerDetails(int customerId) {
        Session session = factory.openSession();
        Customer customer = session.get(Customer.class, customerId);
        if (customer != null) {
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            List<BankAccount> accounts = customer.getAccounts();
            for (BankAccount account : accounts) {
                System.out.println("Account ID: " + account.getAccountId());
                System.out.println("Balance: " + account.getBalance());
            }
        } else {
            System.out.println("Customer not found.");
        }
        session.close();
    }

    // Deposit money into account
    public void deposit(int customerId, double amount) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Customer customer = session.get(Customer.class, customerId);
        if (customer != null && !customer.getAccounts().isEmpty()) {
            BankAccount acc = customer.getAccounts().get(0); // assuming one account per customer
            acc.setBalance(acc.getBalance() + amount);
            session.update(acc);
            tx.commit();
            System.out.println("Amount deposited successfully! New Balance: " + acc.getBalance());
        } else {
            System.out.println("Customer or account not found.");
            tx.rollback();
        }

        session.close();
    }

    // Withdraw money from account
    public void withdraw(int customerId, double amount) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Customer customer = session.get(Customer.class, customerId);
        if (customer != null && !customer.getAccounts().isEmpty()) {
            BankAccount acc = customer.getAccounts().get(0);
            if (acc.getBalance() >= amount) {
                acc.setBalance(acc.getBalance() - amount);
                session.update(acc);
                tx.commit();
                System.out.println("Withdrawal successful! New Balance: " + acc.getBalance());
            } else {
                System.out.println("Insufficient balance.");
                tx.rollback();
            }
        } else {
            System.out.println("Customer or account not found.");
            tx.rollback();
        }

        session.close();
    }

    // Check account balance
    public void checkBalance(int customerId) {
        Session session = factory.openSession();

        Customer customer = session.get(Customer.class, customerId);
        if (customer != null && !customer.getAccounts().isEmpty()) {
            BankAccount acc = customer.getAccounts().get(0);
            System.out.println("Current Balance: " + acc.getBalance());
        } else {
            System.out.println("Customer or account not found.");
        }

        session.close();
    }

    // Close session factory
    public void close() {
        factory.close();
    }
}
