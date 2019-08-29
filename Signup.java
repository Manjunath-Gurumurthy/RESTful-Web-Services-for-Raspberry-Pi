

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restpi.RESTPi;
import org.restpi.model.Role;
import org.restpi.model.User;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/signup")
public class Signup extends HttpServlet {
	public RESTPi pi;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		String user = config.getInitParameter("username");
		String pass = config.getInitParameter("password");
		String url = config.getInitParameter("url");
		pi = new RESTPi.Builder(url).username(user).password(pass).setCompression(true).build();
		pi.auth();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher view = request.getRequestDispatcher("signup.html");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newuser = request.getParameter("username");
		String newpass = request.getParameter("password");
		String[] roles = request.getParameterValues("role");
		ArrayList<Role> userroles = new ArrayList<>();
		for (int i = 0; i < roles.length; i++) {
			Role temp = new Role(roles[i],true);
			userroles.add(temp);
		}
		User u = new User(newuser,newpass,userroles);
		pi.createUser(u);
		RequestDispatcher view = request.getRequestDispatcher("switch.html");
		view.forward(request, response);
	}

}
