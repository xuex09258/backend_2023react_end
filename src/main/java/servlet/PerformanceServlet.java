package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pref")
public class PerformanceServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().print("{\"Hello\": \"PerformanceServlet\"}");
		System.out.print("11111");
	}
	
	/*
	 * 當您的React前端應用發起跨來源的POST請求時，瀏覽器會首先發送一個OPTIONS請求，這被稱為CORS預檢請求。
	 * 這是瀏覽器的安全機制，用來確認伺服器是否允許跨來源請求。
	 * 即使您的React代碼中指定了POST方法，瀏覽器在實際發送POST請求之前，仍會自動發送OPTIONS請求。
	 * 因此，服務器必須能夠響應OPTIONS請求並返回適當的CORS相關響應頭，告訴瀏覽器它可以安全地進行實際的POST請求。
	 * */
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 取消 CORS 跨來源資源共享，因為要給前端 React 使用
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		// 回應狀態
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 取消 CORS 跨來源資源共享，因為要給前端 React 使用
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
		// 接收來自前端 React 的效能統計資料
		BufferedReader reader = req.getReader();
		String payload = reader.lines().collect(Collectors.joining(System.lineSeparator()));
		
		// 處理/印出 payload
		System.out.println("react payload: " + payload);
		
		// 回應狀態
		resp.setStatus(HttpServletResponse.SC_OK);
			
	}
	
}
