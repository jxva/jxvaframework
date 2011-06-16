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
package org.jxva.graph;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jxva.graph.Graph;
import com.jxva.graph.GraphException;
import com.jxva.graph.GraphFactory;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-06-03 12:37:59 by Jxva
 */
public class GraphCut {

	private static final Graph GRAPH=GraphFactory.createSmoothGraph();
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws GraphException 
	 */
	public static void main(String[] args) throws GraphException, IOException {
		File src=new File("C:/zte/2.jpg");
		File dest=new File("C:/zte/dest2.jpg");
		GRAPH.ioWrite(GRAPH.cut(ImageIO.read(src),300D,400D),dest,1.0F);

	}

}
