package com.jxva.dao;


public class Pager extends PageBean{
	
	public Pager(int pageno) {
		super(pageno);
	}
	
	public Pager(int pageno,int pagesize) {
		super(pageno,pagesize);
	}
	
	public String getPageInfo(){
		StringBuilder sb=new StringBuilder();
		sb.append("总共有 <b>").append(totalCount).append("</b> 记录  共分 <b>");
		sb.append(totalPage).append("</b> 页   每页显示 <b>");
		sb.append(pagesize).append("</b> 条  当前为第 <b><font color=#FF0000>");
		sb.append(pageno).append("</font></b> 页 ");
		return sb.toString();
	}
	public String getPageNum(String url){
		StringBuilder sb=new StringBuilder();
		
		int p=0,i=0;
		
		if((pageno-1)%10==0){
			p=(pageno-1)/10;
		}else{
			p=(((pageno-1)-(pageno-1)%10)/10);
		}

		sb.append("<div class=\"pager\">");
		sb.append("<a href=\""+url+"1\">首页</a>");
				
		if (p*10 > 0){
			sb.append("<a href=\""+url+(p*10)+"\">上页</a>");
		}else{
			sb.append("<a href=\""+url+((pageno-1)==0?1:(pageno-1))+"\">上页</a>");
		}
		
		for (i=p*10+1;i<p*10+11;i++){
			if (totalCount==0){
				sb.append("<a> 还没有任何数据  ! </a>");
				break;
			}
			if (i==pageno){
				sb.append(" <a href=\""+url+i+"\" class=\"active\">"+i+"</a>");
			}else{
				sb.append(" <a href=\""+url+i+"\">"+i+"</a>");
			}
			if (i==totalPage) break;
		}

		if (i<totalPage){
			sb.append(" <a href=\""+url+i+"\">下页</a>");
		}else{
			sb.append(" <a href=\""+url+((pageno+1)>totalPage?totalPage:(pageno+1))+"\">下页</a>");
		}
		
		sb.append("<a href=\""+url+totalPage+"\">尾页</a>");
		sb.append("</div>");
		return sb.toString();
	}
}
