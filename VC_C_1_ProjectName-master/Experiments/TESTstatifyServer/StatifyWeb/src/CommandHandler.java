

import java.util.Scanner;


//package server;


public class CommandHandler 
{
    //private int[] bowlingScores = {190, 200, 197, 194, 201, 180, 204, 300, 300, 300, 200, 199};//temporary and arbitrary
    DatabaseConnection db;
    
    public CommandHandler()
    {
        
    }
    
   /**
    *
    *commandAndData String input format: "<cmd> <dataString>"
    *               
    *
    *return String output format: "<success status> <message based on cmd>"
    * @throws Exception 
    *                       
    */
    public String commandHandler(String commandAndData) throws Exception
    {
        db = new DatabaseConnection();//make a connection to database
        Scanner scan = new Scanner(commandAndData);
        String cmd;
        String dataString;
        
        
        cmd = scan.next();
        dataString = scan.nextLine();
        
        String result;
        switch(cmd)
        {
            case "add" :
                result = addHandler(dataString);
                break;
            case "pull" :
                result = pullHandler(dataString);
                break;
            case "remove" : 
                result = removeHandler(dataString);
                break;
            case "usernameCheck" : 
                result = usernameCheckHandler(dataString);
                break;
            case "register" : 
                result = registerHandler(dataString);
                break;
            case "passwordCheck" : 
                result = passwordCheckHandler(dataString);
                break;
            case "getUserID" : 
                result = getUserIDHandler(dataString);
                break;
            default :
                result = "fail Something went wrong with Command \"" + cmd + "\", Data: \"" + dataString + "\"";
                break;
        }
        
        db.close();//close connection to database
        return result;
    }
    
    /**
	 * returns UserID corresponding to the given username
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
    private String getUserIDHandler(String username) 
    {
		// TODO Auto-generated method stub
		return db.getUserID(username);
	}

    /**
	 * returns passwordHash corresponding to the given username
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private String passwordCheckHandler(String username) 
	{
		// TODO Auto-generated method stub
		return db.checkPassword(username);
	}

   /**
    * TODO: fix these comments because they aren't necessarily right
    * 
    *dataString input format: "<score>"
    *                    ie.  "201"
    *
    *return String output format: "<success status> <message>"
    *                       ie.   "success You made it to the addHandler method! Your score: "201" was recorded!"
    *
    *note: in a successful execution of the addHandler method, the output message doesn't really matter (at least I not yet)
    */
    public String addHandler(String dataString)
    {
        Scanner scan = new Scanner(dataString);
        String cmd;
        String data;
        
        
        cmd = scan.next();
        data = scan.nextLine();
        
        String result;
        switch(cmd)
        {
            case "activity" :
                result = addActivity(data);
                break;
            case "module" :
                result = addModule(data);
                break;
            case "data" : 
                result = addData(data);
                break;
            default :
                result = "fail Something went wrong with Command \"add\", Subcommand \"" + cmd + "\", Data: \"" + data + "\"";
                break;
        }
        
        return result;
    }
    
   /**
    * 
    * @param dataString
    * @return 
    */
    public String addActivity(String dataString)
    {
        Scanner scan = new Scanner(dataString);
        
        db.add_Activity(scan.next(), scan.next());
        
        
        return "success You made it to the addActivity method! A new activity: \"" + dataString + "\" has been added!";
    }
    
   /**
    * 
    * @param dataString
    * @return 
    */
    public String addModule(String dataString)
    {
        Scanner scan = new Scanner(dataString);
        
        db.add_Module(scan.next(), scan.next(), scan.nextInt(), scan.next());
        
        
        return "success You made it to the addModule method! ";
    }
    
   /**
    * TODO
    * @param dataString
    * @return 
    */
    public String addData(String dataString)
    {
        Scanner scan = new Scanner(dataString);
        
        db.add_Data(scan.next(), scan.next(), scan.next());
        
        
        return "success You made it to the addHandler method! Your score: \"" + dataString + "\" was recorded!";
    }
    
   /**
    * TODO: fix these comments because they aren't necessarily right
    * 
    *dataString input format: "" (empty String or something arbitrary)
    *
    *return String output format: "<success status> <data length> <data>"
    *                       ie.   "success 12 190 200 197 194 200 180 204 300 300 300 200 199"
    *                       where 12 is the number of bowling scores
    *
    *note: in this version of the pullHandler method, the dataString input doesn't really matter (at least I not yet)
    */
    public String pullHandler(String tableName)
    {
        String table = db.pullTable(tableName);
        
        return table;
    }
    
    
   /**
    * if user already exists returns "fail";
    * else returns "success";
    * 
    * @param username
    * @return
    */
    public String usernameCheckHandler(String username)
    {
    	String result = "fail";
    	if(db.checkUsername(username))
    	{
    		result = "success";
    	}
    	
    	return result;
    }
    
   /**
    * TODO: test
    * 
    * input format: data = "<username> <email> <password>"
    * 
    * returns "success" if it was successful in creating a new user
    * 
    * @param data
    * @return
    * @throws Exception 
    */
    public String registerHandler(String data) throws Exception
    {
    	Scanner scan = new Scanner(data);
    	
    	String username = scan.next();
    	String email = scan.next();
    	String password = scan.next();
    	
    	db.addNewUser(username, email, password);
    	
    	return "success";
    }
    
    public String removeHandler(String dataString)
    {
        
        String result = "";
        return result;
    }
}
