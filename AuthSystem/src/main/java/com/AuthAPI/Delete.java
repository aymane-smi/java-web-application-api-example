package com.AuthAPI;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;

public class Delete extends HttpServlet{
	//mysql server configuration
	String url="jdbc:mysql://localhost:3306/servlet_user";
	String user = "root";
	String password = "123@Password";
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException{
		
		String username = req.getParameter("username");
		
		String query = String.format("DELETE FROM user WHERE ", username);
		
		res.setHeader("Content-Type", "application/json");
		
		PrintWriter out = res.getWriter();
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url, user, password);
			
			Statement statement = con.createStatement();
			
			ResultSet result = statement.executeQuery(query);
			
			while(result.next()) {
				
				res.setStatus(200);
				
				out.println(String.format("{\nmessage: user(%s) was deleted}", username));
			}
		}catch(ClassNotFoundException e) {
			
			res.setStatus(500);
			
			out.println("{\nmessage: Something went wrong in the server!\n}");
			
		}catch(SQLException e) {
			
			res.setStatus(500);
			
			out.println("{\nmessage: SQL ERROR!\n}");
		}
	}
}
