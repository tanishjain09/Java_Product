import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {

    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "Tani.0905";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
            return;
        }

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Scanner scanner = new Scanner(System.in)
        ) {
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_number;

            while (true) {
                System.out.println("*** WELCOME TO BANKING SYSTEM ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice1 = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice1) {
                    case 1:
                        user.register();
                        System.out.print("\033[H\033[2J"); // clear screen (UNIX only)
                        System.out.flush();
                        break;

                    case 2:
                        email = user.login();
                        if (email != null) {
                            System.out.println("\nUser logged in successfully!");

                            if (!accounts.account_exists(email)) {
                                System.out.println("\nNo account found!");
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit to Main Menu");
                                System.out.print("Enter your choice: ");
                                int createChoice = scanner.nextInt();
                                scanner.nextLine();

                                if (createChoice == 1) {
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your account number is: " + account_number);
                                } else {
                                    continue; // return to main menu
                                }
                            }

                            account_number = accounts.getAccountNumber(email);
                            int choice2 = 0;

                            while (choice2 != 5) {
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.print("Enter Your Choice: ");

                                choice2 = scanner.nextInt();
                                scanner.nextLine(); // consume newline

                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.getBalance(account_number);
                                        break;
                                    case 5:
                                        System.out.println("Logging out...");
                                        break;
                                    default:
                                        System.out.println("Enter a valid choice!");
                                }
                            }
                        } else {
                            System.out.println("Incorrect Email or Password");
                        }
                        break;

                    case 3:
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!");
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Enter a valid choice");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
