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
package com.jxva.tag;

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Base Tag Class
 * 
 * @author  The Jxva Framework Foundation
 * @since 2006-12-22
 */

public abstract class BaseTag extends BodyTagSupport {

	private static final long serialVersionUID = -5111312632799090511L;
	protected String name;
	protected String value;
	// Define Base Attribute
	protected String alt;
	protected String title;
	protected String id;
	// Define Style
	protected String cssClass;
	protected String cssStyle;
	// Define Base States
	protected boolean readonly;
	protected boolean disabled;
	protected boolean checked;
	protected boolean selected;
	// Define JavaScript Mouse Events
	protected String onclick;
	protected String ondblclick;
	protected String onmouseover;
	protected String onmouseout;
	protected String onmousemove;
	protected String onmousedown;
	protected String onmouseup;
	// Define JavaScript Keyboard Events
	protected String onkeydown;
	protected String onkeyup;
	protected String onkeypress;
	// Define JavaScript Text Events
	protected String onselect;
	protected String onchange;
	// Define JavaScript Focus Events and States
	protected String onblur;
	protected String onfocus;

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}
	
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}
	
	public void release() {
		super.release();
		name = null;
		value = null;
		alt = null;
		title = null;
		id = null;
		cssStyle = null;
		cssClass = null;
		readonly = false;
		disabled = false;
		checked = false;
		selected = false;
		onclick = null;
		ondblclick = null;
		onmouseover = null;
		onmouseout = null;
		onmousemove = null;
		onmousedown = null;
		onmouseup = null;
		onkeydown = null;
		onkeyup = null;
		onkeypress = null;
		onselect = null;
		onchange = null;
		onblur = null;
		onfocus = null;
	}
}