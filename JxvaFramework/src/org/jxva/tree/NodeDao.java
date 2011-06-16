
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

import java.util.ArrayList;
import java.util.List;

import com.jxva.dao.BaseDao;

/**
 * @author  The Jxva Framework Foundation
 * @version v1.0  at 2008-05-25
 */
public class NodeDao extends BaseDao implements Tree{
	
	private Node node;
	
	public NodeDao(Node node){
		this.node=node;
	}
	
	public NodeDao(){
		this.node=new Node();
	}
	
	public Node appendRoot(String nodeName) {
		try {
			Object obj = dao.findUnique("from Node n where n.parentid=-1");
			if (obj != null) {
				throw new TreeException("已存在根结点");
			}else{
				node.setNodeId(0);
				node.setParentId(-1);
				node.setRootId(0);
				node.setLevelNum(1);
				node.setLevelInfo(",0");
				node.setNodeName(nodeName);
				dao.save(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return node;
	}
	
	public Node append(Node parentNode,String nodeName){
		try{
			if(parentNode==null||parentNode.getNodeId()==null){
				appendRoot(nodeName);
			}else{
				Object obj=dao.findUnique("from Node n where n.parentid="+parentNode.getNodeId()+" and n.nodename='"+nodeName.trim()+"'");
				if(obj!=null){
					throw new TreeException("同层不能出现重名节点");
				}
				//node.setNodeId(Long.valueOf(dao.getAutoId(node.getClass(),"nodeid")).intValue());
				node.setParentId(parentNode.getNodeId());
				node.setRootId(parentNode.getRootId());
				node.setLevelNum(parentNode.getLevelNum()+1);
				node.setLevelInfo(parentNode.getLevelInfo()+","+node.getNodeId());
			}
			node.setNodeName(nodeName);
			dao.save(node);
		}catch(Exception e){
			e.printStackTrace();
		}
		return node;
	}

	public Node getNodeById(Integer nodeid) {
		try {
			return (Node)dao.findUnique("from Node n where n.nodeid="+nodeid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public List<Node> getSubTree(Integer nodeid) {
		return getTree("levelinfo like '"+getNodeById(nodeid).getLevelInfo()+"%' order by levelinfo");
	}



	public List<Node> getTree() {
		return getTree("order by levelinfo");
	}


	public void modify(Node node) {
		try {
			dao.update(node);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void remove(Node node) {
		dao.delete("from Node n where n.levelinfo like ?","'"+node.getLevelInfo()+"%'");
	}
	
	@SuppressWarnings("unchecked")
	private List<Node> getTree(String where){
		List<Node> list=new ArrayList<Node>();
		List<Node> objlist;
		try {
			objlist = (List<Node>)dao.find("from Node "+where);
			for(Object obj:objlist){
				list.add((Node)obj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
