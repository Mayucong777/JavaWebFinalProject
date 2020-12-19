package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.fanyiHelper;
import com.google.gson.Gson;

@WebServlet(urlPatterns = "/fanyi.do")
public class fanyiController extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String query = request.getParameter("query");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		fanyiHelper fyh = new fanyiHelper(query, "zh-CHS", "en");

		String url = fyh.createUrl();
		String translation;
		try {
			translation = fyh.getFanyiFromUrl(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			translation = e.getMessage();
		}
		// Map存放放回的信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("translation", translation);
		
		String jsonStr = new Gson().toJson(map);
		// 字符流输出字符串
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String query = request.getParameter("query");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		
		fanyiHelper fyh = new fanyiHelper(query, from, to);

		String url = fyh.createUrl();
		String translation;
		try {
			translation = fyh.getFanyiFromUrl(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			translation = e.getMessage();
		}
		// Map存放放回的信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("translation", translation);
		
		String jsonStr = new Gson().toJson(map);
		// 字符流输出字符串
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}
