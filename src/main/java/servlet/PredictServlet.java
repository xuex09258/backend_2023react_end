package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.Predict;

@WebServlet("/predict")
public class PredictServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 允許特定來源訪問，或使用 "*" 來允許所有來源
		resp.setHeader("Access-Control-Allow-Origin", "*");
        // 其他CORS頭信息（根據需要添加）
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

		String predict = req.getParameter("predict") + "";
		String stockNo = req.getParameter("stockNo");
		String date = req.getParameter("date");
		System.out.print(stockNo + ", " + date + ", ");
		double predictPrice = 0;
		switch (predict) {
			case "smile":
				predictPrice = Predict.smile(stockNo, date);
				break;
			default:
				predictPrice = Predict.simpleRegression(stockNo, date);
				break;
		}
		
		System.out.println(predictPrice);
		resp.getWriter().print("{\"predictPrice\": " + predictPrice + "}");
	
	}
	
}
