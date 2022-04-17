package authentication;


/**
 * Methods for logging a user in.
 */
public interface LoginInterface
{
    /**
     * Checks if passwordIn matches the password corresponding to the user with username is correct;
     * if it is, the userID corresponding to username is returned
     *
     * @param username - username entered by user
     * @param passwordIn - password entered by user
     * @return - returns User ID if username exist, else returns 0
     * @throws Exception
     */
    public int userID(String username, String passwordIn) throws Exception;

    /**
     * takes in userID,
     * returns corresponding Rank
     *
     * @param userID - User ID
     * @return - returns rank corresponding to userID
     * @throws Exception
     */
    public int getUserInfo(int userID) throws Exception;
}
