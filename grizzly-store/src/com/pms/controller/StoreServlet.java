package com.pms.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pms.dao.LoginDao;
import com.pms.dao.ProductDao;
import com.pms.pojo.LoginPojo;
import com.pms.pojo.ProductPojo;

@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("Login")!=null) {
			String name=request.getParameter("user");
            String password=request.getParameter("pass");

            LoginPojo pojo=new LoginPojo();
            pojo.setUsername(name);
            //pojo.setPassword(password);

            pojo=LoginDao.loginValidationPassword(pojo);
            
            if(pojo.getStatus()==null)
            {
          	  RequestDispatcher rd=request.getRequestDispatcher("LoginUser.jsp");
                //request.setAttribute("error", "Invalid Password! Please try again!");
                rd.forward(request, response);            	  
            }
            else if(pojo.getStatus().equals("enabled"))
                   
            {
               if(pojo.getPassword().equals(password))
                   {                                           
                         HttpSession session=request.getSession();
                         session.setAttribute("user", name);
                         session.setAttribute("role", pojo.getRole());
                         session.removeAttribute("attempt");
                         /*ArrayList<ProductPojo> arrayList = ProductDao.fetchProductDetails();
             			 request.setAttribute("arrayList", arrayList);*/
             			 RequestDispatcher requestDispatcher = request.getRequestDispatcher("Layout.jsp");
             			 requestDispatcher.forward(request, response);                      
                   }
               else
                   {
                   HttpSession session=request.getSession();
                   String user=(String) session.getAttribute("user");
                   if(request.getParameter("user").equals(user))
                   {
                      	 String attempt=(String)session.getAttribute("attempt");
                      	 System.out.println(attempt);
                      	 			if(attempt==null)
                      	 			{
                      	 				 //sessionsetMaxInactiveInterval(20*60);
                                          session.setAttribute("user", name);
                                          session.setAttribute("attempt", "1");
                                          RequestDispatcher rd=request.getRequestDispatcher("LoginUser.jsp");
                                          //request.setAttribute("error", "Invalid Password! Please try again!");
                                          rd.forward(request, response);
                      	 			}
                      	 			else if(attempt.equals("1"))
                                       {
                      	 				int attemptInt=Integer.parseInt(attempt);
                                      	 session.setAttribute("attempt", (++attemptInt)+"");
                                           RequestDispatcher rd=request.getRequestDispatcher("LoginUser.jsp");
                                           //request.setAttribute("error", "Invalid Password! Please try again!");
                                           rd.forward(request, response); 
                                      	 
                                      	 	
                                              
                                       }
                                       else
                                       {
                                      	 session.invalidate();
                                           LoginDao.loginLock(name);
                                           RequestDispatcher rd=request.getRequestDispatcher("AccountLocked.html");
                                           //request.setAttribute("error", "You have attempted thrice with wrong password!!. Account is locked!!");
                                           rd.forward(request, response);  
                                       }
                         
                          
                     }
                     else
                     {
                         
                         //sessionsetMaxInactiveInterval(20*60);
                         session.setAttribute("user", name);
                         session.setAttribute("attempt", "1");
                         RequestDispatcher rd=request.getRequestDispatcher("LoginUser.jsp");
                         //request.setAttribute("error", "Invalid Password! Please try again!");
                         rd.forward(request, response); 
                    }
                   
                    
             }
            }
            else
            {
                   RequestDispatcher rd=request.getRequestDispatcher("AccountLocked.html");
                   //request.setAttribute("error", "Account is locked!!");
                   rd.forward(request, response); 
             }
            
            
     
     
            /*System.out.println(res);
            if(res.equals("max")){
                   RequestDispatcher rd=request.getRequestDispatcher("MaxCount.jsp");  
                   rd.forward(request, response);
            }else if(res.equals("success")){
                   RequestDispatcher rd=request.getRequestDispatcher("FetchServlet");  
                   rd.forward(request, response);
            }else{
                   RequestDispatcher rd=request.getRequestDispatcher("LoginFailed.jsp");  
                   rd.forward(request, response);
            }*/
		}
		
		if(request.getParameter("Add")!=null) {
			ArrayList<ProductPojo> arrayList = ProductDao.fetchProductDetails();
			request.setAttribute("arrayList", arrayList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("FetchProduct.jsp");
			requestDispatcher.forward(request, response);
		}
		
		if(request.getParameter("Logout")!=null) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginUser.html");
				requestDispatcher.forward(request, response);
			} else {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginUser.html");
				requestDispatcher.forward(request, response);
			}
		}
		
		if(request.getParameter("id")!=null) {
			String id = request.getParameter("id");
			int check = ProductDao.removeProduct(Integer.parseInt(id));

			if (check != 0) {
				
				ArrayList<ProductPojo> arrayList = ProductDao.fetchProductDetails();
				request.setAttribute("arrayList", arrayList);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("FetchProduct.jsp");
				requestDispatcher.forward(request, response);

			}
		}
	}


}
