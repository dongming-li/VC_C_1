

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServerComm
 */
@WebServlet("/ServerComm")
public class ServerComm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String msg;
	
	public void init() throws ServletException {
		msg = "Hey there!";
	}
	
    /**
     * Default constructor. 
     */
    public ServerComm() {
        // TODO Auto-generated constructor stub
    }

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
		//out.println("Your command: " + cmd);
		//out.println("You can send me a message just like you requesting this page through a port on my server and then I can run some code to do stuff on the SQL DB. Then I will give you a response. (This message is one)");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public void destroy() {
		// do nothing
	}

}
