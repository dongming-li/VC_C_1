package authentication;

/**
 * Contains method for registering a new user account
 */
public interface RegistrationInterface
{
    /**
     * Abstract method for registering a user
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
    public boolean register(String username, String email, String emailVerify, String password) throws Exception;
}
