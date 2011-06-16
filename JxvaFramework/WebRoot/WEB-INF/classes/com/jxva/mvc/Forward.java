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
package com.jxva.mvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Action子类的业务转发注解<br>
 * 相当于:request.getRequestDispatcher("page.jsp").forward(request,response);<br>
 * <b>Usage:</b>
 * <pre>
 *   @Forward("/article/listArticle.jsp") 
 *   public void listArticle(){
 *   	request.setAttribute("list",new ArticleDao().getArticles());
 *   }
 *   
 * 控制中心执行listArticle()方法后
 * 将会直接转发到/article/listArticle.jsp
 * <pre>
 * @see com.jxva.mvc.Redirect
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:04:32 by Jxva
 */
//@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Forward {
	/**
	 * 页面文件,通常为JSP文件
	 * @return
	 */
	String value();
	
	/**
	 * <pre>
	 *  小于0: 表示永久缓存  <br>
	 *  等于0: 表示永远不缓存  <br>
	 *  大于0: 缓存的秒数 <br>
	 *  </pre>
	 * @return 缓存的秒数
	 */
	int cache() default 0;
}