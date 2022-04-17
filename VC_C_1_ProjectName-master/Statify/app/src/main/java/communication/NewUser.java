package communication;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 *interface for creating a new user
 */
public interface NewUser 
{
	/**
	 * Adds A new User to the server
	 * @param email Email to be passed in
	 * @param passwordHash Password Hash to be sent through BCrypt
	 * @param username Username to be sent
	 * @return True if addNewUser worked correctly
	 * @throws Exception
	 */
	public boolean addNewUser(String email, String passwordHash, String username) throws Exception;

	/**
	 * Checks to see if a username exists given a passed username
	 * @param username passed username to be checked
	 * @return true if the username exists
	 * @throws Exception
	 */
	public boolean checkUsername(String username) throws Exception;
}
