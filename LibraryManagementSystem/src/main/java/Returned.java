

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.cj.x.protobuf.MysqlxCrud.Update;

/**
 * Servlet implementation class Returned
 */
public class Returned extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Returned() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
          int userid=Integer.parseInt(request.getParameter("userid"));
          int bookid=Integer.parseInt(request.getParameter("bookid"));
          
          
          try {
			Update(userid,bookid,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		
	}


	private void Update(int userid, int bookid, HttpServletResponse response) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");	      
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
        LocalDateTime myDateObj = LocalDateTime.now();
	       
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = myDateObj.format(myFormatObj);
      
	    
		PreparedStatement stmt = con.prepareStatement("update transactions set returneddate=? where bookid=? and userid=?" );
		stmt.setString(1, formattedDate);
		stmt.setInt(2,bookid );
		stmt.setInt(3,userid );		
		  if( stmt.executeUpdate()==1) {
	        	
			  Provide provide = new Provide();
			  
			  int currentcount = provide.currentcount(bookid);
			  
			  booksdb(bookid,currentcount);
			  
	        	JFrame f=new JFrame();  
	        	    JOptionPane.showMessageDialog(f,"successfully returned!");  
	        	
	     	   response.sendRedirect("index.html");  
	        }else {
	        	JFrame f=new JFrame();  
        	    JOptionPane.showMessageDialog(f,"Failed!, try again");  
        	
	     	   response.sendRedirect("returned.html");
	        }
		
		
	}


	private void booksdb(int bookid, int currentcount) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");	      
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
	        
        
        PreparedStatement ps = con.prepareStatement("update books set currentcount=? where bookid = ?");
       
        currentcount++;
        ps.setInt(1, currentcount);	
	     ps.setInt(2, bookid);	    
	     
	     
	     
	    ps.executeUpdate();
	     
		
	}

}
