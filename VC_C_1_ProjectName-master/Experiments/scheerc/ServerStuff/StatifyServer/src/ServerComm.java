import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet("/ServerComm")
public class ServerComm extends HttpServlet {

        private String msg;

        public void init() throws ServletException {
        	
        }

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

                //response.setContentType("text/html");
                if(request.getParameterMap().containsKey("cmd")){
                        String cmd  = request.getParameter("cmd");
                        CommandHandler ch = new CommandHandler();
                        PrintWriter out = response.getWriter();
                        out.println(ch.commandHandler(cmd));
                }
                else{
                        PrintWriter out = response.getWriter();
                        out.println("No cmd parameter given");
                }
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                doGet(request, response);
        }
        public void destroy() {
                // do nothing
        }

}
