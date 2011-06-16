/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jxva.dao;
/**
 * 
 * @author The Jxva Framework Foundation
 * @since 1.0
 * @version 2009-01-03 21:09:38 by Jxva
 */
public abstract class PageBean {
	
	public static final int DEFAULT_PAGESIZE = 20;
	protected int pagesize=20;
	protected int pageno;
	protected long totalCount;
	protected long totalPage;
	
	public PageBean(int pageno) {
		//Assert.isTrue(pageno>0,"pageno must be greater than zero");
		this.pageno=pageno<1?1:pageno;
	}
	
	public PageBean(int pageno,int pagesize) {
		//Assert.isTrue(pageno>0,"pageno must be greater than zero");
		//Assert.isTrue(pagesize>0,"pagesize must be greater than zero");
		this(pageno);
		this.pagesize=pagesize<1?DEFAULT_PAGESIZE:pagesize;
	}
	
	public int getPageno() {
		return pageno;
	}
		
	public int getPagesize() {
		return pagesize;
	}

	public long getTotalPage() {			
		return totalPage;
	}
	
	public int getNextPage() {
		if (hasNextPage()) {
			return pageno + 1;
		}
		return pageno;
	}
	
	public int getPrevPage(){
		if(hasPrevPage()){
			return pageno-1;
		}
		return pageno;
	}

	public boolean hasPrevPage(){
		return pageno>1;
	}

	public boolean hasNextPage() {
		return pageno < totalPage;
	}

	
	public long getTotalCount(){
		return totalCount;
	}
	
	void setTotalCount(long totalCount){
		if(totalCount%this.pagesize==0) {
			this.totalPage=totalCount/this.pagesize;
		}else{
			this.totalPage=(totalCount-totalCount%this.pagesize)/this.pagesize+1;
		}
		this.totalCount=totalCount;
	}
	
	public int getStartIndex(){
		return pagesize*(pageno-1);
	}
	
	public int getEndIndex(){
		return pageno*pagesize;
	}
	
	public abstract String getPageInfo();
	public abstract String getPageNum(String url);
}
