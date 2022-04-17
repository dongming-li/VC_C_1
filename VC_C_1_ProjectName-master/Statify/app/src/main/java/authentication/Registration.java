package authentication;

import java.io.IOException;
import java.net.MalformedURLException;

import security.*;
import communication.*;

/**
 * Contains method for registering a new user account
 */
public class Registration implements RegistrationInterface
{
	private static NewUser newUser;
	private static HashInterface potato;

	/**
	 * Dependency injection of a NewUser and HashInterface
	 *
	 * @param n - NewUser object
	 * @param h - HashInterface object
	 */
	public Registration(NewUser n, HashInterface h)
	{
		newUser = n;
		potato = h;
	}
	
    /**
     * Enter user info, and if the username isn't already used and email equals emailVerify
	 * then password is hashed and a new user is created using username, the hashed password, and
	 * email.
     * note: does not log user in
     * 
     * @param username - Username entered by the user
     * @param email - Email entered by the user
     * @param emailVerify - Second email entered by the user to check that the email was entered
	 *                       correctly
     * @param password - plain text password entered by the user
     * @return - return true if the username isn't already used and email equals
	 * emailVerify
     * @throws Exception 
     */
	public boolean register(String username, String email, String emailVerify, String password) throws Exception
	{

		boolean success = false;
		int UserID = 0;
		
		if(email.equals(emailVerify))
		{
			if(!newUser.checkUsername(username))
			{
				String passwordHash = potato.hashPass(password);//dependency inject BCrypt
				
				success = newUser.addNewUser(email, passwordHash, username);
			}
		}
		
		return success;
	}
}
