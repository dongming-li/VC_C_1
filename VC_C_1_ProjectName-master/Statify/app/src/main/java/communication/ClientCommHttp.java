package communication;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Cow on 10/8/2017.
 */

/**
 * This class handles all communication from the client to the server
 */
public class ClientCommHttp implements CommunicationInterface{

    //private String url = "http://localhost:8080/StatifyWeb";
    /**
     * This is the Url that the app communicates with for the server
     */
   private String url = "http://proj-309-vc-c-1.cs.iastate.edu:8080";
    //private String url = "http://10.0.2.2:8080/Statify";
   //Pp private String url = "http://localhost:8080/Statify/";




    /**
     * Takes in an arraylist (make sure it's ordered),
     * Passes a string containing the elements of the cmd concatenated and separated by %20 to the server
     * Returns a String containing the response from the server
     *
     * @param cmd Passed array list for the send method to send out
     * @return The response from the server after the command has been sent
     * @throws Exception
     */
    public String sendData(ArrayList cmd) throws Exception{

        enableStrictMode();

        //spawn thread to make the server call

        url = url + "/ServerComm?cmd=" + concatCmd(cmd);
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Host", "www.ex.com");
        //String urlParameters = "cmd=" + data;


        //Sending the parameters through dataoutput stream
        //con.setDoOutput(true);
        //DataOutputStream dOutStream = new DataOutputStream(con.getOutputStream());
        //dOutStream.writeBytes(urlParameters);
        //dOutStream.flush();
        //dOutStream.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Reponse Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLn;
        StringBuffer response = new StringBuffer();

        while ((inputLn = in.readLine()) != null) {
            response.append(inputLn);
        }
        in.close();
    	

        return response.toString();
    }

    /**
     * Legacy method for making server calls.
     *
     * @param cmd The command that the user wants to make
     * @param data The data that will be communicated
     * @return The response from the server after the command has been sent
     * @throws Exception
     */
    public String sendData(String cmd, String data) throws Exception{

        enableStrictMode();

        //spawn thread to make the server call
        data = URLEncoder.encode(data, "UTF-8");
        url = url + "/ServerComm?cmd=" + cmd + "%20" + data;

        System.out.println("URLURLURLURLURLURLURL: " + url);
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Host", "www.ex.com");
        //String urlParameters = "cmd=" + data;


        //Sending the parameters through dataoutput stream
        //con.setDoOutput(true);
        //DataOutputStream dOutStream = new DataOutputStream(con.getOutputStream());
        //dOutStream.writeBytes(urlParameters);
        //dOutStream.flush();
        //dOutStream.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Reponse Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLn;
        StringBuffer response = new StringBuffer();

        while ((inputLn = in.readLine()) != null) {
            response.append(inputLn);
        }
        in.close();


        return response.toString();
    }

    /**
     * takes in an arraylist (make sure it's ordered),
     * returns a string containing the elements of the cmd concatenated and separated by %20
     * TODO: maybe make this its own class
     *
     * @param cmd Command passed in
     * @return Blank meaningless String
     */
    public String concatCmd(ArrayList cmd)
    {
        Iterator i = cmd.iterator();
        String result;

        result = ""+ i.next();
        while(i.hasNext())
        {
            result += "%20" + i.next();
        }

        return "";
    }

    /**
     * Sets the machine To Strict MOde
     */
    //Temporary code to get apst Network on Main Thread Exception
    public  void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }
}

