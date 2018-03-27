package com.ai.ast;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class DemoVisitor extends ASTVisitor {

	public boolean visit(FieldDeclaration node) {
		for (Object obj : node.fragments()) {
			VariableDeclarationFragment v = (VariableDeclarationFragment) obj;
			System.out.println("FieldDeclaration - Field name: " + v.getName());
		}

		return true;
	}

	public boolean visit(MethodInvocation node) {
		System.out.println("MethodInvocation - Invocation method name: "+node.getName());// 测试每个方法里所调用的方法
		System.out.println("MethodInvocation - Invocation method way: "+node.getExpression());// 输出调用方法的对象，例如commandline.createArgument().setValue("-root_dir"); 总共有三个调用commandline.createArgument()，commandline，null
		return true;
	}

	public boolean visit(MethodDeclaration node) {
		System.out.println("MethodDeclaration - Method name: " + node.getName());// 得到方法名
		System.out.println("MethodDeclaration - the character length of the method is:" + node.getLength());// 节点的长度，不过是以字符长度来计算的，不是以行数来计//算的
		System.out.println("MethodDeclaration - Parameter list of Method:\t" + node.parameters());// 得到方法的参数列表
		System.out.println("MethodDeclaration - Return Value of Method:\t" + node.getReturnType2());// 得到方法的返回值
		Block b = node.getBody();
		
		
		System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		System.out.println(b.statements());
		Statement s = (Statement)b.statements().get(0);
		System.out.println(s.toString());
		System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		
		
		
		
		return true;
	}

	public boolean visit(TypeDeclaration node) {
		System.out.println("TypeDeclaration - Class name: " + node.getName());
		return true;
	}

	public boolean visit(BodyDeclaration node) {
		System.out.println("Body:\t" + node.getFlags());
		return true;
	}
}