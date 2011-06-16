package study.rbac;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JServlet extends HttpServlet{

	private static final long serialVersionUID = -7312090877991867291L;

	public void service(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.print("这是一个Servlet例子!");
	}
}
