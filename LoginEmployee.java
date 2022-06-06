//import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class LoginEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/Json");
		response.setCharacterEncoding("UTF-8");
		Connection c = null;
		Statement s = null;
		ResultSet r = null;
		PrintWriter pw = response.getWriter();
		String empl_name=request.getParameter("empl_name");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "Current-Root-Password");
			s = c.createStatement();
			r = s.executeQuery("select * from employee where empl_name = "+empl_name+"");
	
			JSONArray jsonr = new JSONArray();    
			while(r.next()) {
				JSONObject jo = new JSONObject();
				
				jo.put("empl_id", r.getLong("empl_id"));
				jo.put("id", r.getLong("id"));
				jo.put("empl_name", r.getString("empl_name"));
				jo.put("phone_number", r.getLong("phone_number"));
				jo.put("place", r.getString("place"));
				jo.put("role", r.getString("role"));
				jsonr.add(jo);
				      }
			
			pw.println(jsonr);			
		}
		catch (Exception e) {
			e.printStackTrace();
	      
	      }
		finally {
		    try {
		        if(c != null)
		            c.close();
		        if(s != null)
		            s.close();
		    } catch(SQLException sqlee) {
		        sqlee.printStackTrace();
		    } finally { 
		        c = null;
		        s = null;
		    }
		}
		}
	}