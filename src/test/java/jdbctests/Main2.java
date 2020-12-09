package jdbctests;
import java.sql.*;
public class Main2 {
    public static void main(String[] args) throws SQLException {
        String dbUrl = "jdbc:oracle:thin:@52.90.239.48:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

        //1-create connection
        Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        //2-create statement object
        Statement statement = connection.createStatement();

        //3-run query and get the result in resultset object
        //ResultSet resultSet = statement.executeQuery("select * from departments");
        ResultSet resultSet = statement.executeQuery("select * from regions");

        //move pointer to first row
        resultSet.next();

        //getting information with column name-BU DA OLUR
        System.out.println(resultSet.getString("region_name"));
        //getting information with column index (starts from 1) BU DA OLUR
        System.out.println(resultSet.getString(2));

        System.out.println(resultSet.getInt(1)+" - "+resultSet.getString("region_name")); //BUNLAR AYNI
        System.out.println(resultSet.getInt("region_id")+" - "+resultSet.getString(2));   //BUNLAR AYNI

        //move pointer to second row
        resultSet.next();

        System.out.println(resultSet.getInt(1)+" - "+resultSet.getString("region_name"));

    }
}