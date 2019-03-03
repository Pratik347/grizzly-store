package com.pms.dao;

import com.pms.pojo.LoginPojo;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
public static boolean loginValidation(LoginPojo pojo) {
		
		Connection connection = DBUtil.makeConnection();
		Statement statement = null;
		boolean check=false;
		try {
			statement = connection.createStatement();
           
			ResultSet resultSet = statement.executeQuery("Select * from login_user where login_username='"+pojo.getUsername()+"' and login_password='"+pojo.getPassword()+"'");
			if (resultSet.next())
			{
				check=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return check;
	}

       static public int loginLock(String name)
       {

              Connection connection = DBUtil.makeConnection();
              Statement statement = null;
              String status="disabled";

              int rowsAffected = 0;
              try {
                     statement = connection.createStatement();
                     rowsAffected = statement.executeUpdate("UPDATE login_user SET login_status ='" +status+ "' WHERE login_username ='" + name + "'");
                                                
                     statement.close();
              } catch (SQLException e) { 

                     e.printStackTrace();
              }
              return rowsAffected;
              
       }

	public static LoginPojo loginValidationPassword(LoginPojo pojo) {
		Connection connection = DBUtil.makeConnection();
		Statement statement = null;
		boolean check=false;
		try {
			statement = connection.createStatement();
           
			ResultSet resultSet = statement.executeQuery("Select * from login_user where login_username='"+pojo.getUsername()+"'");
			if (resultSet.next())
			{
				pojo.setPassword(resultSet.getString(2));
				pojo.setRole(resultSet.getString(3));
				pojo.setStatus(resultSet.getString(4));
				//check=true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pojo;
	}

}
