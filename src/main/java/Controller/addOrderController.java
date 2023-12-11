package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.porderDao;
import Model.porder;

public class addOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public addOrderController() {
        super();
    }
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*1.取得porder訂單-->session
		 *2.新增到資料庫
		 *3.切換到finish 
		 */
		HttpSession session=request.getSession();
		
		porder p=(porder) session.getAttribute("P");
		
		new porderDao().add(p);
		
		response.sendRedirect("porder/finish.jsp");
		
	}

}

