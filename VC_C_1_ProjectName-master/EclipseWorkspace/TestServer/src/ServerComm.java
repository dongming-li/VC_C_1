import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements Java Servlets to receive data from the server and send it to the appropriate location for processing.
 * It will send back a response based on the request.
 */
@WebServlet("/ServerComm")
public class ServerComm extends HttpServlet {


	
	public void init() throws ServletException { }
	
    /**
     * Default constructor. 
     */
    public ServerComm() { }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		if(request.getParameterMap().containsKey("cmd")){
			String cmd  = request.getParameter("cmd");
			CommandHandler ch = new CommandHandler();
			PrintWriter out = response.getWriter();
			try {
				out.println(ch.commandHandler(cmd));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			PrintWriter out = response.getWriter();
			out.println("No cmd parameter given");
		}
	}
}
