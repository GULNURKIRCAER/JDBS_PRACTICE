package jdbctests;
import java.sql.*;
public class MainDepartmentsAll {
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
        ResultSet resultSet = statement.executeQuery("select * from departments");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " - " + resultSet.getString(2) + " - " + resultSet.getString(3)+" - " + resultSet.getString(4));
        }
    }
}
