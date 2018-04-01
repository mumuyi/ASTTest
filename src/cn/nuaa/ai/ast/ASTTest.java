package cn.nuaa.ai.ast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	/** linkedListʵ�� **/
	private static LinkedList<File> queueFiles = new LinkedList<File>();

	public static void main(String[] args) {
		
		
	}

	private static void SingleFileTest(){
		projectName = "TestClass";
		className = "Program";
		CompilationUnit comp = getCompilationUnit("C:\\Users\\ai\\Desktop\\code\\IndexFiles.java");
		MyVisitor visitor = new MyVisitor();
		// DemoVisitor visitor = new DemoVisitor();
		comp.accept(visitor);
		// ��ȡimport����;
		// System.out.println(comp.imports());
		// ������������,��ʱ��֪�������;
		// System.out.println(comp.types());
	}
	
	private static void SingleProjectTest(){
		scanFilesWithNoRecursion("E:\\Ѹ������\\github data dump\\Activiti-develop");
		
		for(int i=0;i<scanFiles.size();i++){
			File file = scanFiles.get(i);
			System.out.println(file);
			className = file.getName();
			projectName = "Activiti-develop";
			CompilationUnit comp = getCompilationUnit(file.getAbsolutePath());
			MyVisitor visitor = new MyVisitor();
			comp.accept(visitor);
		}
	}
	
	private static void Compelte(){
		
		List<String> list = scanProjects("E:\\Ѹ������\\github data dump");
		
		for(int i = 0;i<list.size();i++){
			//System.out.println(list.get(i));
			scanFilesWithNoRecursion("E:\\Ѹ������\\github data dump\\" + list.get(i));
			projectName = list.get(i);
			for(int j = 0;j < scanFiles.size();j++){
				File file = scanFiles.get(j);
				System.out.println(file);
				className = file.getName();
				CompilationUnit comp = getCompilationUnit(file.getAbsolutePath());
				MyVisitor visitor = new MyVisitor();
				comp.accept(visitor);
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

	private static ArrayList<File> scanFilesWithNoRecursion(String folderPath) {
		File directory = new File(folderPath);
		if (!directory.isDirectory()) {
			System.out.println(directory + " is not a directory");
		} else {
			// ���Ƚ���һ��Ŀ¼ɨ��һ��
			File[] files = directory.listFiles();
			// ����ɨ�����ļ����飬������ļ��У�������뵽linkedList���Ժ���
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					queueFiles.add(files[i]);
				} else {
					// ��ʱ���ļ�������scanFiles��
					if(files[i].getName().endsWith(".java"))
						scanFiles.add(files[i]);
				}
			}

			// ���linkedList�ǿձ���linkedList
			while (!queueFiles.isEmpty()) {
				// �Ƴ�linkedList�еĵ�һ��
				File headDirectory = queueFiles.removeFirst();
				File[] currentFiles = headDirectory.listFiles();
				for (int j = 0; j < currentFiles.length; j++) {
					if (currentFiles[j].isDirectory()) {
						// �����Ȼ���ļ��У��������linkedList��
						queueFiles.add(currentFiles[j]);
					} else {
						if(currentFiles[j].getName().endsWith(".java"))
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
}
