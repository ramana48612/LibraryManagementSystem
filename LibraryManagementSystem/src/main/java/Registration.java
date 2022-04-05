

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

/**
 * Servlet implementation class Registration
 */
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
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
		
       String name = request.getParameter("name");
       String  phone = request.getParameter("phone");
       String email = request.getParameter("email");
       String aadhar = request.getParameter("aadhar");
       String gender = request.getParameter("gender");
       String dob = request.getParameter("dob");
       
       try {
		Insert(name,phone,email,aadhar,gender,dob,response);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		response.sendRedirect("failed.html");
	}
       
	}

	private void Insert(String name, String phone, String email, String aadhar, String gender, String dob, HttpServletResponse response) throws Exception {
		 Class.forName("com.mysql.cj.jdbc.Driver");	      
	        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
		        
	        
	        PreparedStatement stmt=con.prepareStatement("insert into users(name,phone,gender,dob,email,aadhar)values(?,?,?,?,?,?)");
	        
	        stmt.setString(1, name);
	        stmt.setString(2, phone);
	        stmt.setString(3, gender);
	        stmt.setString(4, dob);
	        stmt.setString(5, email);
	        stmt.setString(6, aadhar);
	        
	        if( stmt.executeUpdate()==1) {
	        	
	        	String useridString  = gettingid(name,aadhar,con);
	        	
	           JFrame f=new JFrame();  
	            JOptionPane.showMessageDialog(f,"Your ID is : "+useridString); 
	        	
	        	   response.sendRedirect("index.html");  
	           }else {
	        	   response.sendRedirect("failed.html");
	           }
	        
        
		
	}

	private String gettingid(String name, String aadhar, Connection con) throws Exception {
		// TODO Auto-generated method stub
		
		PreparedStatement stmt=con.prepareStatement("select userid from users where name=? and aadhar=?");
		
        
        stmt.setString(1, name);
        stmt.setString(2, aadhar);
        
        ResultSet rs=stmt.executeQuery();
      	 
      	 while(rs.next()) {
      		 
      		 return rs.getString(1);
      		 
      	 }
		
		
		
		return null;
	}

	

}
