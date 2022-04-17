import java.util.Scanner;


/**
 * 
 * Responsible for parsing through requests from servlet and sending data to appropriate route for processing and output
 *
 */
public class CommandHandler 
{
	/**
	 * The MySQL database connection from JDBC
	 */
    DatabaseConnection db;
    
    /**
     * Required empty constructor
     */
    public CommandHandler() { }
    

    /**
     * Parses through the request and sends the data to the appropriate route.
     * Some commands require multiple steps, those will be sent to their respective methods
     * @param commandAndData Command and data in stringified format
     * @return The output of the response
     * @throws Exception
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
            case "pullLayouts" :
            	result = pullLayoutsHandler();
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
            case "refresh" :
            	result = refresh(dataString);
            	break;
            case "downloadIncrement" :
            	result = downloadIncrement(dataString);
            	break;
            case "uploadLayout" :
            	result = uploadLayout(dataString);
            	break;
            case "update" :
            	result = updateCalcData(dataString);
            	break;
            case "updateLayout" :
            	result = updateLayout(dataString);
            	break;
			case "getUserInfo" :
            	result = getUserInfoHandler(dataString);
            	break;
            default :
                result = "fail Something went wrong with Command \"" + cmd + "\", Data: \"" + dataString + "\"";
                break;
        }
        
        db.close();//close connection to database
        return result;/**/
    }
    
    /**
	 * returns UserID corresponding to the given username
	 * 
	 * @param username The username to find the userID of
	 * @return The userID
	 * @throws Exception
	 */
    private String getUserIDHandler(String username) 
    {
		// TODO Auto-generated method stub
		return db.getUserID(username);
	}

    /**
	 * Gets the password hash of the sent username
	 * 
	 * @param username The username to find the password hash of
	 * @return returns passwordHash corresponding to the given username
	 * @throws Exception
	 */
	private String passwordCheckHandler(String username) 
	{
		// TODO Auto-generated method stub
		return db.checkPassword(username);
	}

	/**
	 * Handles all "add" methods"
	 * @param dataString Data to be added
	 * @return Output of command
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
            case "calcData" :
            	result = addCalculatedData(data);
            	break;
            case "layout" :
            	result = addLayout(data);
            	break;
            default :
                result = "fail Something went wrong with Command \"add\", Subcommand \"" + cmd + "\", Data: \"" + data + "\"";
                break;
        }
        scan.close();
        return result;
    }
    
    /**
     * Adds calculated data point into database
     * @param dataString Data of calculated data point "<userID>;<activityName>;<expressionName>;<expression>"
     * @return Standard message
     */
    public String addCalculatedData(String dataString){
    	Scanner scan = new Scanner(dataString);
    	scan.useDelimiter(";");
    	
    	String userID = scan.next();
    	String activityName = scan.next();
    	String expressionName = scan.next();
    	String expression = scan.next();
    	
    	db.addCalculatedData(userID, activityName, expressionName, expression);
    
    	scan.close();
    	return "success You made it to the addCalculatedData method! Your data: \"" + dataString + "\" was recorded!";
    }
    
   /**
 	* Checks if sent username already exists
    * @param username Username to be checked if exists
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
     * Handles data related to registering a user account 
     * @param data Account data from user registration "<username><email><password>"
     * @return Standard message
     * @throws Exception
     */
    public String registerHandler(String data) throws Exception
    {
    	Scanner scan = new Scanner(data);
    	
    	String username = scan.next();
    	String email = scan.next();
    	String password = scan.next();
    	
    	db.addNewUser(username, email, password);
    	
    	scan.close();
    	return "success";
    }
    
    /**
     * Updates the user data with the data from the server. Returns both the layout data and the calculated data module data.
     * @param dataString Data related to refreshing the user data from the database "<userID>"
     * @return serialized form of layout data and calculated data module data
     */
    public String refresh(String dataString){
    	String result = "";
    	
    	String layouts = db.getLayouts(dataString) + ";;";
    	result += layouts;
    	Scanner scan = new Scanner(layouts);
    	scan.useDelimiter(";");
    	int numLayouts = scan.nextInt();
    	for(int i = 0; i < numLayouts; i++){
    		result += db.getCalculateData(dataString, scan.next());
    		scan.next();
    	}
    	scan.close();
    	return result;
    }
    
    /**
     * Prepares the input for the database handler of updating calculated data point
     * @param dataString Data relevant to updating a calculated data point "<UserID>%20<activityName>%20<expressionName>%20<newExpression>"
     * @return Standard message
     */
    public String updateCalcData(String dataString){
    	String result = "";
    	
    	Scanner scan = new Scanner(dataString);
    	scan.useDelimiter("%20");
    	String userID = scan.next();
    	String activityName = scan.next();
    	String expressionName = scan.next();
    	String newExpression = scan.nextLine();
    	newExpression = newExpression.substring(3);
    	
    	db.updateCalculateData(userID, activityName, expressionName, newExpression);
    	
    	return "SUCCESS";
    }
    
    /**
     * Prepares adding a layout to the database
     * @param dataString Data relevant to adding a layout ""<UserID>%20<layoutName>%20<layoutData>"
     * @return Standard message
     */
    public String addLayout(String dataString){
    	Scanner scan = new Scanner(dataString);
    	scan.useDelimiter("%20");
    	String userID = scan.next();
    	String layoutName = scan.next();
    	String layoutData = scan.next();
    	
    	db.addLayout(userID, layoutName, layoutData);
    	
    	scan.close();
    	return dataString;
    }
    
    /**
     * Prepares updating a layout to the database
     * @param dataString Data relevant to updating a layout "<userID>%20<layoutName>%20<layoutData>"
     * @return Stand message
     */
    public String updateLayout(String dataString){
    	Scanner scan = new Scanner(dataString);
    	scan.useDelimiter("%20");
    	String userID = scan.next();
    	String layoutName = scan.next();
    	String layoutData = scan.next();
    	
    	db.editLayout(userID, layoutName, layoutData);
    	
    	scan.close();
    	return dataString;
    }
    
    /**
     * Prepares pulling layouts from the database
     * @return Layout data from database
     */
    public String pullLayoutsHandler() {
    	String data = db.pullUploadedLayouts();
    	return data;	
    }
    
    /**
     * Prepares incrementing the numDownloads for uploaded layouts in database
     * @param dataString Data relevant to incrementing value "<layoutName>;<userID>;<numDownloads>"
     * @return Standard message
     */
    public String downloadIncrement(String dataString) {
    	Scanner scan = new Scanner(dataString);
    	scan.useDelimiter(";");
    	
    	String LayoutName = scan.next();
    	String ID = scan.next();
    	int downNumber = scan.nextInt();
    	
    	String data = db.downloadIncrement(downNumber, ID, LayoutName);
    	scan.close();
    	return data;
    }
    
    /**
     * Prepares adding a layout to the uploaded layouts table 
     * @param dataString Data relevant to uploaded layout "<UserID>;<layoutName>;<layoutData>"
     * @return Standard message
     */
    public String uploadLayout(String dataString) {
    	Scanner scan = new Scanner(dataString);
    	
    	scan.useDelimiter(";");
    	String UserID = scan.next();
    	String layoutName = scan.next(); 
    	String LayoutData = scan.next();
    	String numberOfDownloads = "0";
    	
    	return db.uploadLayout(UserID,layoutName,LayoutData, numberOfDownloads);
    }
	
	/**
     * Takes in userID and and returns corresponding username and rank 
     * @param dataString Data relevant to getting user data "<userID>"
     * @return Corresponding username and rank of sent userID
     */
    public String getUserInfoHandler(String dataString)
    {
    	Scanner scan = new Scanner(dataString);
    	int userID = scan.nextInt();
    	
    	return db.getUserInfo(userID);
    }
}
