

import jakarta.servlet.ServletException;

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
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<JSONObject> j1;
		PrintWriter out = response.getWriter();
		
		String bookname = request.getParameter("bookname");

		try {
		 	j1=fetch(response,bookname);
			
			
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

	private List<JSONObject> fetch(HttpServletResponse response, String bookname) throws Exception {
		List<JSONObject> ljs = new ArrayList();
	     
		
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");	    

		@SuppressWarnings("unchecked")
		Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");

		PreparedStatement ps = con.prepareStatement("select * from books where bookname like '%"+bookname+"%' ");

		
		ResultSet rs =   ps.executeQuery();

         while(rs.next()) {
        	  JSONObject jo = new JSONObject();
        	 jo.put("bookid",rs.getString(1) );
        	 jo.put("bookname",rs.getString(2) );
        	 jo.put("authorname",rs.getString(3) );
        	 jo.put("currentcount",rs.getString(4) );
        	 jo.put("count",rs.getString(5) );
        	
        	 
        	 ljs.add(jo);
         }

		
		return ljs;
	
	}

}
