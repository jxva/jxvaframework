
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
 *
 */
package org.jxva.tree;

import java.io.Serializable;

public class Node implements Serializable {

	private static final long serialVersionUID = 7035260195222626134L;
	private java.lang.Integer nodeId;
	private java.lang.Integer parentId;
	private java.lang.Integer rootId;
	private java.lang.Integer levelNum;
	private java.lang.String levelInfo;
	private java.lang.String nodeName;

	public java.lang.Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(java.lang.Integer nodeId) {
		this.nodeId = nodeId;
	}

	public java.lang.Integer getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.Integer parentId) {
		this.parentId = parentId;
	}

	public java.lang.Integer getRootId() {
		return rootId;
	}

	public void setRootId(java.lang.Integer rootId) {
		this.rootId = rootId;
	}

	public java.lang.Integer getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(java.lang.Integer levelNum) {
		this.levelNum = levelNum;
	}

	public java.lang.String getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(java.lang.String levelInfo) {
		this.levelInfo = levelInfo;
	}

	public java.lang.String getNodeName() {
		return nodeName;
	}

	public void setNodeName(java.lang.String nodeName) {
		this.nodeName = nodeName;
	}
	
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	public int hashCode() {
		return super.hashCode();
	}

	public String toString() {
		return super.toString();
	}
}
