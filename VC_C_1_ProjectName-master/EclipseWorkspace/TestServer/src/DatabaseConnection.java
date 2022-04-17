import java.sql.*;
import java.util.Scanner;
import java.io.PrintWriter;


import java.io.PrintWriter;

/**
 * Manages all transactions to and from the MySQL database
 */
public class DatabaseConnection 
{
	/**
	 * JDBC connection to database
	 */
    private Connection connection;
    
    /**
     * URL of the database
     */
    private final String dbUrl = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309vcc1";
    
    /**
     * Username for database
     */
    private final String user = "dbu309vcc1";
    
    /**
     * Password for database
     */
    private final String password = "222RxfQ3";
    
    /**
     * Boolean state to manage if userInfo is checked
     */
    private boolean userInfoChecked = false;//this is set by checkNewUserInfo() method, and checked and reset by addNewUser() method
    


    //CONSTRUCTOR//////////////////////////////////////////////////////////////////////    
   /**
    *
    *Automatically connects to the database
    *Call close() to close connection before program terminates 
    *
    */
    public DatabaseConnection()
    {
        connect();
    }
    


    //METHODS//////////////////////////////////////////////////////////////////////////
   /**
    *
    *Connects to the database at dbUrl using username user and password password
    *
    */
    public void connect()
    {
        try 
        {
            //PrintWriter pw = new PrintWriter("debug.txt");
            //pw.println("In connect method before loading driver");
            // Load the driver (registers itself)
            Class.forName("com.mysql.jdbc.Driver");
            //pw.println("In connect method after loading driver");
            //pw.close();
        } 
        catch (Exception E) 
        {
            System.err.println("Unable to load driver.");
            E.printStackTrace();
        }


        try 
        {
            // Connect to the database
            
            
            
            connection = DriverManager.getConnection(dbUrl, user, password);
            System.out.println("*** Connected to the database ***");
            
            

        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
	
   /**
    * closes connection with database
    */
    public void close()
    {
        try 
        {
            connection.close();
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    

    /**
     * Pulls uploaded layouts from database
     * @return Uploaded layout data from database
     */
    public String pullUploadedLayouts() {
    	
    	
		String result = "";
		try{
			Statement statement = connection.createStatement();
			ResultSet results;
			
			results = statement.executeQuery("select * " 
					+ "from Uploaded_Layouts");
			
			while(results.next()){
		
				result += results.getString(1);//userid
				result += ";"+ results.getString(2);//layoutname
				result += ";" + results.getString(4);//#downloads
				result += ";" + results.getString(3);
				result += "#";
			}
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		return result;
    	
    }
    
   /**
    * 
    * This method checks for the validity of the new user's username
    * Returns false if username are already registered
    * Returns true in all other cases
    * 
    * @param username Username to be checked
    * @return False if username exists in database
    */
    public boolean checkUsername(String username) //throws SQLException
    {
    	boolean success = false;
        try
        {
            Statement statement = connection.createStatement();
            ResultSet registeredUsers;
            
            registeredUsers = statement.executeQuery("select Username "
                    +                                "from Users");
            
            while(registeredUsers.next())
            {
            	String Username = registeredUsers.getString(1);
                if(username.equals(" "+Username))
                {
                    success = true;
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
    * 
    * This method is to be called only after chackNewUserInfo is called and returns a positive result.
    * This method adds a new tuple to the User table
    * 
    * @param username Username to be added
    * @param email Email to be added
    * @param password Password to be added
    * @throws Exception
    */
    public void addNewUser(String username, String email, String password) throws Exception
    {
	        try
	        {
	            PreparedStatement addUser = connection.prepareStatement("insert into Users(Username, Email, Password_, Rank)"
	                    +                                               "values(?,?,?,?)");
	            addUser.setString(1, username);
	            addUser.setString(2, email);
	            addUser.setString(3, password);
				addUser.setInt(4, 1);
	            addUser.executeUpdate();
	        }
	        catch (SQLException e) 
	        {
	            System.out.println("SQLException: " + e.getMessage());
	            System.out.println("SQLState: " + e.getSQLState());
	            System.out.println("VendorError: " + e.getErrorCode());
	        }
    }

    /**
	 * Returns passwordHash corresponding to the given username
	 * 
	 * @param username Username to use to get password hash
	 * @return Password hash of sent username
	 * @throws Exception
	 */
	public String checkPassword(String username) 
	{
		String passwordHash = "";
		try
        {
            Statement statement = connection.createStatement();
            ResultSet registeredUsers;
            
            registeredUsers = statement.executeQuery("select Username, Password_ "
                    +                                "from Users");
            
            while(registeredUsers.next())
            {
            	String Username = registeredUsers.getString(1);
                if(username.equals(" "+Username))
                {
                    passwordHash = registeredUsers.getString(2);
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
		return passwordHash;
	}


	/**
	 * Returns UserID corresponding to the given username
	 * 
	 * @param username The username to find the userID of
	 * @return The userID of the sent username
	 * @throws Exception
	 */
	public String getUserID(String username) 
	{
		int UserID = 0;
		try
        {
            Statement statement = connection.createStatement();
            ResultSet registeredUsers;
            
            registeredUsers = statement.executeQuery("select Username, UserID "
                    +                                "from Users");
            
            while(registeredUsers.next())
            {
            	String Username = registeredUsers.getString(1);
                if(username.equals(" "+Username))
                {
                    UserID = registeredUsers.getInt(2);
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
		return "" + UserID;
	}
    
	/**
	 * Returns the layouts from a particular user
	 * @param UserID The userID to get the layouts of
	 * @return The layouts from the sent userID
	 */
	public String getLayouts(String userID){
		String result = "";
		int counter = 0;
		
		try{
			Statement statement = connection.createStatement();
			ResultSet userLayouts;
			
			userLayouts = statement.executeQuery("select layoutName,layoutData " 
					+ "from User_Layouts "
					+ "where userID = " + userID);
			

			while(userLayouts.next()){
				result += userLayouts.getString(1) + ";";
				result += userLayouts.getString(2) + ";";
				counter++;
			}
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		
		return Integer.toString(counter) + ";" + result;
	}
	
	/**
	 * Adds calculated data point to specified table.
	 * @param UserID userID of calculated data point creator
	 * @param ActivityName activity name to add
	 * @param ExpressionName expression name to add
	 * @param Expression expression to add
	 */
	public void addCalculatedData(String userID, String activityName, String expressionName, String expression){
		String tableName = userID + "_" + activityName + "_" + "CalcData";
		expression.replace("+", "\\+");
		 try
	        {
			 	PreparedStatement createTableIfNotExist = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + tableName
			 			+ "(Name varchar(255), Expression varchar(255))");
			 	createTableIfNotExist.executeUpdate();
	            PreparedStatement statement = connection.prepareStatement("insert into " + tableName + "(Name, Expression)"
	                    +                                               "values(?,?)");
			 	statement.setString(1, expressionName);
			 	statement.setString(2, expression);
			 	statement.executeUpdate();
	        }
	        catch (SQLException e) 
	        {
	            System.out.println("SQLException: " + e.getMessage());
	            System.out.println("SQLState: " + e.getSQLState());
	            System.out.println("VendorError: " + e.getErrorCode());
	        }
	}
	
	/**
	 * Returns serialized form of calculated data from database
	 * @param UserID userID to get calculated data of
	 * @param ActivityName Activity name to get calculated data from
	 * @return Serialized form of calculated data
	 */
	public String getCalculateData(String userID, String activityName){
		String tableName = userID + "_" + activityName + "_" + "CalcData";
		String result = "";
		try{
			Statement statement = connection.createStatement();
			ResultSet results;
			
			results = statement.executeQuery("select * " 
					+ "from " + tableName);
			
			while(results.next()){
				result += results.getString(1) + ";";
				result += results.getString(2) + ";";
			}
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		return "#" + result;
	}
	
	/**
	 * Updates a tuple of a given expression from the Calculated Data module when it is edited
	 * @param userID UserID of calculated data point editor
	 * @param activityName Activity name of data point
	 * @param expressionName Expression name of data point
	 * @param newExpression New expression to update
	 * @return True if successful
	 */
	public boolean updateCalculateData(String userID, String activityName, String expressionName, String newExpression){
		String tableName = userID + "_" + activityName + "_" + "CalcData";
		String result = "";
		try{
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("UPDATE " + tableName + " SET Expression = \'" + newExpression 
					+ "\' WHERE Name = \'" + expressionName + "\'");
			
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		return true;
	}
	
	/**
	 * Adds a new layout to the database
	 * @param userID UserID of layout creator
	 * @param layoutName LayoutName to add
	 * @param layoutData LayoutData to add
	 * @return True if successful
	 */
	public boolean addLayout(String userID, String layoutName, String layoutData){
		try{
			PreparedStatement statement = connection.prepareStatement("insert into User_Layouts(UserID,layoutName, layoutData)"
                +                                               "values(?,?,?)");
		 	statement.setString(1, userID);
		 	statement.setString(2, layoutName);
		 	statement.setString(3, layoutData);
		 	statement.executeUpdate();
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		
		return true;
	}
	
	/**
	 * Updates the tuple of a given layout
	 * @param userID UserID of the editor
	 * @param layoutName Layout name to update
	 * @param layoutData New layout data to update
	 * @return
	 */
	public boolean editLayout(String userID, String layoutName, String layoutData){
		try{
			PreparedStatement statement = connection.prepareStatement("UPDATE User_Layouts SET layoutData = ? WHERE UserID = ? AND layoutName = ?");
		 	statement.setString(1, layoutData);
		 	statement.setString(2, userID);
		 	statement.setString(3, layoutName);
		 	statement.executeUpdate();
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		
		return true;
	}
	
	/**
	 * Increments the numDownloads of a given uploaded layout
	 * @param numDownloads New number of downloads to add
	 * @param userId UserID of the uploader
	 * @param layoutName Layout name of the downloaded layout
	 * @return Standard message
	 */
	public String downloadIncrement(int numDownloads, String userId, String layoutName) {
		String tableName = "Uploaded_Layouts";
		String result = "";
		try{
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("UPDATE Uploaded_Layouts" + " SET numberOfDownloads = \'" +  numDownloads
					+ "\' WHERE UserID = \'" + userId + "\' AND layoutName = \'" + layoutName + "\'");
			
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		return result;
	}
	
	/**
	 * Adds new uploaded layout to the database
	 * @param UserID UserID of the uploader
	 * @param layoutName Layout name to be added
	 * @param layoutData Layout data to be added
	 * @param numberOfDownloads Number of downloads to be added
	 * @return Standard message
	 */
	public String uploadLayout(String UserID, String layoutName, String layoutData, String numberOfDownloads) {
		
	    try
        {
            PreparedStatement addLayout = connection.prepareStatement("insert into Uploaded_Layouts(UserID, layoutName, layoutData, numberOfDownloads)"
                    +                                               "values(?,?,?,?)");
            addLayout.setString(1, UserID);
            addLayout.setString(2, layoutName);
            addLayout.setString(3, layoutData);
            addLayout.setString(4, numberOfDownloads);
            addLayout.executeUpdate();
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }   
		  return "stuff";
	}
	
	/**
	 * Takes in userID returns corresponding username and rank 
	 * 
	 * @param UserID UserID of requested user
	 * @return Username and Rank of matching UserID
	 */
	public String getUserInfo(int UserID)
	{
		String result = "invalid_ID";
		
		try{
			Statement statement = connection.createStatement();
			ResultSet users;
			
			users = statement.executeQuery("select Username, Rank " 
					+ "from Users "
					+ "where userID = " + UserID);
			
			users.next();
			result = users.getString(1) + " ";
			result += users.getInt(2);
		} catch(SQLException e){
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
		}
		
		
		return result;
	}  
}
