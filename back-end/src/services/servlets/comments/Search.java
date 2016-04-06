package services.servlets.comments;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.tools.Services;
import services.tools.ServicesComments;

public class Search extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String key = req.getParameter("key");
		String query = req.getParameter("query");
		String forFriends = req.getParameter("forFriends");
		String myself = req.getParameter("myself");
		
		boolean friends = Boolean.parseBoolean(forFriends);
		boolean msf = Boolean.parseBoolean(myself);
		
		Services.addHeader(res);
		
		PrintWriter out = res.getWriter();
		JSONObject jo = ServicesComments.search(key, query, friends, msf);
		
		out.println(jo.toString());
		
	}		
}