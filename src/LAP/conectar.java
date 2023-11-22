package LAP;
 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class conectar {
    public static final String URL = "jdbc:mysql://localhost:3306/mydb";
    public static final String USER = "root";
    public static final String CLAVE = "123789456";
    
    public static Connection conect(String user,String pass) {
            Connection con = null;
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = (Connection) DriverManager.getConnection(URL, user, pass);
            }catch(ClassNotFoundException | SQLException e){
                
            }  
            
            return con;
        }
}