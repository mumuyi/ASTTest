package cn.nuaa.ai.ast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MyVisitor extends ASTVisitor {
	public boolean visit(MethodDeclaration node) {

		String method = "";
		if(node != null)
			method = node.toString();
		
		String annotation = "";
		if(node.getJavadoc() != null)
			annotation += node.getJavadoc().toString();
		
		String methodName = "";
		if(node.getName() != null)
			methodName += node.getName().toString();

		String methodBody = "";
		if(!method.equals("")&&!annotation.equals("")){
			methodBody += method.replace(annotation, "");
		}else if(!method.equals("")&&annotation.equals("")){
			methodBody += method;
		}

		// 获取方法体;
		//System.out.println(methodName+" method body:\n" + node.getBody());

		// 获取注释;
		// System.out.println("annotation:\n" + annotation);

		StoreToFile(methodName,methodBody.replaceAll("@Override", ""),annotation.replaceAll("\\*|\\/", ""));
		return true;
	}

	private static void StoreToFile(String methodName, String methodBody, String methodAnnotation) {
		if (methodName != null && !"".equals(methodName)) {
			if (methodBody != null && !"".equals(methodBody)) {
				try {
					writeFileContent("F:\\data\\github\\methodbody\\"+ASTTest.projectName.replaceAll(".jar.src", "")+"@"+ASTTest.className.replaceAll(".java", "")+"#"+methodName+".txt", methodBody);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (methodAnnotation != null && !"".equals(methodAnnotation)) {
				try {
					writeFileContent("F:\\data\\github\\methodannotation\\"+ASTTest.projectName+"@"+ASTTest.className+"#"+methodName+".txt", methodAnnotation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean writeFileContent(String filepath, String newstr) throws IOException {
		Boolean bool = false;

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			File file = new File(filepath);// 文件路径(包括文件名称)
			StringBuffer buffer = new StringBuffer();

			buffer.append(newstr);

			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buffer.toString().toCharArray());
			pw.flush();
			bool = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			// 不要忘记关闭
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return bool;
	}
}