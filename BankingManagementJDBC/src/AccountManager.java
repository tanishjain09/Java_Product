import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void credit_money(long account_number) {
        try {
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.print("Enter Security Pin: ");
            String security_pin = scanner.nextLine();

            connection.setAutoCommit(false);

            if (account_number == 0) {
                System.out.println("Invalid account number");
                return;
            }

            String verifyQuery = "SELECT * FROM Accounts WHERE account_number = ? AND security_pin = ?";
            try (PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery)) {
                verifyStmt.setLong(1, account_number);
                verifyStmt.setString(2, security_pin);
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (rs.next()) {
                        String creditQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
                        try (PreparedStatement creditStmt = connection.prepareStatement(creditQuery)) {
                            creditStmt.setDouble(1, amount);
                            creditStmt.setLong(2, account_number);

                            int rowsAffected = creditStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Rs." + amount + " credited Successfully");
                                connection.commit();
                            } else {
                                System.out.println("Transaction failed");
                                connection.rollback();
                            }
                        }
                    } else {
                        System.out.println("Invalid Security Pin!");
                        connection.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    public void debit_money(long account_number) {
        try {
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.print("Enter Security Pin: ");
            String security_pin = scanner.nextLine();

            connection.setAutoCommit(false);

            if (account_number == 0) {
                System.out.println("Invalid account number");
                return;
            }

            String verifyQuery = "SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?";
            try (PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery)) {
                verifyStmt.setLong(1, account_number);
                verifyStmt.setString(2, security_pin);
                try (ResultSet rs = verifyStmt.executeQuery()) {
                    if (rs.next()) {
                        double current_balance = rs.getDouble("balance");
                        if (amount <= current_balance) {
                            String debitQuery = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                            try (PreparedStatement debitStmt = connection.prepareStatement(debitQuery)) {
                                debitStmt.setDouble(1, amount);
                                debitStmt.setLong(2, account_number);

                                int rowsAffected = debitStmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    System.out.println("Rs." + amount + " debited Successfully");
                                    connection.commit();
                                } else {
                                    System.out.println("Transaction failed");
                                    connection.rollback();
                                }
                            }
                        } else {
                            System.out.println("Insufficient Balance");
                            connection.rollback();
                        }
                    } else {
                        System.out.println("Invalid Security Pin!");
                        connection.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    public void transfer_money(long sender_account_number) {
        try {
            System.out.print("Enter Receiver Account Number: ");
            long receiver_account_number = scanner.nextLong();
            System.out.print("Enter Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.print("Enter Security Pin: ");
            String security_pin = scanner.nextLine();

            connection.setAutoCommit(false);

            if (sender_account_number == 0 || receiver_account_number == 0) {
                System.out.println("Invalid Account number(s)");
                return;
            }

            String verifySenderQuery = "SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?";
            try (PreparedStatement verifySenderStmt = connection.prepareStatement(verifySenderQuery)) {
                verifySenderStmt.setLong(1, sender_account_number);
                verifySenderStmt.setString(2, security_pin);
                try (ResultSet senderRs = verifySenderStmt.executeQuery()) {
                    if (senderRs.next()) {
                        double currentBalance = senderRs.getDouble("balance");
                        if (amount <= currentBalance) {
                            String debitQuery = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
                            String creditQuery = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";

                            try (
                                    PreparedStatement debitStmt = connection.prepareStatement(debitQuery);
                                    PreparedStatement creditStmt = connection.prepareStatement(creditQuery)
                            ) {
                                debitStmt.setDouble(1, amount);
                                debitStmt.setLong(2, sender_account_number);

                                creditStmt.setDouble(1, amount);
                                creditStmt.setLong(2, receiver_account_number);

                                int debitRows = debitStmt.executeUpdate();
                                int creditRows = creditStmt.executeUpdate();

                                if (debitRows > 0 && creditRows > 0) {
                                    System.out.println("Transaction Successful");
                                    System.out.println("Rs." + amount + " transferred successfully");
                                    connection.commit();
                                } else {
                                    System.out.println("Transaction failed!");
                                    connection.rollback();
                                }
                            }
                        } else {
                            System.out.println("Insufficient Balance!");
                            connection.rollback();
                        }
                    } else {
                        System.out.println("Invalid Security Pin!");
                        connection.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            try { connection.rollback(); } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            try { connection.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    public void getBalance(long account_number) {
        try {
            System.out.print("Enter Security Pin: ");
            String security_pin = scanner.nextLine();

            String query = "SELECT balance FROM Accounts WHERE account_number = ? AND security_pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, security_pin);

                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        double balance = rs.getDouble("balance");
                        System.out.println("Balance: Rs." + balance);
                    } else {
                        System.out.println("Invalid account number or security pin.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
