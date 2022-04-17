package communication;


import java.util.ArrayList;

/**
 * Created by ohcsu_000 on 11/27/2017.
 *
 */

/**
 * Interface for the communicationInterface
 */
public interface CommunicationInterface
{
    /**
     * Sends data based on the arraylist command sent in
     * @param cmd The arraylist of Command and data to be acted on
     * @return The response from the server
     * @throws Exception
     */
    public String sendData(ArrayList cmd) throws Exception;

    /**
     * takes in an arraylist (make sure it's ordered),
     * returns a string containing the elements of the cmd concatonnated and separated by %20
     * TODO: maybe make this its own class
     *
     * @param cmd passed in command and data to be interpreted
     * @return String response from server
     */
    public String concatCmd(ArrayList cmd);
}
