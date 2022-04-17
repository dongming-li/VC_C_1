package authentication;

import java.util.Scanner;

import communication.*;
import security.*;

/**
 * Methods for logging a user in.
 */
public class Login implements LoginInterface
{
	private static ReturningUser user;
	private static HashInterface potato;

	/**
	 * Dependency injection of a NewUser and HashInterface
	 *
	 * @param n - NewUser
	 * @param h - HashInterface
	 */
	public Login(ReturningUser n, HashInterface h)
	{
		user = n;
		potato = h;
	}

	/**
	 * Checks if passwordIn matches the password corresponding to the user with username is correct;
	 * if it is, the userID corresponding to username is returned
	 *
	 * @param username - username entered by user
	 * @param passwordIn - password entered by user
	 * @return - returns User ID if username exist, else returns 0
	 * @throws Exception
	 */
	public int userID(String username, String passwordIn) throws Exception
	{
		int UserID = 0;
		String passwordHash = user.checkPassword(username);

		if(passwordHash != "incorrect_username") {
			if (potato.checkPass(passwordIn, passwordHash)) {
				UserID = user.getUserID(username);
			}
		}
		
		return UserID;
	}


	/**
	 * takes in userID,
	 * returns corresponding Rank
	 *
	 * @param userID - User ID
	 * @return - returns rank corresponding to userID
	 * @throws Exception
	 */
	public int getUserInfo(int userID) throws Exception
	{
		ClientCommHttp c = new ClientCommHttp();
		String result = c.sendData("getUserInfo", "" + userID);
        System.out.println(result);
        Scanner scan = new Scanner(result);
		String doesntmatter = scan.next();
        int rank = scan.nextInt();

		return rank;
	}
}
