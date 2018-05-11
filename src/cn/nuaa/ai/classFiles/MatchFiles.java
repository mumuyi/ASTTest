package cn.nuaa.ai.classFiles;

import java.io.File;

public class MatchFiles {
	public static void main(String[] args) {
		ChangeFilesName();
	}

	public static void MatchFilesByName() {
		File instruction = new File("F:\\data\\instruction\\");
		File methodbody = new File("F:\\data\\methodbody\\");
		File[] instructionFiles = instruction.listFiles();
		File[] methodFiles = methodbody.listFiles();

		System.out.println(instructionFiles.length + "   " + methodFiles.length);

		int flag = 0;
		int counter = 0;
		for (File insfile : instructionFiles) {
			String fileName = insfile.getName();
			for (File mefile : methodFiles) {
				if (mefile.getName().equals(fileName)) {
					flag = 1;
					break;
				}
			}

			if (flag == 0) {
				counter++;
				System.out.println(fileName);
				insfile.delete();
			}

			flag = 0;
		}
		System.out.println(counter);
	}
	
	public static void ChangeFilesName(){
		File methodbody = new File("F:\\data\\methodbody\\");
		File[] methodFiles = methodbody.listFiles();
		for(File file : methodFiles){
			String newFileName = file.getAbsolutePath().replaceAll(".java", "");
			System.out.println(newFileName);
			file.renameTo(new File(newFileName));
			//break;
		}
	}
}
