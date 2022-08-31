package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.utils.ConnectionUtil;

public class EmployeeDao implements Dao<Employee> {

	@Override
	public void addInstance(Employee instance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Employee> getAllInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getByAttribute(String attribute) {
		try(Connection connection = ConnectionUtil.getConnection()){
			String query = "select * from employees where username = ?";
			PreparedStatement pstmt = connection.prepareStatement(query); //conevert to prepared statement
			pstmt.setString(1, attribute);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Employee(rs.getInt("employee_id"), rs.getString("username"), rs.getString("pw"));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return null;
	}

	@Override
	public Employee getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
