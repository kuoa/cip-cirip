package services.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.tools.ServicesUser;

public class Delete extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {						
					
		String user = req.getParameter("user");
		String pass = req.getParameter("pass");
		String mail = req.getParameter("mail");
		
		PrintWriter out = res.getWriter();
		JSONObject jo = ServicesUser.delete(user, pass, mail);
		
		out.println(jo.toString());
	}

}
