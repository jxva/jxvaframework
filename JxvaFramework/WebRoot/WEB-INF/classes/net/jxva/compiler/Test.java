package net.jxva.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jxva.util.UtilException;

public class Test {

	//E:/eclipse/workspace/Platform/WebRoot/7000.txt
	public static void main(String[] args) {
		System.out.println("16天记住7000单词");
		read(new File("E:/eclipse/workspace/Platform/WebRoot/7000.txt"));
	}

	public static void read(File file)throws UtilException {
		InputStreamReader input=null;
		BufferedReader br =null;
		try {
			input = new InputStreamReader(new FileInputStream(file),"UTF-8");
			br = new BufferedReader(input);
			String line =null;
			int i=0;
			while ((line = br.readLine()) != null) {
				i++;
				if(i%3==0)continue;
				if((i+1)%3==0){
					System.out.println("   "+line);
				}else{
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			throw new UtilException(e);
		}finally{
			try {if(input!=null)input.close();} catch (IOException e) {}
			try {if(br!=null)br.close();} catch (IOException e) {}
		}
	}
}
