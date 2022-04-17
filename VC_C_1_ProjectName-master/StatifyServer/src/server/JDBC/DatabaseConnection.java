

import java.sql.*;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elijah Buscho
 */
import java.io.PrintWriter;
public class DatabaseConnection 
{

    //INSTANCE DATA////////////////////////////////////////////////////////////////////
    private Connection connection;
    private final String dbUrl = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309vcc1";
    private final String user = "dbu309vcc1";
    private final String password = "222RxfQ3";
    
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
    *Connects to the database at dbUrl
    *using username user and password password
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
    
    
    //ADD METHODS//////////////////////////////////////////////////////////////////////
   /**
    * TODO: test, implement scemaForActivity() method
    * @param Username
    * @param ActivityName 
    */
    public void add_Activity(String Username, String ActivityName)
    {
        //creates two new tables: "<Username>_<ActivityName>_Dashboard", and "<Username>_<ActivityName>_RawData"
        try
        {
            Statement createDashboardTable = connection.createStatement();
            createDashboardTable.executeQuery("create table "+Username+"_"+ActivityName+"_Dashboard "
                    +                                               "(ModuleName varchar[100], Position int)");
            
            String schema;
            switch(ActivityName)
            {
                case "Bowling" :
                    schema = "(Frame1_1 int, Frame1_2 int, "
                            + "Frame2_1 int, Frame2_2 int, "
                            + "Frame3_1 int, Frame3_2 int, "
                            + "Frame4_1 int, Frame4_2 int, "
                            + "Frame5_1 int, Frame5_2 int, "
                            + "Frame6_1 int, Frame6_2 int, "
                            + "Frame7_1 int, Frame7_2 int, "
                            + "Frame8_1 int, Frame8_2 int, "
                            + "Frame9_1 int, Frame9_2 int, "
                            + "Frame10_1 int, Frame10_2 int, Frame10_3 int";
                    break;
                default :
                    schema = "(oops int";
                    break;
            }
            
            schema += ", TimeRecord timestamp)";//last attribute for _RawData table is always a timestamp
            
            Statement createRawDataTable = connection.createStatement();
            
            createRawDataTable.executeQuery("create table "+Username+"_"+ActivityName+"_RawData "
                    +                                               schema);
            
            //adds a tuple, (ActivityName), to "<Username>_Activity"
            PreparedStatement addGame = connection.prepareStatement("insert into "+Username+"_Activity (ActivityName)"
                    +                                               "values(?)");
            addGame.setString(1, ActivityName);
            addGame.executeUpdate();
            
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
   /**
    * TODO: test
    * @param Username
    * @param ActivityName 
    * @param Position 
    * @param ModuleName 
    */
    public void add_Module(String Username, String ActivityName, int Position, String ModuleName)
    {
        //checks if there exists a module in "<Username>_<ActivityName>_Dashboard" at <Position>
        //if true: changes the tuple to the correct name
        //else: adds a tuple, (ModuleName, Position), to "<Username>_<ActivityName>_Dashboard"
        try
        {
            Statement statement = connection.createStatement();
            ResultSet dashboard;
            boolean moduleAdded = false;
            
            dashboard = statement.executeQuery("SELECT *\n" +
                                                    "FROM `db309vcc1`.`" + Username + "_" + ActivityName +"_Dashboard`;");

            while(dashboard.next())//look through the table to see if a tuple exists with the given Position
            {
                if(dashboard.getInt("Position") == Position)//if there is a tuple containting Position, update the ModuleName of that tuple
                {
                    PreparedStatement update = connection.prepareStatement ("update " + Username + "_" + ActivityName +"_Dashboard" + " " +
                                                                            "set ModuleName=?" + " " +
                                                                            "where Position = ? " );
                    update.setString(1,ModuleName);
                    update.setInt(2,Position);
                    update.executeUpdate();
                    
                    moduleAdded = true;
                    break;
                }
            }
            if(!moduleAdded)//if module has not yet been added, add a new tuple containing ModuleName and Position
            {
                PreparedStatement addModule = connection.prepareStatement("insert into "+Username+"_"+ActivityName+"_Dashboard (ModuleName, Position)"
                    +                                               "values(?, ?)");
                addModule.setString(1, ModuleName);
                addModule.setInt(1, Position);
                addModule.executeUpdate();
            }
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
   /**
    * TODO: implement, using scemaForActivity() method
    * @param Username
    * @param ActivityName 
    * @param dataString
    */
    public void add_Data(String Username, String ActivityName, String dataString)
    {
        //adds a new tuple, (dataString), to "<Username>_<ActivityName>_RawData"
        try
        {
            PreparedStatement addModule = connection.prepareStatement("insert into "+Username+"_"+ActivityName+"_Dashboard (ModuleName, Position)"
                +                                               "values(?, ?)");
            addModule.setString(1, ActivityName);
            addModule.setInt(1, 69);
            addModule.executeUpdate();
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
   /**
    * TODO:implement this method for create, and insert operations
    * 
    * helper method that returns corresponding schema for activity ActivityName
    * 
    * op represents operation type: 1 = create, 
    *                               2 = insert
    * 
    * @param ActivityName
    * @param op
    * @return 
    */
    public Schema schemaForActivity(String ActivityName, int op)
    {
        Schema schema;
        
        if (op == 1)//create
        {
            switch(ActivityName)
            {
                case "Bowling" :
                    schema = new Schema(22);
                    schema.setSchemaString("(Frame1_1 int, Frame1_2 int, "
                            + "Frame2_1 int, Frame2_2 int, "
                            + "Frame3_1 int, Frame3_2 int, "
                            + "Frame4_1 int, Frame4_2 int, "
                            + "Frame5_1 int, Frame5_2 int, "
                            + "Frame6_1 int, Frame6_2 int, "
                            + "Frame7_1 int, Frame7_2 int, "
                            + "Frame8_1 int, Frame8_2 int, "
                            + "Frame9_1 int, Frame9_2 int, "
                            + "Frame10_1 int, Frame10_2 int, Frame10_3 int");
                    break;
                default :
                    schema = new Schema(2);
                    schema.setSchemaString("(NotYetImplemented int");
                    break;
            }
            
            schema.setSchemaString(schema.getSchemaString() + ", TimeRecord timestamp)");//last attribute for _RawData table is always a timestamp
        }
        else if(op == 2)//insert 
        {
            switch(ActivityName)
            {
                case "Bowling" :
                    int attributes = 22;
                    schema = new Schema(attributes);
                    //all data types are int except the last one which is a timestamp
                    for(int i = 0; i < attributes - 1; i++)
                    {
                        schema.addDataType("int", i);
                    }
                    schema.addDataType("timestamp", attributes-1);
                    break;
                default :
                    schema = new Schema(2);
                    break;
            }
            
            schema.setSchemaString("insert");
        }
        else //op code mismatch
        {
            schema = new Schema();
        }
        
        
        
        return schema;
    }
    
   /**
    * TODO: get rid of this because its obsolete
    */
    public void addBowlingScore(int data)
    {
        try
        {
            PreparedStatement addGame = connection.prepareStatement("insert into BowlingGames(Score)"
                    +                                               "values(?)");
            addGame.setInt(1, data);
            addGame.executeUpdate();
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
    //PULL METHODS/////////////////////////////////////////////////////////////////////
   /**
    * 
    * @param tableName
    * @return 
    */
    public String pullTable(String tableName)
    {
        //return the stringified table, tableName
        return "";
    }
    
   /**
    * TODO: implement generic pull
    *
    *return String output format: "<success status> <data length> <data>"
    *                       ie.   "success 12 190 200 197 194 200 180 204 300 300 300 200 199"
    *                       where 12 is the number of bowling scores
    *
    */
    public String pullBowlingScores()
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet bowlingScores;
            int scoreCount = 0;//to keep track of number of scores. This is the first argument of the result String
            String result = "";

            bowlingScores = statement.executeQuery("SELECT Score\n" +
                                                    "FROM `db309vcc1`.`BowlingGames`;");

            while(bowlingScores.next())
            {
                scoreCount++;
                result += " " + bowlingScores.getInt("Score");
            }
            
            return "success " + scoreCount + result;
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return "fail 2 pull failure";
    }
    
   /**
    * TODO: query database for the tuple with the matching username, and check if the password matches
    * 
    * return String output format: "<success status> <username>"
    * 
    * @param username
    * @param password
    * @return 
    */
    public String login(String username, String password)//TODO: potentially add a check to see if the user is already logged in somewhere else
    {
        String userID = "";
        
        
        return userID;
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
            
            registeredUsers = statement.executeQuery("select Username "
                    +                                "from Users");
            
            while(registeredUsers.next())
            {
            	String Username = registeredUsers.getString(1);
                if(username.equals(" "+Username))
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
	    //if(userInfoChecked)
	    //{
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
	    //}
        //else
        //{
        //    Exception e = new Exception();
        //    throw e;
        //}
    }

    /**
	 * returns passwordHash corresponding to the given username
	 * 
	 * @param username
	 * @return
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
	 * returns UserID corresponding to the given username
	 * 
	 * @param username
	 * @return
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
    
    
}
