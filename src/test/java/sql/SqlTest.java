package sql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SqlTest {

    // Retrieve the number of customers from the database
    // Check that the total number of customers is equal to 2

   Connection connection;
   @BeforeEach
   public void establishConnection(){
      try {
          String url = "jdbc:mysql://localhost:3306/online_banking_db";
          String username = "root";
          String password = "Vegeter@217290701";

          connection = DriverManager.getConnection(url,username,password);
      }catch (SQLException se){
          se.printStackTrace();
      }
   }
    @Test
    public void getAllCustomers_checkNumber_shouldBeTwo() {

        try {
            String sql = "select count(*) as total_record From customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                int numberOfCustomers = resultSet.getInt("total_record");
                assertEquals(2,numberOfCustomers);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }

    }

    // Retrieve all accounts for customer Sarah from the database
    // Check that the total number of accounts for her is 2
    @Test
    public void getAccountsForSarah_checkNumber_shouldBeTwo() {
       String name = "Sara";
       try {
           String sql = "select count(all Account_Type) from accounts join customer on customer.Customer_ID = accounts.Customer_ID where First_Name = '"+name+"'";
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sql);

           while (resultSet.next()){
               System.out.println(resultSet.getInt("count(all Account_Type)"));
               int totalNumberOfUserAccounts = resultSet.getInt("count(all Account_Type)");
               assertEquals(2, totalNumberOfUserAccounts);
           }
       }catch (SQLException se){
           se.printStackTrace();
       }

    }

    // A test that checks that the total of all transactions is equal to 0 for a given account.
    // Doing this for a single account is enough,
    // and you’re allowed to use the account ID for that account in the query
    @Test
    public void retrieveTransactionsForAccount_checkTotalBalance_shouldBeZero() {
       try {
           String sql = "select sum(all Amount) from transactions join accounts on accounts.Account_Number = transactions.Account_Number  join customer " +
                   "on customer.Customer_ID = accounts.Customer_ID where accounts.Customer_ID = 1 and transactions.Account_Number = 1501020321";
           Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sql);

           while (resultSet.next()){
               System.out.println(resultSet.getDouble("sum(all Amount)"));
               double transactionTotal = resultSet.getDouble("sum(all Amount)");
               assertEquals(0,transactionTotal);
           }
       }catch (SQLException se){
           se.printStackTrace();
       }
    }
    @AfterEach
    public void terminateConnection() throws SQLException {
       connection.close();
    }
}
