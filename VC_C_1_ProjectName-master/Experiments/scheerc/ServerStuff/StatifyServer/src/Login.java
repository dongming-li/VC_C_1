import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


@WebServlet("/Login")
public class Login extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!request.getParameterMap().containsKey("username")) {
			PrintWriter out = response.getWriter();
			out.println("Please fill in Username field");
		}
		
		if(!request.getParameterMap().containsKey("password")) {
			PrintWriter out = response.getWriter();
			out.println("Please fill in password field");
		}
		//Todo call password hash from db
		//PasswordStorage.verifyHash(password)
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
