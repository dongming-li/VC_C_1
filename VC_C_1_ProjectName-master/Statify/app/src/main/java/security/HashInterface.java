package security;

/**
 * Hash interface to make passing and checking hashes easier
 *
 */
public interface HashInterface 
{
	/**
	 * Passes the plaintext hash through a hash for encryption purposes
	 * @param plainPassword The password of a user to be sent for encryption
	 * @return A Hashed and Salted password
	 */
	public String hashPass(String plainPassword);

	/**
	 * checks the password with the stored password
	 * @param candidate_password Passed candidate password to be interpreted
	 * @param stored_hash Passed stored hash to be interpreted
	 * @return True if passwords match
	 */
	public boolean checkPass(String candidate_password, String stored_hash);
}
