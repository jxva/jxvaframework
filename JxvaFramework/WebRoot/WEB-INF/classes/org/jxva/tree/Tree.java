
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

import java.util.List;

/**
 * @author  The Jxva Framework Foundation
 * @version v1.0  at 2008-05-25
 */
public interface Tree {
	
	/**
	 * 新增根节点
	 * @param rootNode 根结点
	 * @return 根节点
	 */
	public Node appendRoot(String nodeName);
	
	/**
	 * 新增节点
	 * @param parentNode 父结点
	 * @param newNode 新结点
	 * @return 新增的节点 
	 */
	public Node append(Node parentNode,String nodeName);

	/**
	 * 通过nodeid获取节点
	 * @param nodeid
	 * @return 获取的节点
	 */
	public Node getNodeById(Integer nodeid);
	

	/**
	 * 通过nodeid获取子树
	 * @param nodeid
	 * @return 子树
	 */
	public List<Node> getSubTree(Integer nodeid);
	

	/**
	 * 获取整个树
	 * @return 整个树
	 */
	public List<Node> getTree();
	
	/**
	 * 修改结点
	 * @param node
	 */
	public void modify(Node node);
	
	/**
	 * 删除结点
	 * @param node
	 */
	public void remove(Node node);
	
}

