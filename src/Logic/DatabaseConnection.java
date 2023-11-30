package Logic;

import Main.GUI;

import javax.swing.*;
import java.sql.*;

public class DatabaseConnection {
    private final String URL ="jdbc:postgresql://memorydatabase.ct5cqswjns9i.us-east-1.rds.amazonaws.com:5432/memoryRecorder";
    private Connection connection;
    public void connectDatabase(){
        try {
            connection = DriverManager.getConnection(URL,"postgres","memoryRecorder");
//            return true;
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
//            return false;
        }
    }
/*    public Boolean login(String username, String password){
        try {
            Statement stm = connection.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "Select * From customer Where username = '" + username + "' and password = '" + password + "'";
            ResultSet result = stm.executeQuery(sql);

            if(result.next()){
                return true;
//                hideFrame();
//                new GUI(connection, result);
            }
            else {JOptionPane.showMessageDialog(null, "Username/ Password is incorrect");}
        }catch (SQLException e1){
            JOptionPane.showMessageDialog(null, "System Error");
            return false;
        }
    }*/
}
