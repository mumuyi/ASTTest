package cn.nuaa.ai.ast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTTest {
	public static String className = "";
	public static String projectName = "";

	private static ArrayList<File> scanFiles = new ArrayList<File>();

	/** linkedList实现 **/
	private static LinkedList<File> queueFiles = new LinkedList<File>();

	public static void main(String[] args) {
		
		//Compelte();
		//SingleProjectTest();
		
		getClassFile();
	}

	// 单个文件解析;
	private static void SingleFileTest() {
		projectName = "TestClass";
		className = "Program";
		CompilationUnit comp = getCompilationUnit("C:\\Users\\ai\\Desktop\\code\\IndexFiles.java");
		MyVisitor visitor = new MyVisitor();
		// DemoVisitor visitor = new DemoVisitor();
		comp.accept(visitor);
		// 获取import数据;
		// System.out.println(comp.imports());
		// 返回了整个类,暂时不知道干嘛的;
		// System.out.println(comp.types());
	}

	// 单个工程解析;
	private static void SingleProjectTest() {
		scanFilesWithNoRecursion("F:\\data\\jarFiles\\decompiled jars\\aopalliance-1.0-sources.jar.src", ".java");

		for (int i = 0; i < scanFiles.size(); i++) {
			File file = scanFiles.get(i);
			System.out.println(file);
			className = file.getName();
			projectName = "Activiti-develop";
			CompilationUnit comp = getCompilationUnit(file.getAbsolutePath());
			MyVisitor visitor = new MyVisitor();
			comp.accept(visitor);
		}
	}

	// AST特征抽取完整;
	private static void Compelte() {

		List<String> list = scanProjects("F:\\data\\jarFiles\\decompileJars");

		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			if(i > 23 && i < 54){
				scanFilesWithNoRecursion("F:\\data\\jarFiles\\decompileJars\\" + list.get(i), ".java");
				projectName = list.get(i);
				for (int j = 0; j < scanFiles.size(); j++) {
					File file = scanFiles.get(j);
					System.out.println(file);
					className = file.getName();
					CompilationUnit comp = getCompilationUnit(file.getAbsolutePath());
					MyVisitor visitor = new MyVisitor();
					comp.accept(visitor);
				}
			}
			scanFiles.clear();
		}
	}

	// 抽取.class文件;
	private static void getClassFile() {

		List<String> list = scanProjects("F:\\data\\jarFiles\\decompressionJars");
		for (int i = 0; i < list.size(); i++) {
			if(i > 13){
				projectName = list.get(i);
				scanFilesWithNoRecursion("F:\\data\\jarFiles\\decompressionJars\\" + list.get(i), ".class");
				for (int j = 0; j < scanFiles.size(); j++) {
					File file = scanFiles.get(j);
					//System.out.println(file);
					copyFile(file, projectName);
				}
				System.out.println(projectName + " num: " + scanFiles.size());
				scanFiles.clear();
			}
		}
	}

	public static CompilationUnit getCompilationUnit(String javaFilePath) {
		byte[] input = null;
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(javaFilePath));
			input = new byte[bufferedInputStream.available()];
			bufferedInputStream.read(input);
			bufferedInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ASTParser astParser = ASTParser.newParser(AST.JLS3);
		astParser.setSource(new String(input).toCharArray());
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);

		CompilationUnit result = (CompilationUnit) (astParser.createAST(null));

		return result;
	}

	private static ArrayList<File> scanFilesWithNoRecursion(String folderPath, String endstr) {
		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			System.out.println(directory + " is not a directory");
		} else {
			// 首先将第一层目录扫描一遍
			File[] files = directory.listFiles();
			// 遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					queueFiles.add(files[i]);
				} else {
					// 暂时将文件名放入scanFiles中
					if (files[i].getName().endsWith(endstr))
						scanFiles.add(files[i]);
				}
			}

			// 如果linkedList非空遍历linkedList
			while (!queueFiles.isEmpty()) {
				// 移出linkedList中的第一个
				File headDirectory = queueFiles.removeFirst();
				File[] currentFiles = headDirectory.listFiles();
				for (int j = 0; j < currentFiles.length; j++) {
					if (currentFiles[j].isDirectory()) {
						// 如果仍然是文件夹，将其放入linkedList中
						queueFiles.add(currentFiles[j]);
					} else {
						if (currentFiles[j].getName().endsWith(endstr))
							scanFiles.add(currentFiles[j]);
					}
				}
			}
		}
		return scanFiles;
	}

	private static List<String> scanProjects(String folderPath) {
		List<String> list = new ArrayList<String>();
		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			System.out.println(directory + " is not a directory");
		} else {
			File[] files = directory.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					list.add(files[i].getName());
				}
			}
		}
		return list;
	}

	private static void copyFile(File file, String projectName) {
		// 将指定文件复制到指定目录
		try {
			Files.copy(Paths.get(file.toURI()),
					new FileOutputStream("F:\\data\\jarFiles\\classfile\\" + projectName + "@" + file.getName()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
