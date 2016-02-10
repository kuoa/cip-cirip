package services.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.tools.ServicesUser;

public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {		
						
		int id = Integer.parseInt(req.getParameter("id"));
				
		PrintWriter out = res.getWriter();
		JSONObject jo = ServicesUser.logout(id);
		
		out.println(jo.toString());
		
	}
}
