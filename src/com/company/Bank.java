package com.company;

import java.io.Console;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class  Bank implements BankingServices  {
    private String name ;
    private String address ;
    private String phone ;
    public int balance ;
    private String  bankName;
    Connection dbconnection;
    public Scanner scanner = new Scanner(System.in);
    String connectionUrl = "jdbc:mysql://127.0.0.1:3306/bankCustomer";
    
    public Bank(String bankName )  {
        setBankName(bankName);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    // all operation bank can provide to the customer.
    public  void operations (int option)
    {
        switch (option)
        {
            case 1 :
                createAccount();
                break;
            case 2 :
                deleteCustomer();
                break;
            case 3 :
                checkIfCustomerExists();
                break;
            case 5 :
                withdraw();
                break;
            case 6 :
                checkBalance();
            case 7 :
                System.exit(0);
            default:
                System.out.println("Sorry your select is not listed ");
        }
    }
    public void createAccount()
    {
         try {
          // String name , String address , String phone,int balance
             System.out.println("Enter customer name  ? ");
            String name = scanner.next();
            System.out.println("Enter customer address  ? ");
            String address = scanner.next();
            System.out.println("Enter customer phone  ? ");
            String phone = scanner.next();
            System.out.println("Enter customer balance  ? ");
            int balance = scanner.nextInt();
             {
                 try {
                     dbconnection = DriverManager.getConnection(connectionUrl, "root", "");
                     // create a statement object to send to database
                     String insertQuery = "insert into customer  (name,address,phone,balance)" + " values (?,?,?,?)";
                      PreparedStatement preparedStatement = dbconnection.prepareStatement(insertQuery);
                     // prepare all data before insert it
                     preparedStatement.setString(1,name);
                     preparedStatement.setString(2,address);
                     preparedStatement.setString(3,phone);
                     preparedStatement.setInt(4,balance);
                    // return 0 if not insert it Or 1 if inserted
                     int result = preparedStatement.executeUpdate();
                     preparedStatement.close();
                     if (result == 1) {
                         System.out.println(" New Customer Created.");
                         ConsoleController.sleepAndReShowMenu(bankName);
                     }
                 } catch (SQLException throwables) {
                    System.out.println("Can not create new customer database Error ");
                 }
             }

        }catch(InputMismatchException e )
        {
            System.out.println("Sorry input mismatch !");
        }
    }
    @Override
    public void checkBalance() {
        try {
            // int id
            System.out.println("Enter customer id   ? ");
            int id = scanner.nextInt();
            try {
                dbconnection = DriverManager.getConnection(connectionUrl, "root", "");
                // create a statement object to send to database
                String checkQuery = "select * from customer where id = ? ";
                PreparedStatement preparedStatement = dbconnection.prepareStatement(checkQuery);
                // prepare all data before insert it
                preparedStatement.setInt(1, id);
                // return 0 if not query compete  Or 1 if not
                ResultSet result = preparedStatement.executeQuery();

                if (result.next())
                {
                    System.out.println("Current Balance = " + result.getInt("balance"));
                }else
                {
                    System.out.println(" Customer not found ");
                    ConsoleController.sleepAndReShowMenu(bankName);
                }
                preparedStatement.close();
            } catch (SQLException throwables) {
                System.out.println(" error from database   ");
            }
        } catch (Exception e) {
            System.out.println("Sorry Input error ");
        }

    }

    @Override
    public void withdraw() {
        int amount = 0 ;

        try {
            // int id
            System.out.println("Enter customer id   ? ");
            int id = scanner.nextInt();
            try {
                dbconnection = DriverManager.getConnection(connectionUrl, "root", "");
                // create a statement object to send to database
                String checkQuery = "select * from customer where id = ? ";
                PreparedStatement preparedStatement = dbconnection.prepareStatement(checkQuery);
                // prepare all data before insert it
                preparedStatement.setInt(1, id);
                // return 0 if not query compete  Or 1 if not
                ResultSet result = preparedStatement.executeQuery();

                if (result.next())
                {
                    System.out.println("Current Balance = " + result.getInt("balance"));
                    int balance = result.getInt("balance");
                    //ConsoleController.sleepAndReShowMenu(bankName);
                    System.out.println("Enter what amount you want ?");
                   amount = scanner.nextInt();
                    if(balance <  amount)
                        System.out.println("Sorry your balance is less than what you want ");
                     else
                    {
                        try {
                            dbconnection = DriverManager.getConnection(connectionUrl, "root", "");
                            // create a statement object to send to database
                              String updateQuery = "update customer set balance = ? where id = ? ";
                              preparedStatement = dbconnection.prepareStatement(updateQuery);
                            // prepare all data before insert it
                            int newBalance  = balance - amount ;
                            preparedStatement.setInt(1,newBalance);
                            preparedStatement.setInt(2, id);

                           int  updateResult = preparedStatement.executeUpdate();
                            // return 0 if not query compete  Or 1 if not
                            if (updateResult == 1)
                            {
                                System.out.println("withdraw completed ");
                                System.out.println("Your new Balance =  " + newBalance);
                            }
                        }catch (SQLException throwables) {
                            System.out.println(" error from database from update withdraw  ");
                        }

                    }
                }else
                {
                    System.out.println(" Customer not found ");
                    ConsoleController.sleepAndReShowMenu(bankName);
                }
                preparedStatement.close();
            } catch (SQLException throwables) {
                System.out.println(" error from database   ");
            }
        } catch (Exception e) {
            System.out.println("Sorry Input error ");
        }
     }

    @Override
    public void checkIfCustomerExists() {

        try {
            // int id
            System.out.println("Enter customer id   ? ");
            int id = scanner.nextInt();
            try {
                dbconnection = DriverManager.getConnection(connectionUrl, "root", "");
                // create a statement object to send to database
                String checkQuery = "select * from customer where id = ? ";
                PreparedStatement preparedStatement = dbconnection.prepareStatement(checkQuery);
                // prepare all data before insert it
                preparedStatement.setInt(1, id);
                // return 0 if not query compete  Or 1 if not
                ResultSet result = preparedStatement.executeQuery();

                 if (result.next())
                 {
                     System.out.println(" Customer found ");
                     ConsoleController.sleepAndReShowMenu(bankName);
                 }else
                 {
                     System.out.println(" Customer not found ");
                     ConsoleController.sleepAndReShowMenu(bankName);
                 }
                preparedStatement.close();
            } catch (SQLException throwables) {
                System.out.println(" error from database   ");
            }
        } catch (Exception e) {
            System.out.println("Sorry Input error ");
        }

    }

    @Override
    public int deposit(int amount) {
        return 0;
    }

    @Override
    public void UpdateCustomerName( ) {

    }

    @Override
    public void deleteCustomer() {
        {

            try {
                // int id
                System.out.println("Enter customer id   ? ");
                int id = scanner.nextInt();
                try {
                    dbconnection = DriverManager.getConnection(connectionUrl, "root", "");
                    // create a statement object to send to database
                    String deleteQuery  = "delete from customer  where(id) = " + "  (?)";
                    PreparedStatement preparedStatement = dbconnection.prepareStatement(deleteQuery);
                    // prepare all data before insert it
                    preparedStatement.setInt(1, id);
                     // return 0 if not query compete  Or 1 if not
                    int result = preparedStatement.executeUpdate();
                    preparedStatement.close();
                    if (result == 1) {
                        System.out.println(" Customer deleted ");

                     }else
                    {
                        System.out.println("Customer not found ");
                     }
                } catch (SQLException throwables) {
                    System.out.println("Can't delete customer from database error ");
                }
            } catch (Exception e) {
                System.out.println("Sorry Input error ");
            }
        }
    }

    @Override
    public void UpdateCustomerPhone() {

    }

    @Override
    public void UpdateCustomerAddress() {

    }
}
