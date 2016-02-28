package services.servlets.friends;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.tools.ServicesFriends;

public class Add extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String key = req.getParameter("key");
		String friendLogin = req.getParameter("friendLogin");
		
		PrintWriter out = res.getWriter();
		JSONObject jo = ServicesFriends.add(key, friendLogin);
		
		out.println(jo.toString());
		
	}		
}
