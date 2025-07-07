package test;


import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


import com.example.demo.model.UserEntity;


public class Test {
	 // Database connection details
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=Data_Base;encrypt=true;trustServerCertificate=true;";
    private static final String JDBC_USERNAME = "user=sa;";
    private static final String JDBC_PASSWORD = "password=Eason901215";
    
	public static void main(String[] args) {
		/*
		Test test = new Test();
		
		UserEntity inputEntity = new UserEntity();
		inputEntity.setUserName("Eason");
		inputEntity.setPassword("Eason901215");
		inputEntity.setRole("admin");
		
		UserEntity outputEntity = test.findUserByUsername(inputEntity.getUserName());
		if (outputEntity != null) {
			System.out.println("Query Successfully!");
		}else {
			System.out.println("Account:" + inputEntity.getUserName()+" is not founded!");
		}
		*/
	}
    
	public UserEntity findUserByUsername(String username) {
	    String query = "SELECT id, username, password, role FROM users WHERE username = ?";
		//String query = "SELECT * FROM Stocks";
	    UserEntity return_entity = null;

	    try {
	        // Corrected the connection string format
	        Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Data_Base;user=sa;password=Eason901215;encrypt=true;trustServerCertificate=true;");
	        
	        System.out.println("Log in to SQL successfully!");
	        System.out.println("Executing Query!");

	        // Use PreparedStatement to prevent SQL injection and properly set parameters
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setString(1, username);  // Set the username parameter to the query
	        
	        // Execute the query and get the ResultSet
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            return_entity = new UserEntity();
	            return_entity.setId(rs.getLong("id"));
	            return_entity.setUsername(rs.getString("username"));
	            return_entity.setPassword(rs.getString("password"));
	            return_entity.setRole(rs.getString("role"));
	        }
	        
	        System.out.println("Connection close");
	        connection.close();
	    } catch (SQLException e) {
	        System.out.println(e);
	    }

	    return return_entity;
	}

    
	public boolean saveUser(UserEntity userEntity) {
        String query = "SELECT id, username, password, role FROM users WHERE username = ?";
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the username parameter
            stmt.setString(1, userEntity.getUsername());
            
            // Execute the query and check if the account already exists
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Account " + userEntity.getUsername() + " is already existed.");
                    return false;
                }
            }

            // If the user does not exist, insert the new user into the database
            query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(query)) {
                insertStmt.setString(1, userEntity.getUsername());
                insertStmt.setString(2, userEntity.getPassword());
                insertStmt.setString(3, userEntity.getRole());
                insertStmt.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();  // Handle the error appropriately
        }
        
        return false;
    }
}

