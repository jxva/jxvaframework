
/*
 * Copyright @ 2006-2008 by The Jxva Framework Foundation
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
package demo.graph;

import java.io.File;


public class MagickImpl{
		
	public void resize(File src,File dest,int width,int height,String format,int scale)throws Exception{
		switch(scale){
			case 0:
				runCmd("cmd /c convert -quality 100 -resize "+width+"x"+height+" \""+src.getAbsoluteFile()+"\" \""+dest.getAbsoluteFile()+"\"");
				break;
			case 1:
				runCmd("cmd /c convert -quality 100 -resize "+width+"x \""+src.getAbsoluteFile()+"\" \""+dest.getAbsoluteFile()+"\"");
				break;
			case 2:
				runCmd("cmd /c convert -quality 100 -resize x"+height+"  \""+src.getAbsoluteFile()+"\"  \""+dest.getAbsoluteFile()+"\"");
				break;
			case 3:
				runCmd("cmd /c convert -quality 100 -resize "+width+"x"+height+" \""+src.getAbsoluteFile()+"\"  \""+dest.getAbsoluteFile()+"\"");
				break;
		}
	}

	/**
	 * 执行系统命令
	 * @param cmd
	 */
	private void runCmd(String cmd){
		try {
			Runtime.getRuntime().exec(cmd);
			/**
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);	
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
