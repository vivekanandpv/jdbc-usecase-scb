package io.anviksha;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankCli {
    private final static CustomerService customerService = new CustomerServiceImplementation();


    public static void run() {
        while(true) {
            Scanner sc=new Scanner(System.in);

            displayMainMenu();

            try {
                int i = sc.nextInt();
                switch (i) {
                    case 1:
                        createCustomer();
                        break;
                    case 2:
                        displayAllCustomers();
                        break;
                    case 3:
                        searchCustomer();
                        break;
                    case 4:
                        deleteCustomer();
                        break;
                    case 5:
                        System.out.println("Quitting...");
                        return;
                    default:
                        System.out.println("Invalid option...");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Only 1 to 5");
                sc.nextLine();
            }
        }
    }

    static void displayMainMenu() {
        System.out.println("Welcome to Standard Chartered Bank");
        System.out.println("Please enter your choice");
        System.out.println("1. for Add new customer");
        System.out.println("2. for Display customers");
        System.out.println("3. for Search customer");
        System.out.println("4. for Delete customer");
        System.out.println("5. for Exit the bank application");
    }

    private static void createCustomer() {
        try {
            Scanner scanner = new Scanner(System.in);
            Customer customer = new Customer();

            System.out.println("Adding a new Customer:");
            System.out.println("*****************************");
            System.out.println("Enter Name: ");
            customer.setName(scanner.nextLine());

            System.out.println("Enter Email: ");
            customer.setEmail(scanner.nextLine());

            System.out.println("Enter Contact: ");
            customer.setContact(Long.parseLong(scanner.nextLine()));

            System.out.println("Enter Account Type: ");
            String accountType = scanner.nextLine();

            if (accountType.equalsIgnoreCase("credit")) {
                customer.setAccountType("Credit");
            } else if (accountType.equalsIgnoreCase("debit")) {
                customer.setAccountType("Debit");
            } else {
                System.out.println(accountType);
                throw new RuntimeException("Invalid account type");
            }

            customerService.create(customer);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Only numbers.");
        } catch (RuntimeException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void displayAllCustomers() {
        try {
            for (Customer customer : customerService.getAll()) {
                System.out.println(customer);
            }
        } catch (RuntimeException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void searchCustomer() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the Customer ID (Search):");
            System.out.println(customerService.findById(scanner.nextInt()));
        } catch (InputMismatchException re) {
            System.out.println("Invalid input. Only numbers.");
        } catch (RuntimeException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }

    private static void deleteCustomer() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the Customer ID (Delete):");
            customerService.deleteById(scanner.nextInt());
        } catch (InputMismatchException re) {
            System.out.println("Invalid input. Only numbers.");
        } catch (RuntimeException e) {
            System.out.printf("Error: %s\n", e.getMessage());
        }
    }
}
