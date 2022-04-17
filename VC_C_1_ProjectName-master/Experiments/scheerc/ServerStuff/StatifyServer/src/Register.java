import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import passwordhashing.PasswordStorage;


@WebServlet("/Register")
public class Register extends HttpServlet {

	public void init() throws ServletException {
    	
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(
				request.getParameterMap().containsKey("username") &&
				request.getParameterMap().containsKey("email") && 
				request.getParameterMap().containsKey("password") && 
				request.getParameterMap().containsKey("password2")
				) {
			String username  = request.getParameter("username");
			String email  = request.getParameter("email");
			if(!checkPasswords(request.getParameter("password"), request.getParameter("password2")){
				PrintWriter out = response.getWriter();
				out.println("Passwords entered did not match");
			}
			String password  = request.getParameter("password");
			String passHash = PasswordStorage.createHash(password);
		}
		
		else {
			PrintWriter out = response.getWriter();
            out.println("Please fill in all required fields.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/*
	 * Checks to see if both passwords entered are the same
	 */
	private Boolean checkPasswords(String password1, String password2) {
		
		if(password1.equals(password2)) {
			return true;
		}
		else {
			return false;
		}
		
		
	}

}
