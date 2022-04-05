

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;



/**
 * Servlet implementation class Provide
 */
public class Provide extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Provide() {
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
		
		
		
		int userid = Integer.parseInt(request.getParameter("userid"));
		int bookid = Integer.parseInt(request.getParameter("bookid"));
		String duedate= request.getParameter("duedate");
  
		try {
			if(already(userid,bookid)) {
				
				
				 JFrame f;    
				    f=new JFrame();  
				    JOptionPane.showMessageDialog(f,"Already you have this Book!");  
					response.sendRedirect("index.html");

				
			}
			
			
			else {
					try {
						if(booksdb(bookid,response))
						Insert(userid,bookid,duedate,response);
						else{
							response.sendRedirect("index.html");
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						response.sendRedirect("failed.html");
						e.printStackTrace();
					}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	
	private boolean already(int userid, int bookid) throws Exception {
		 Class.forName("com.mysql.cj.jdbc.Driver");	      
	        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
		        
	        
	        PreparedStatement ps = con.prepareStatement("select * from transactions where userid=? and bookid = ? and returneddate=?");
	      ps.setInt(1, userid);
	      ps.setInt(2, bookid);
	      ps.setString(3, "");
	      
	      ResultSet rs=ps.executeQuery();
	        while(rs.next()) {
	        	return true;
	        }

		
		
		return false;
	}

	private boolean booksdb(int bookid, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		 Class.forName("com.mysql.cj.jdbc.Driver");	      
	        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
		        
	        
	        PreparedStatement ps = con.prepareStatement("update books set currentcount=? where bookid = ?");
	        int currentcount= currentcount(bookid);
	        currentcount--;
	        ps.setInt(1, currentcount);	
		     ps.setInt(2, bookid);	    
		     
		     
		     if(currentcount>=0) {
		    	 ps.executeUpdate();
		     }else {
		    	 
		    	JFrame f=new JFrame();  
		    	    JOptionPane.showMessageDialog(f,"Not Available!");  
		    	    
		    	    return false;   
		     }
	      return true;
		
	}

	int currentcount(int bookid) throws Exception {
		 Class.forName("com.mysql.cj.jdbc.Driver");	      
	        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
		        
	        
	        PreparedStatement ps = con.prepareStatement("select currentcount from books where bookid=?");
	        ps.setInt(1, bookid);
	         ResultSet rs=ps.executeQuery();
	        while(rs.next()) {
	        	return rs.getInt(1);
	        }
	        
	       		return 0;
	}

	private void Insert(int userid, int bookid, String duedate, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		 Class.forName("com.mysql.cj.jdbc.Driver");	      
	        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
		        
	        
	        PreparedStatement stmt=con.prepareStatement("insert into transactions(userid,bookid,issueddate,duedate,returneddate)values(?,?,?,?,?)");
	        
	        
	        LocalDateTime myDateObj = LocalDateTime.now();
	       
	        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	        String formattedDate = myDateObj.format(myFormatObj);
	       
	        
	        stmt.setString(3, formattedDate);
	        stmt.setString(4, duedate);
         stmt.setInt(1, userid);
         stmt.setInt(2, bookid);
         stmt.setString(5, "");
         
        if( stmt.executeUpdate()==1) {
        	
        	JFrame f=new JFrame();  
        	    JOptionPane.showMessageDialog(f,"Added Successfully!");  
        	
     	   response.sendRedirect("index.html");  
        }else {
     	   response.sendRedirect("failed.html");
        }
		
		
	}

}
