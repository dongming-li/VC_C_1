package communication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;

/**
 * Class that adds new users and updates users to the database. implements newUser and ReturningUser
 */
public class User implements NewUser, ReturningUser
{
   /**
    * TODO: test
    * Adds a new user into the data base
    * @param email Passed email to be added
    * @param passwordHash Passed password hash to be hashed
    * @param username Passed userName to be hashed
    * @throws IOException 
    * @throws MalformedURLException
	* @returns True if add user was successful
    */
	public boolean addNewUser(String email, String passwordHash, String username) throws Exception
	{
		boolean success = false;
		ClientCommHttp c = new ClientCommHttp();//TODO: dependency inject ClientCommHttp

		String response = c.sendData("register", username + " " + email + " " + passwordHash);

		if(response.equals("success"))
		{
			success = true;
		}
		
		return success;
	}
	
   /**
    * TODO: test
    * 
    * Takes in a username, 
    *
    * @param username Passes username to be checked
    * @return False if username doesn't exist. Returns true otherwise.
    * @throws Exception 
    */
	public boolean checkUsername(String username) throws Exception
	{
		boolean success = false;
		ClientCommHttp c = new ClientCommHttp();//TODO: dependency inject ClientCommHttp
		String response = c.sendData("usernameCheck", username);

		System.out.println("RESPONSERESPONSERESPONSERESPONSERESPONSERESPONSE: " + response);

		if(response.equals("success"))
		{
			success = true;
		}
		return success;
	}
	
	/**
	 * returns passwordHash corresponding to the given username
	 * 
	 * @param username Username to be sent in to check Password
	 * @return passwordHash corresponding to the given username
	 * @throws Exception
	 */
	public String checkPassword(String username) throws Exception 
	{
		ClientCommHttp c = new ClientCommHttp();//TODO: dependency inject ClientCommHttp
		String response = "incorrect_username";
		if(checkUsername(username)) {
			response = c.sendData("passwordCheck", username);
			//System.out.println("returned by passwordCheck: " + response);
		}
		
		return response;
	}
	
	/**
	 * returns UserID corresponding to the given username
	 * 
	 * @param username Passes given username to getUserID
	 * @return UserId corresponding to the given username
	 * @throws Exception
	 */
	public int getUserID(String username) throws Exception 
	{
		ClientCommHttp c = new ClientCommHttp();//TODO: dependency inject ClientCommHttp
		String response = c.sendData("getUserID", username);
		Scanner scan = new Scanner(response);
		
		return scan.nextInt();
	}
}
