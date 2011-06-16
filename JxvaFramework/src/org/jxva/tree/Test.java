
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


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Tree tree=new NodeDao(new Leaf());
			
		tree.append(tree.getNodeById(4),"fdsfsa");
		
		List<Node> list=tree.getTree();
		for(Node n:list){
			System.out.println(n.getLevelInfo());
		}
	}
}
