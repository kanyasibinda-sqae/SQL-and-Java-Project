
import java.sql.*;
import java.util.Scanner;

public class ProduceReports {



        public static void main(String args []) throws SQLException{

            Scanner console = new Scanner(System.in);
            System.out.println("Please enter customer id");
            int Customer_id = console.nextInt();
            ResultSetMetaData rsm = null;

            String connectionUrl = "jdbc:mysql://localhost:3306/online_banking_db";
            String username = "root";
            String password = "Vegeter@217290701";

            //Establish connection between the database and java code (connecting backend to frontend) using jdbc
            Connection conn = DriverManager.getConnection(connectionUrl,username ,password );

            //setup statements for interacting with the database
            String sqlSelectFromCustomer = "SELECT Address FROM customer WHERE Customer_ID =('"+Customer_id+"')" ;
            PreparedStatement ps = conn.prepareStatement(sqlSelectFromCustomer);
            ResultSet rs = ps.executeQuery();

            //Execution Statements for retrieving data from the database
            while (rs.next()) {
                String Address = rs.getString("Address");
                System.out.println(Address);
            }

            //setup statements for interacting with the database
            String sqlSelectAccountBalance = "SELECT sum(Account_Balance) FROM accounts WHERE Customer_ID =('"+Customer_id+"')" ;
            PreparedStatement ps1 = conn.prepareStatement(sqlSelectAccountBalance);
            ResultSet rs1 = ps1.executeQuery();

            //Execution Statements for retrieving account balance total from the database
            while(rs1.next()){
                Double account_balance = rs1.getDouble("sum(Account_Balance)");
                System.out.println(account_balance);
            }

            //setup statements for getting the transaction overview
            String Account_Type= "Checking";
            String TransactionOverview = "select Transaction_ID,Amount,Transaction_Date,Transaction_Type, transactions.Account_Number from transactions" +
                    " inner join accounts on accounts.Account_Number = transactions.Account_Number where Account_Type = '"+Account_Type+"' and Customer_ID = '"+Customer_id+"'";
            PreparedStatement ps2 = conn.prepareStatement(TransactionOverview);
            ResultSet rs2 = ps2.executeQuery();

            rsm = rs2.getMetaData();
            System.out.println(rsm.getColumnName(5)+" | "+rsm.getColumnName(2)+" | "+rsm.getColumnName(4)+"  | "+rsm.getColumnName(3));

            //Execution Statements for retrieving transaction overview data from the database
            while(rs2.next()){

                int account_number = rs2.getInt ("Account_Number");
                Date transaction_date = rs2.getDate("Transaction_Date");
                Double amount = rs2.getDouble("Amount");
                String transaction_type = rs2.getString("Transaction_Type");

                System.out.println(account_number +"     | "+ amount +"  | "+ transaction_type +"           | "+ transaction_date);;
            }

            console.close();
            }


        }

