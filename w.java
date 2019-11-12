package dbConnection;

import java.sql.*;

public class ClientSQL {

	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost:3306/sakila";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "password";
	   
	   static ResultSet rs = null;
	   static PreparedStatement pstmt = null;
	   
	   private static java.sql.Timestamp getCurrentTimeStamp() {

			java.util.Date today = new java.util.Date();
			return new java.sql.Timestamp(today.getTime());

		}
	   
   
	   public static Connection getConnection(Connection conn){
		   try{
			      // Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			   }  
		   
		   return conn;
		   
		   }
	   
	   public static ResultSet select(Connection conn){
		   System.out.println("Creating statement....");
		   String sql;
		   sql = "SELECT * FROM actor";
		   try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return rs;
	   }
	   
	   public static void display(ResultSet rs){
		   
		 //STEP 5: Extract data from result set
		      try {
				while(rs.next()){
				     //Retrieve by column name
				     int id  = rs.getInt("actor_id");
				     //int age = rs.getInt("age");
				     String first = rs.getString("first_name");
				     String last = rs.getString("last_name");

				     //Display values
				     System.out.print("ID: " + id);
				    // System.out.print(", Age: " + age);
				     System.out.print(", First: " + first);
				     System.out.println(", Last: " + last);
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   
	   public static void insert(Connection conn){
		   System.out.println("Inserting records into the table...");
		   rs = null;
		   try{
			  String sql = "INSERT INTO actor (actor_id, first_name, last_name, last_update) VALUES (?, ?, ?, ?)";
		      pstmt = conn.prepareStatement(sql);
		      pstmt.setInt(1, 202);
		      pstmt.setString(2, "Sunny");
		      pstmt.setString(3, "Leone");
		      pstmt.setTimestamp(4, getCurrentTimeStamp());
		      int rowsInserted = pstmt.executeUpdate();
		      rs=pstmt.getResultSet();
		      while(rs.next()){
		    	  System.out.println(rs.getInt("actor_id"));
		      }
		      if (rowsInserted > 0) {
		    	    System.out.println("A new actor was inserted successfully!");
		    	}
			  

		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }
	   }
	   
	   
	   public static void main(String[] args) {
		
		   Connection conn = null;   
		   conn = getConnection(conn);
		   if (conn != null){
			   System.out.println("Connected database successfully...");
		   }
		  
		   /* SELECT QUERY*/
		   
		   ResultSet rs = select(conn);
		   //System.out.println(rs);
		   
		   /* Display Data */
		   display(rs);
		   
		   /* Insert Data */
		   insert(conn);

		   
		   /* CLOSING CONNECTION */
		   try{
			   rs.close();
		   }catch(SQLException se3){
			   se3.printStackTrace();
		   }
		   try{
		       if(pstmt!=null)
		          pstmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }

	   System.out.println("Goodbye!");
	}
}


