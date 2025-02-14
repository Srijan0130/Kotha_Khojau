package database;



import java.sql.*;
import java.util.Random;

public class DbConnection {

    Statement statement;
    Connection connection;
    int val;
    ResultSet resultSet;
    ResultSet rows;

    public DbConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Kotha_Khojau_Db?characterEncoding=utf8&useSSL=true&autoReconnect=true",
                            "root", "MacBooK12@");
            statement = connection.createStatement();


            if (connection != null){
                System.out.println("Database is connected");
            }
            else {
                System.out.println("Database connection failed");
            }

            // create table for User
            String tableCreate = "create table if not exists User_tbl(UserID int auto_increment, " +
                    "FirstName varchar(30) not null, " +
                    "MiddleName varchar(20), " +
                    "LastName varchar(30) not null," +
                    "MemberType varchar(20) not null, " +
                    "Gender varchar(10) not null," +
                    "Contact varchar(15) not null, " +
                    "DOB date not null, " +
                    "Occupation varchar(20) not null, " +
                    "PersonalEmail varchar(50) not null, " +
                    "Address varchar(50) not null, " +
                    "Username varchar(40) not null, " +
                    "Password varchar(40) not null," +
                    "AboutMe varchar(200) ," +
                    "constraint userId_pk primary key(UserID), " +
                    "constraint contact_uk unique(Contact), " +
                    "constraint email_uk unique(PersonalEmail), " +
                    "constraint username_uk unique(Username))";


            // create table for driver details
            String driverTable = "create table if not exists Driver_tbl(DriverID int auto_increment, " +
                    "FullName varchar(30) not null, " +
                    "Contact varchar(10) not null, " +
                    "AvailableLocations varchar(100) not null, " +
                    "ServiceChargeStatus varchar(10) not null, " +
                    "ShortDistance varchar(10), " +
                    "LongDistance varchar(10), " +
                    "VehicleSize varchar(20), " +
                    "constraint driverId_pk primary key(DriverID))";


           // PreparedStatement pst = connection.prepareStatement(tableCreate);

            String[] tables = {tableCreate, driverTable};
            for (String element : tables) {
                statement.execute(element);
            }


            System.out.println("Table has been created");

        }
        catch (Exception exception){
            exception.printStackTrace();
        }

    }

    // this function of this method is to insert data into database
    public int manipulate(String query) throws SQLException {
        try{
            val = statement.executeUpdate(query);
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
        finally {
            connection.close();
        }
        return val;
    }

    // this functions checks whether provided username and passwords are in the database or not.

    public ResultSet matchValues(String query, String username, String password){

        try{
            // 1st try
//            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query);
//            pst.setString(1, username);
//            pst.setString(2, password);
//
//            resultSet = pst.executeQuery(query);

            // this does not work : memberType
            //System.out.println(statement.executeQuery(memberCategory));
            resultSet = statement.executeQuery(query);
        }
        catch (Exception exception){
            exception.getStackTrace();
            System.out.println(exception.getMessage());
        }

        return resultSet;
    }


    // retrieving data from the database

    public ResultSet retrieveData(String query){  // data stores in ResultSet (multi-dimensional array)
        try{

            rows = statement.executeQuery(query);
        }
        catch (Exception error){
            error.printStackTrace();
        }
        return rows;
    }

    // for updating database
    public void updateDetails(String query){
        try{
            statement.executeUpdate(query);
        }
        catch (SQLException error){
            error.printStackTrace();
            System.out.println(error.getMessage());

        }
    }



    public boolean driverDetailsInsert(){
        String[] names = {"Bishal Kumar Karki", "Santosh Adhikari", "Lal Mani Shrestha", "Suraj Majhi", "Keshav Bhujel", "Nabin Jung Magar", "Pravin Narayan Thapa", "Manoj Ratna Shakya", "Arjun Bahadur Karki"};
        String[] contacts = {"9861251844", "9823426229", "9864224909", "9841124819", "9844224909", "9746297792", "9848673763", "9869760012", "9808502001"};
        String[] locations = {"Balkumari - Gongabu", "Gausala - New Baneshowr", "Imadol - Balaju", "Lazmipat - Basundhara", "Kalimati - Chandragiri", "Syambhu - Patan", "Sundhara - Jagathi", "Nepaltar - Rantapark", "Maitighar - Jawalakhel"};
        String[] chargeStatus = {"Fix", "Negotiable", "NA"};
        String[] shortDistance = {"Rs 500", "Rs 600", "Rs 700"};
        String[] longDistance = {"Rs 1000", "Rs 1200", "Rs 1400"};
        String[] vehicleStatus = {"Small", "Medium", "Large"};

        int num;
        Random random = new Random();

        try {
            for (int i = 0; i < names.length; i++) {

                num = random.nextInt(chargeStatus.length);
                statement.execute("insert into Driver_tbl(FullName, Contact, AvailableLocations, ServiceChargeStatus, ShortDistance, LongDistance, VehicleSize) values('"
                        + names[i] + "','" +
                        contacts[i] + "','" +
                        locations[i] + "','" +
                        chargeStatus[num] + "','" +
                        shortDistance[num] + "','" +
                        longDistance[num] + "','" +
                        vehicleStatus[num] + "')");
            }
            System.out.println("Driver info inserted in the database");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }

        return false;
    }


    public static void main(String[] args) {
        new DbConnection();
    }
}
