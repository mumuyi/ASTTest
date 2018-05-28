package cn.nuaa.ai.classFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MatchFiles {
	public static void main(String[] args) {
		//ChangeFilesName();
		
		Top100000("F:\\data\\jarFiles\\methodbody\\","F:\\data\\jarFiles\\instruction\\");
	}

	public static void Top100000(String methodFilePath,String instructionFilePath){
		File instruction = new File(instructionFilePath);
		File methodbody = new File(methodFilePath);
		File[] instructionFiles = instruction.listFiles();
		File[] methodFiles = methodbody.listFiles();

		System.out.println(instructionFiles.length + "   " + methodFiles.length);

		int counter = 0;
		for (File insfile : instructionFiles) {
			String fileName = insfile.getName();
			for (File mefile : methodFiles) {
				if (mefile.getName().equals(fileName)) {
					if(counter > 10000){
						copyFile(insfile,"F:\\data\\jarFiles\\Top100000\\instruction\\");
						copyFile(mefile,"F:\\data\\jarFiles\\Top100000\\methodbody\\");
						System.out.println(fileName);
					}
					counter ++;
					break;
				}
			}
			//break;
			if(counter > 100000)
				break;
		}
		System.out.println(counter);
	}
	
	private static void copyFile(File file, String path) {
		// 将指定文件复制到指定目录
		try {
			Files.copy(Paths.get(file.toURI()),
					new FileOutputStream(path + file.getName()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
