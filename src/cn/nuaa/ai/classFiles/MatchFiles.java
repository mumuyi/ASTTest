package cn.nuaa.ai.classFiles;

import java.io.File;

public class MatchFiles {
	public static void main(String[] args){
		File instruction = new File("F:\\data\\instruction\\");
		File methodbody = new File("F:\\data\\methodbody\\");
		File[] instructionFiles = instruction.listFiles();
		File[] methodFiles = methodbody.listFiles();
		
		System.out.println(instructionFiles.length + "   " + methodFiles.length);
		
		int flag = 0;
		int counter = 0;
		for(File insfile : instructionFiles){
			String fileName = insfile.getName();
			for(File mefile : methodFiles){
				if(mefile.getName().replaceAll(".java", "").equals(fileName)){
					flag = 1;
					break;
				}
			}
			
			if(flag == 0){
				counter ++;
				System.out.println(fileName);
				insfile.delete();
			}
			
			flag = 0;
		}
		System.out.println(counter);
	}
}
