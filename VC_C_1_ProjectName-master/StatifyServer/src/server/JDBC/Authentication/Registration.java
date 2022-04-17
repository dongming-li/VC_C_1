package JDBC.Authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import JDBC.DatabaseConnection;
import org.springframework.security.crypto.bcrypt.*;

public class Registration extends DatabaseConnection
{   
	private Connection connection;
    private boolean userInfoChecked = false;//this is set by checkNewUserInfo() method, and checked and reset by addNewUser() method
    
    /**
     * remember to call close() when done with object
     */
    public Registration()
    {
    	super();
    	connect();
    }
    
    
	
   /**
    * TODO: test
    * 
    * This method checks for the validity of the new user's username
    * returns false if username are already registered
    * returns true in all other cases
    * 
    * @param username
    * @return 
    */
    public boolean checkUsername(String username) //throws SQLException
    {
    	boolean success = true;
        try
        {
            Statement statement = connection.createStatement();
            ResultSet registeredUsers;
            
            registeredUsers = statement.executeQuery("select u.Username"
                    +                                "from Users u");
            
            while(registeredUsers.next())
            {
                if(username.equals(registeredUsers.getString("Username")))
                {
                    success = false;
                    break;
                }
            }
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
        userInfoChecked = success;
        return success;
    }
    
   /**
    * TODO: test
    * 
    * This method is to be called only after chackNewUserInfo is called and returns a positive result
    * This method adds a new tuple to the User table
    * 
    * @param username
    * @param email
    * @param password
    * @throws Exception
    */
    public void addNewUser(String username, String email, String password) throws Exception
    {
	    if(userInfoChecked)
	    {
	        try
	        {
	            PreparedStatement addUser = connection.prepareStatement("insert into Users(Username, Email, Password_)"
	                    +                                               "values(?,?,?)");
	            addUser.setString(1, username);
	            addUser.setString(2, email);
	            addUser.setString(3, password);
	            addUser.executeUpdate();
	        }
	        catch (SQLException e) 
	        {
	            System.out.println("SQLException: " + e.getMessage());
	            System.out.println("SQLState: " + e.getSQLState());
	            System.out.println("VendorError: " + e.getErrorCode());
	        }
	    }
        else
        {
            Exception e = new Exception();
            throw e;
        }
    }
    
}
