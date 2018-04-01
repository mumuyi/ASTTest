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
		classDec.setInterface(false);// 设置为非接口

		SimpleName className = ast.newSimpleName("HelloWorld12121");
		Modifier classModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);

		// 设置类节点
		classDec.setName(className);// 类名
		classDec.modifiers().add(classModifier);// 类可见性

		// 将类节点连接为编译单元的子节点
		comp.types().add(classDec);

		/*
		 * public class HelloWorld { public HelloWorld(){
		 * 
		 * } }
		 */
		MethodDeclaration methodDec = ast.newMethodDeclaration();
		methodDec.setConstructor(true);// 设置为构造函数

		SimpleName methodName = ast.newSimpleName("HelloWorld11111");
		Modifier methodModifier = ast.newModifier(Modifier.ModifierKeyword.PUBLIC_KEYWORD);
		Block methodBody = ast.newBlock();

		// 设置方法节点
		methodDec.setName(methodName);// 方法名
		methodDec.modifiers().add(methodModifier);// 方法可见性
		methodDec.setBody(methodBody); // 方法体

		// 将方法节点连接为类节点的子节点
		classDec.bodyDeclarations().add(methodDec);

		/*
		 * public class HelloWorld { public HelloWorld(){ System.out.println(
		 * "Hello World!"); } }
		 */
		MethodInvocation methodInv = ast.newMethodInvocation();

		SimpleName nameSystem = ast.newSimpleName("System");
		SimpleName nameOut = ast.newSimpleName("out");
		SimpleName namePrintln = ast.newSimpleName("println");

		// 连接‘System’和‘out’
		// System.out
		QualifiedName nameSystemOut = ast.newQualifiedName(nameSystem, nameOut);

		// 连接‘System.out’和‘println’到MethodInvocation节点
		// System.out.println()
		methodInv.setExpression(nameSystemOut);
		methodInv.setName(namePrintln);

		// ”Hello World!”
		StringLiteral sHelloworld = ast.newStringLiteral();
		sHelloworld.setEscapedValue("\"Hello World!\"");

		// System.out.println(“Hello World!”)
		methodInv.arguments().add(sHelloworld);

		// 将方法调用节点MethodInvocation连接为表达式语句ExpressionStatement的子节点
		// System.out.println(“Hello World!”);
		ExpressionStatement es = ast.newExpressionStatement(methodInv);

		// 将表达式语句ExpressionStatement连接为方法节点的点
		methodBody.statements().add(es);

		//创建语句; 方法1;
		VariableDeclarationFragment vdf = ast.newVariableDeclarationFragment();
		vdf.setName(ast.newSimpleName("gridLayout"));
		VariableDeclarationStatement vds = ast.newVariableDeclarationStatement(vdf);
		vds.setType(ast.newSimpleType(ast.newSimpleName("GridLayout")));
		ClassInstanceCreation cc = ast.newClassInstanceCreation();
		cc.setType(ast.newSimpleType(ast.newSimpleName("GridLayout")));
		vdf.setInitializer(cc);
		methodBody.statements().add(vds);
		//创建语句; 方法2;
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
		
		//测试注释;
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