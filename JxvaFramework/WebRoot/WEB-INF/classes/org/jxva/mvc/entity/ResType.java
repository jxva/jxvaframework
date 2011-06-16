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
package org.jxva.mvc.entity;

/**
 * mvc资源类型模型类,留后备用
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:21:00 by Jxva
 */
public class ResType {
	
	private String restype;
	private Integer restypeid;
	private Boolean isable;
	
	public String getRestype() {
		return restype;
	}
	public void setRestype(String restype) {
		this.restype = restype;
	}
	public Integer getRestypeid() {
		return restypeid;
	}
	public void setRestypeid(Integer restypeid) {
		this.restypeid = restypeid;
	}
	public Boolean getIsable() {
		return isable;
	}
	public void setIsable(Boolean isable) {
		this.isable = isable;
	}
}
