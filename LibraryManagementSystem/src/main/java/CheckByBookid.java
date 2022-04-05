

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class CheckByBookid
 */
public class CheckByBookid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckByBookid() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int bookid = Integer.parseInt(request.getParameter("bookid"));
		 List<JSONObject> j1;
			PrintWriter out = response.getWriter();
			
			
			try {
			 	j1=fetch(response,bookid);
				
				
				out.write(j1.toString());
				out.flush();
				out.close();
			} catch (Exception e) {
				JSONObject j2=new JSONObject();
				j2.put("status", "failed");
				out.flush();
				out.close();
				out.write(j2.toJSONString());
				// TODO Auto-generated catch block
			    
			}
		 
	}
	
	
	
	
	private List<JSONObject> fetch(HttpServletResponse response, int bookid) throws Exception {
		List<JSONObject> ljs = new ArrayList();
	     
		
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");	    

		@SuppressWarnings("unchecked")
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");

		PreparedStatement ps = con.prepareStatement("select * from transactions where bookid =?");
   ps.setInt(1, bookid);
		
		ResultSet rs =   ps.executeQuery();

         while(rs.next()) {
        	  JSONObject jo = new JSONObject();
        	 jo.put("userid",rs.getString(1) );
        	 jo.put("bookid",rs.getString(2) );
        	 jo.put("issueddate",rs.getString(3) );
        	 jo.put("duedate",rs.getString(4) );
        	 jo.put("returneddate",rs.getString(5) );
        	
        	 
        	 ljs.add(jo);
         }

		
		return ljs;
	
	}


}
