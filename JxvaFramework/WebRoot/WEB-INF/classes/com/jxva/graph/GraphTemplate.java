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
package com.jxva.graph;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-06-04 16:58:24 by Jxva
 */
public class GraphTemplate {
	
	private Graph graph;
	
	public GraphTemplate(Graph graph){
		this.graph=graph;
	}
	
	public <T> T execute(GraphCallback<T> callback)throws GraphException{
		try{
			return callback.doInGraph(graph);
		}catch(IOException e){
			throw new GraphException(e);
		}
	}

	public Boolean execute(GraphCallback<BufferedImage> callback,File dest)throws GraphException{
		try{
			return graph.ioWrite(callback.doInGraph(graph),dest,1.0F);
		}catch(IOException e){
			throw new GraphException(e);
		}
	}
		
	public Boolean demo(final File src,final File dest){
		return execute(new GraphCallback<BufferedImage>(){
			public BufferedImage doInGraph(Graph graph) throws GraphException, IOException {
				return graph.cutToSquare(ImageIO.read(src),100D);
			}
		},dest);
	}
}
