package com.pms.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.pms.pojo.ProductPojo;

public class ProductDao {
	
	public static ArrayList<ProductPojo> fetchProductDetails() {
		Connection connection = DBUtil.makeConnection();
		Statement statement = null;
		ArrayList<ProductPojo> productDetails = new ArrayList<ProductPojo>();
		
		try {
			statement = connection.createStatement();

			ResultSet resultSet = statement
					.executeQuery("select p.product_id,p.product_name,p.product_brand,p.product_description,p.product_category,p.product_rating,p.product_price,i.inventory_buffer from product_management p,inventory_management i where p.product_id=i.product_id");

			while (resultSet.next())
			{
				ProductPojo pojo = new ProductPojo();
				pojo.setProductId(resultSet.getString(1));
				pojo.setProductName(resultSet.getString(2));
				pojo.setProductBrand(resultSet.getString(3));
				pojo.setProductDescription(resultSet.getString(4));
				pojo.setProductCategory(resultSet.getString(5));
				pojo.setProductRating(resultSet.getString(6));
				pojo.setProductPrice(resultSet.getString(7));
				pojo.setProductBuffer(resultSet.getString(8));
				pojo.setProductStock("0");
				productDetails.add(pojo);
			}

			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productDetails;
	}
	
	
	public static int removeProduct(int id) {
		int rowAffected = 0;
		try {
			Connection connection = DBUtil.makeConnection();
			Statement statement = connection.createStatement();

			rowAffected = statement.executeUpdate("delete from product_management where product_id=" + id);

			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowAffected;
	}

}
