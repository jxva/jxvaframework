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

public class AuthFilter implements Filter{
	
    protected FilterConfig filterConfig;

    public void init(FilterConfig config) throws ServletException{
       this.filterConfig=config;
    }

    public void destroy(){
        this.filterConfig=null;
    }
      
    
    
    public void doFilter(ServletRequest req,ServletResponse response,FilterChain chain) throws
        IOException,ServletException{
    	
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

	    HttpServletRequest request = (HttpServletRequest)req;
	    
	 	UserInfo userinfo=new UserInfo(request);
	 	
	 	String url 			= request.getServletPath();
	 	url=url.substring(1,url.length());
	 	
	 	if(Resource.isableUrl()){
	 		if(!Permission.hasPermission(url,userinfo)){
	 			PrintWriter out=response.getWriter();
				out.print("<font color=red>权限不够!</font>");
			    out.close();
				out.flush();
				return;
	 		}
	 	}
		
	 	try{
             chain.doFilter(request, response);
        } catch(Throwable t){  
             t.printStackTrace();
        }
     }
}