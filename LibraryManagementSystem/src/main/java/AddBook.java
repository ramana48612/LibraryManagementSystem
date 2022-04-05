

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.swing.*;





/**
 * Servlet implementation class AddBook
 */
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		
		String bookname = request.getParameter("bookname");
		String authorname= request.getParameter("authorname");
		int count = Integer.parseInt(request.getParameter("count"));
		
		
		try {
			if(check(bookname,authorname,count)) {
				
				System.out.println("count : "+count);
				int[] array = getCount(bookname,authorname); 
				System.out.println(array[0]+" "+array[1]);
				
				
				
				System.out.println((array[0]+count)+" "+(count+array[1]));
				Update(array[2],array[0]+count,array[1]+count,response);
				int bookid = getbookid(bookname,authorname);
				JFrame f=new JFrame();  
        	    JOptionPane.showMessageDialog(f,"Book ID : "+bookid);  
        	
				
				response.sendRedirect("index.html");
				
			}else {
				try {
					Add(bookname,count,authorname,response);
					int bookid = getbookid(bookname,authorname);
					JFrame f=new JFrame();  
	        	    JOptionPane.showMessageDialog(f,"Book ID : "+bookid);  
	        	
					
				} catch (Exception e) {
					
				}
			}
			
			
		} catch (Exception e1) {
			
		}
		
		
		
		
		
	}

	private int getbookid(String bookname, String authorname) throws Exception {


		Class.forName("com.mysql.cj.jdbc.Driver");	      
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
	    
		PreparedStatement stmt = con.prepareStatement("select bookid from books where bookname=? and authorname=?");
    	
		stmt.setString(1, bookname);
	   	stmt.setString(2, authorname);
	   	 ResultSet rs=stmt.executeQuery();
	   	 
	   	 while(rs.next()) {
	   		 return rs.getInt(1);
	   	 }
	   	 
	   	 
		return 0;
		
	}

	private int[] getCount(String bookname, String authorname) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");	      
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
	    
		PreparedStatement stmt = con.prepareStatement("select * from books where bookname=? and authorname=?");
    	
   	 stmt.setString(1, bookname);
   	stmt.setString(2, authorname);
   	 ResultSet rs=stmt.executeQuery();
   	 
   	 System.out.println("after resultset");
   	 
   	int[] array = new int[3];
   	 while(rs.next()) {
   		array[0] = (rs.getInt(4));
   		array[1] = (rs.getInt(5));
   		array[2] = (rs.getInt(1));
   		 
   	 }
   	 return array;
	}


	private void Update(int id, int currentcount, int count,HttpServletResponse response) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");	      
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
       
	    
		PreparedStatement stmt = con.prepareStatement("update books set currentcount=?,count=? where bookid=?" );
		System.out.println(count);
		stmt.setInt(1,currentcount );
		stmt.setInt(2,count );
		stmt.setInt(3, id);
	
//		stmt.setString(3, bookname);
//		stmt.setString(4, authorname);
//	
		
		
		
	
	
		
		if(stmt.executeUpdate()==1) {
			 JFrame f;    
			    f=new JFrame();  
			    JOptionPane.showMessageDialog(f,"Updated Successfully!");  
			
		}else {
			response.sendRedirect("failed.html");
		}
		
		
		
		
		
	}

	private boolean check(String bookname, String authorname, int count) throws Exception {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");	      
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
	    
		PreparedStatement stmt = con.prepareStatement("select * from books where bookname=? and authorname=?");
    	
   	 stmt.setString(1, bookname);
   	stmt.setString(2, authorname);
   	 ResultSet rs=stmt.executeQuery();
   	 
   	 while(rs.next()) {
   		 
   		 return true;
   	 }
   	 return false;
		
	}

	private void Add(String bookname, int count, String authorname, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		 Class.forName("com.mysql.cj.jdbc.Driver");	      
	        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","");
		        
	        
	        PreparedStatement stmt=con.prepareStatement("insert into books(bookname,authorname,currentcount,count)values(?,?,?,?)");
	        
	        stmt.setString(1, bookname);
	        stmt.setString(2, authorname);
            stmt.setInt(3, count);
            stmt.setInt(4, count);
            
           if( stmt.executeUpdate()==1) {
        	   response.sendRedirect("index.html");  
           }else {
        	   response.sendRedirect("failed.html");
           }
	        
		        
		
	}
	
	
	
	
	

}
