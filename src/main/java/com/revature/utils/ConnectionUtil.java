package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionUtil {

	private static Connection con;
	
	private ConnectionUtil() {
		con = null;
	}
	
	public static Connection getConnection() {
		
		try {
			if(con != null && !con.isClosed()) {
				return con;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String url, user, pass;
		
		try {
			Class.forName("org.postgresql.Driver");
			 
			url = System.getenv("db_url");
			user = System.getenv("db_username");
			pass = System.getenv("db_password");
			
			con = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;	
	}
}
