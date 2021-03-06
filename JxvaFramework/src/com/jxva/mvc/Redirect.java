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
 * Action子类的业务转向(重定向)注解<br>
 * 相当于:response.sendRedirect("page.jsp");<br>
 * <b>Usage:</b>
 * <pre>
 *   @Redirect("/article/listArticle.jv") 
 *   public void delArticle(){
 *   	new ArticleDao().delArticle(getInt("articleId"));
 *   }
 * </pre>   
 *   控制中心执行delArticle()方法后<br>
 *   将会直接转向(重定向)到/article/listArticle.jv   
 * @see com.jxva.mvc.Forward
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 10:16:06 by Jxva
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Redirect {
	String value();
}