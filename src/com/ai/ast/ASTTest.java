package com.ai.ast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ASTTest {
	public static void main(String[] args){
        CompilationUnit comp = getCompilationUnit("F:\\Java\\ASTTest\\src\\com\\ai\\ast\\Program.java");  
        DemoVisitor visitor = new DemoVisitor();  
        comp.accept(visitor);
        //获取import数据;
        //System.out.println(comp.imports());
        //返回了整个类,暂时不知道干嘛的;
        //System.out.println(comp.types());
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
}
