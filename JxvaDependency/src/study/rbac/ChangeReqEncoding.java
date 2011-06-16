package study.rbac;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class ChangeReqEncoding implements Filter{
	
	private String encoding = null;
	protected FilterConfig filterConfig = null;
	private boolean ignore = true;

	public void destroy(){
		this.encoding = null;
		this.filterConfig = null;
	}
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding =filterConfig.getInitParameter("encoding");
		String value =filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}
	
	public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain)
	throws IOException, ServletException {
		if (ignore || (req.getCharacterEncoding() == null)){
			String encoding = this.encoding;
			if (encoding != null){
				req.setCharacterEncoding(encoding);
			}
		}	
		try{
		    HttpServletRequest request = (HttpServletRequest)req;
		    
			if(request.getRequestURI().endsWith(".jpg")){//不处理的后缀
			    chain.doFilter(req,resp);
			}
	
		 	UserInfo userinfo	= new UserInfo(request);
		 	String url 			= request.getServletPath();
		 	url=url.substring(1,url.length());
	
		 	if(Resource.isableUrl()){
		 		if(!Permission.hasPermission(url,userinfo)){
			    	resp.setCharacterEncoding("UTF-8");
					resp.setContentType("text/html;charset=UTF-8");
					
		 			PrintWriter out=resp.getWriter();
					out.print("<font color=red>权限不够!</font>");
				    out.close();
					out.flush();
					return;
		 		}
		 	}
		    chain.doFilter(req,resp);
		}finally {
			System.out.println("filter colse...");
		}
	}
}