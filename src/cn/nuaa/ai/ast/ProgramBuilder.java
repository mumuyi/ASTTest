package cn.nuaa.ai.ast;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ProgramBuilder {

	public ProgramBuilder() {
		build();
	}

	private void build() {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setSource("".toCharArray());

		CompilationUnit comp = (CompilationUnit) parser.createAST(null);
		comp.recordModifications();

		AST ast = comp.getAST();

		/*
		 * public class HelloWorld {
		 * 
		 * }
		 */
		TypeDeclaration classDec = ast.newTypeDeclaration();
		classDec.setInterface(false);// ����Ϊ�ǽӿ�

		SimpleName className = ast.newSimpleName("HelloWorld12121");
		Modifier classModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);

		// ������ڵ�
		classDec.setName(className);// ����
		classDec.modifiers().add(classModifier);// ��ɼ���

		// ����ڵ�����Ϊ���뵥Ԫ���ӽڵ�
		comp.types().add(classDec);

		/*
		 * public class HelloWorld { public HelloWorld(){
		 * 
		 * } }
		 */
		MethodDeclaration methodDec = ast.newMethodDeclaration();
		methodDec.setConstructor(true);// ����Ϊ���캯��

		SimpleName methodName = ast.newSimpleName("HelloWorld11111");
		Modifier methodModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
		Block methodBody = ast.newBlock();

		// ���÷����ڵ�
		methodDec.setName(methodName);// ������
		methodDec.modifiers().add(methodModifier);// �����ɼ���
		methodDec.setBody(methodBody); // ������

		// �������ڵ�����Ϊ��ڵ���ӽڵ�
		classDec.bodyDeclarations().add(methodDec);

		/*
		 * public class HelloWorld { public HelloWorld(){ System.out.println(
		 * "Hello World!"); } }
		 */
		MethodInvocation methodInv = ast.newMethodInvocation();

		SimpleName nameSystem = ast.newSimpleName("System");
		SimpleName nameOut = ast.newSimpleName("out");
		SimpleName namePrintln = ast.newSimpleName("println");

		// ���ӡ�System���͡�out��
		// System.out
		QualifiedName nameSystemOut = ast.newQualifiedName(nameSystem, nameOut);

		// ���ӡ�System.out���͡�println����MethodInvocation�ڵ�
		// System.out.println()
		methodInv.setExpression(nameSystemOut);
		methodInv.setName(namePrintln);

		// ��Hello World!��
		StringLiteral sHelloworld = ast.newStringLiteral();
		sHelloworld.setEscapedValue("\"Hello World!\"");

		// System.out.println(��Hello World!��)
		methodInv.arguments().add(sHelloworld);

		// ���������ýڵ�MethodInvocation����Ϊ���ʽ���ExpressionStatement���ӽڵ�
		// System.out.println(��Hello World!��);
		ExpressionStatement es = ast.newExpressionStatement(methodInv);

		// �����ʽ���ExpressionStatement����Ϊ�����ڵ�ĵ�
		methodBody.statements().add(es);

		//�������; ����1;
		VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
		vdf.setName(ast.newSimpleName("gridLayout"));
		VariableDeclarationStatement vds = ast.newVariableDeclarationStatement(vdf);
		vds.setType(ast.newSimpleType(ast.newSimpleName("GridLayout")));
		ClassInstanceCreation cc = ast.newClassInstanceCreation();
		cc.setType(ast.newSimpleType(ast.newSimpleName("GridLayout")));
		vdf.setInitializer(cc);
		methodBody.statements().add(vds);
		//�������; ����2;
		/*
		Assignment a = ast.newAssignment();
		a.setOperator(Assignment.Operator.ASSIGN);
		VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
		vdf.setName(ast.newSimpleName("gridLayout"));
		VariableDeclarationExpression vde = ast.newVariableDeclarationExpression(vdf);
		vde.setType(ast.newSimpleType(ast.newSimpleName("GridLayout")));
		a.setLeftHandSide(vde);
		ClassInstanceCreation cc = ast.newClassInstanceCreation();
		cc.setType(ast.newSimpleType(ast.newSimpleName("GridLayout")));   
		a.setRightHandSide(cc);
		methodBody.statements().add(ast.newExpressionStatement(a));
		*/
		
		//����ע��;
		Javadoc jc = ast.newJavadoc();
		TagElement tag = ast.newTagElement();
		TextElement te = ast.newTextElement();
		tag.fragments().add(te);
		te.setText("Comment of the Class");
		tag.setTagName("@author\n * ");
		jc.tags().add(tag);
		classDec.setJavadoc(jc);
		
		Javadoc jc1 = ast.newJavadoc();
		TagElement tag1 = ast.newTagElement();
		TextElement te1 = ast.newTextElement();
		tag1.fragments().add(te1);
		te1.setText("Comment of the Method");
		tag1.setTagName("@author\n * ");
		jc1.tags().add(tag1);
		methodDec.setJavadoc(jc1);
		
		System.out.println(comp.toString());
	}
}