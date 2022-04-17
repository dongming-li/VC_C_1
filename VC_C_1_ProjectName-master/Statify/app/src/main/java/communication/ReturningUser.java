package communication;

/**
 * Interface for ReturningUser
 */
public interface ReturningUser 
{
	/**
	 * Returns passwordHash corresponding to the given username
	 * 
	 * @param username Passed username to be checked
	 * @return PasswordHash corresponding to the given username
	 * @throws Exception
	 */
	public String checkPassword(String username) throws Exception;
	
	/**
	 * returns UserID corresponding to the given username
	 * 
	 * @param username username to be passed into the function
	 * @return Returns UserID corresponding to te given username
	 * @throws Exception
	 */
	public int getUserID(String username) throws Exception;
}
