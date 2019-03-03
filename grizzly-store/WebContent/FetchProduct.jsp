<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.pms.pojo.ProductPojo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fetch Student</title>
<link rel="stylesheet" type="text/css" href="FetchProduct.css">
<style> table, th, td { border: 1px solid black;
                        border-collapse: collapse;}   
                        
td {
  padding: 8px;
  text-align: left;
  border-bottom: 1px solid #ddd;
  border-right: 1px solid #ddd;
}

th {
  padding: 8px;
  text-align: centre;
  border-bottom: 2px solid #ddd;
  border-right: 1px solid #ddd;
}     
</style>
</head>

<body>
<div>
<%
if(session.getAttribute("role").equals("admin")){%>	
	<ul><li><a id="button2" href ="StoreServlet">Add Product</a></li></ul>
	<%ArrayList arrayList = (ArrayList) request.getAttribute("arrayList");

	Iterator<ProductPojo> iterator = arrayList.iterator();

	out.println("<table style=\"width:100%\">");
	out.println("<tr style=\"font-family:arial;color:lightgrey;\">");
	out.println("<th>Product List</th>");
	out.println("<th>Brand</th>");
	out.println("<th>Category</th>");
	out.println("<th>Rating</th>");
	out.println("<th></th>");
	out.println("</tr>");

	while(iterator.hasNext())
	{
		ProductPojo pojo = (ProductPojo) iterator.next();
		out.println("<tr style=\"font-family:arial;\">");
		out.println("<td>"+pojo.getProductName()+"</td>");
		out.println("<td>"+pojo.getProductBrand()+"</td>");	
		out.println("<td>"+pojo.getProductCategory()+"</td>");
		out.println("<td>"+pojo.getProductRating()+"</td>");
	%>
		<td><a id="button" href ="StoreServlet?id=<%= pojo.getProductId()%>"> Remove </a></td>
		
	<%out.println("</tr>");
	}
	out.println("</table>");
}
if(session.getAttribute("role").equals("vendor")){
	ArrayList arrayList = (ArrayList) request.getAttribute("arrayList");

	Iterator<ProductPojo> iterator = arrayList.iterator();

	out.println("<table style=\"width:100%\">");
	out.println("<tr style=\"font-family:arial;color:lightgrey;\">");
	out.println("<th>Product List</th>");
	out.println("<th>Brand</th>");
	out.println("<th>Category</th>");
	out.println("<th>Rating</th>");
	out.println("<th></th>");
	out.println("</tr>");

	while(iterator.hasNext())
	{
		ProductPojo pojo = (ProductPojo) iterator.next();
		out.println("<tr style=\"font-family:arial;\">");
		out.println("<td>"+pojo.getProductName()+"</td>");
		out.println("<td>"+pojo.getProductBrand()+"</td>");	
		out.println("<td>"+pojo.getProductCategory()+"</td>");
		out.println("<td>"+pojo.getProductRating()+"</td>");
	%>
		<td><a id="button" href ="StoreServlet?id=<%= pojo.getProductId()%>"> Remove </a></td>
		
	<%out.println("</tr>");
	}
	out.println("</table>");
}
%>
</div>
</body>
</html>