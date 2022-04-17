package security;

/**
<<<<<<< HEAD
 * Class to work with BCrypt encryption protocol
=======
 *
>>>>>>> 83b37e2988caeba6ae9ac56077eeffd9603bad87
 */
public class BCryptHash implements HashInterface
{


	/**
	 * Takes in a plain text password
	 *
	 * @param plainPassword The password of a user to be sent for encryption
	 * @return A hashed version of the password
	 */
	public String hashPass(String plainPassword) 
	{
		String pw_hash = BCrypt.hashpw(plainPassword, BCrypt.gensalt()); 
		return pw_hash;
	}

	/**

	 * This method checks the password with the passed candidate password and stored hash
	 * @param candidate_password Passed candidate password to be interpreted
	 * @param stored_hash Passed stored hash to be interpreted
	 * @return true if passwords match
	 */
	@Override
	public boolean checkPass(String candidate_password, String stored_hash)
	{
		if (BCrypt.checkpw(candidate_password, stored_hash))
		    return true;
		else
			return false;
	}
	
}
