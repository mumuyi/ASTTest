package cn.nuaa.ai.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class DemoVisitor2 extends ASTVisitor {

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
		System.out.println("######################################");
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration node) {
		//System.out.println("MethodDeclaration - Method name: " + node.getName());// �õ�������
		//System.out.println("MethodDeclaration - the character length of the method is:" + node.getLength());// �ڵ�ĳ��ȣ����������ַ�����������ģ���������������//���
		//System.out.println("MethodDeclaration - Parameter list of Method:\t" + node.parameters());// �õ������Ĳ����б�
		//System.out.println("MethodDeclaration - Return Value of Method:\t" + node.getReturnType2());// �õ������ķ���ֵ
		
		
		System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		Block b = node.getBody();
		//System.out.println(b.statements());
		List<Statement> list = b.statements();
		for(int i = 0;i < list.size();i++){
			/*
			if(list.get(i).getClass().getSimpleName().equals("IfStatement")){
				System.out.print(list.get(i));
				System.out.println("it a IF statement:");
				IfStatement ifs = (IfStatement) list.get(i);
				System.out.println("get expression: "+ifs.getExpression());
				System.out.println("get then statement: "+ifs.getThenStatement());
				System.out.println("get else statement: "+ifs.getElseStatement());
				System.out.println("###################################");
			}else if(list.get(i).getClass().getSimpleName().equals("WhileStatement")){
				System.out.print(list.get(i));
				System.out.println("it a WHILE statement");
				WhileStatement ifs = (WhileStatement) list.get(i);
				System.out.println("get expression: "+ifs.getExpression());
				System.out.println("get body: "+ifs.getBody());
				System.out.println("###################################");
			}else if(list.get(i).getClass().getSimpleName().equals("ForStatement")){
				System.out.print(list.get(i));
				System.out.println("it a FOR statement");
				ForStatement ifs = (ForStatement) list.get(i);
				System.out.println("get expression: "+ifs.getExpression());
				System.out.println("get body: "+ifs.getBody());
				System.out.println("###################################");
			}else if(list.get(i).getClass().getSimpleName().equals("VariableDeclarationStatement")){
				System.out.print(list.get(i));
				System.out.println("it a VariableDeclarationStatement");
				VariableDeclarationStatement ifs = (VariableDeclarationStatement) list.get(i);
				System.out.println("get Type: "+ ifs.getType());
				VariableDeclarationFragment vdf = (VariableDeclarationFragment) ifs.fragments().get(0);
				System.out.println("get variable name: "+ vdf.getName());
				System.out.println("get variable value: "+ vdf.getInitializer());
				System.out.println("###################################");
			}else if(list.get(i).getClass().getSimpleName().equals("ExpressionStatement")){
				System.out.print(list.get(i));
				System.out.println("it a ExpressionStatement");
				ExpressionStatement ifs = (ExpressionStatement) list.get(i);
				Expression ex = ifs.getExpression();
				if(ex.getClass().getSimpleName().equals("Assignment")){
					Assignment as = (Assignment)ex;
					System.out.println("get LeftHandSide: "+ as.getLeftHandSide());
					System.out.println("get RightHandSide: "+ as.getRightHandSide());
					System.out.println("get Operator: "+ as.getOperator());
				}else if(ex.getClass().getSimpleName().equals("MethodInvocation")){
					MethodInvocation mi = (MethodInvocation)ex;
					System.out.println("get Name: "+ mi.getName());
					System.out.println("get Arguments: "+ mi.arguments());
					System.out.println("get Expression: "+ mi.getExpression());
					System.out.println("get Operators: "+ mi.properties());
				}
				System.out.println("###################################");
			}else{
				System.out.print(list.get(i).getClass().getSimpleName());
				System.out.println("\n###################################");
			}
			*/
			
			if(list.get(i).getClass().getSimpleName().equals("VariableDeclarationStatement")){
				System.out.print(list.get(i));
				System.out.println("it a VariableDeclarationStatement");
				VariableDeclarationStatement ifs = (VariableDeclarationStatement) list.get(i);
				System.out.println("get Type: "+ ifs.getType());
				VariableDeclarationFragment vdf = (VariableDeclarationFragment) ifs.fragments().get(0);
				System.out.println("get variable name: "+ vdf.getName());
				System.out.println("get variable value: "+ vdf.getInitializer());
				System.out.println("###################################");
			}
		}
		
		//��ȡע��;
		//System.out.println("annotation: "+node.getJavadoc());
		
		
		
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