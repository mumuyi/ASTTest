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
		System.out.println("MethodInvocation - Invocation method name: "+node.getName());// ����ÿ�������������õķ���
		System.out.println("MethodInvocation - Invocation method way: "+node.getExpression());// ������÷����Ķ�������commandline.createArgument().setValue("-root_dir"); �ܹ�����������commandline.createArgument()��commandline��null
		return true;
	}

	public boolean visit(MethodDeclaration node) {
		System.out.println("MethodDeclaration - Method name: " + node.getName());// �õ�������
		System.out.println("MethodDeclaration - the character length of the method is:" + node.getLength());// �ڵ�ĳ��ȣ����������ַ�����������ģ���������������//���
		System.out.println("MethodDeclaration - Parameter list of Method:\t" + node.parameters());// �õ������Ĳ����б�
		System.out.println("MethodDeclaration - Return Value of Method:\t" + node.getReturnType2());// �õ������ķ���ֵ
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