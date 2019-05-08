package cn.nuaa.ai.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class DemoVisitor2 extends ASTVisitor {
	
	public static Logger log = Logger.getLogger(DemoVisitor2.class.getClass());
	
	public boolean visit(FieldDeclaration node) {
		for (Object obj : node.fragments()) {
			VariableDeclarationFragment v = (VariableDeclarationFragment) obj;
			System.out.println("FieldDeclaration - Field name: " + v.getName());
		}

		return true;
	}

	public boolean visit(MethodInvocation node) {
		// System.out.println("MethodInvocation - Invocation method name:
		// "+node.getName());// 测试每个方法里所调用的方法
		// System.out.println("MethodInvocation - Invocation method way:
		// "+node.getExpression());//
		// 输出调用方法的对象，例如commandline.createArgument().setValue("-root_dir");
		// 总共有三个调用commandline.createArgument()，commandline，null
		// System.out.println("######################################");
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration node) {
		// System.out.println("MethodDeclaration - Method name: " +
		// node.getName());// 得到方法名
		// System.out.println("MethodDeclaration - the character length of the
		// method is:" + node.getLength());// 节点的长度，不过是以字符长度来计算的，不是以行数来计//算的
		// System.out.println("MethodDeclaration - Parameter list of Method:\t"
		// + node.parameters());// 得到方法的参数列表
		// System.out.println("MethodDeclaration - Return Value of Method:\t" +
		// node.getReturnType2());// 得到方法的返回值

		//System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		Block b = node.getBody();
		parseBlock(b);

		// 获取注释;
		// System.out.println("annotation: "+node.getJavadoc());

		//System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");

		return true;
	}

	//解析block;
	private void parseBlock(Block b){
		// System.out.println(b.statements());
		List<Statement> list = b.statements();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClass().getSimpleName().equals("IfStatement")) {
				//System.out.print(list.get(i));
				IfStatement ifs = (IfStatement) list.get(i);
				parseIfStatement(ifs);

				//System.out.println("###################################");
			} else if (list.get(i).getClass().getSimpleName().equals("WhileStatement")) {
				//System.out.print(list.get(i));
				WhileStatement ifs = (WhileStatement) list.get(i);
				parseWhileStatement(ifs);
				
				
			
				//System.out.println("###################################");
			} else if (list.get(i).getClass().getSimpleName().equals("ForStatement")) {
				//System.out.print(list.get(i));
				ForStatement ifs = (ForStatement) list.get(i);
				parseForStatement(ifs);
				
				//System.out.println("###################################");
			} else if (list.get(i).getClass().getSimpleName().equals("VariableDeclarationStatement")) {
				//System.out.print(list.get(i));
				//System.out.println("it a VariableDeclarationStatement");
				VariableDeclarationStatement ifs = (VariableDeclarationStatement) list.get(i);
				//System.out.println("get Type: " + ifs.getType());
				VariableDeclarationFragment vdf = (VariableDeclarationFragment) ifs.fragments().get(0);
				//System.out.println("get variable name: " + vdf.getName());
				//System.out.println("get variable value: " + vdf.getInitializer());

				if(ASTTest.myTypes.containsKey(ifs.getType().toString())){
					ASTTest.myTypes.replace(ifs.getType().toString(), ASTTest.myTypes.get(ifs.getType().toString()) + 1);
				}else{
					ASTTest.myTypes.put(ifs.getType().toString(), 1);
				}
				
				if(ASTTest.myTokens.containsKey(vdf.getName().toString())){
					ASTTest.myTokens.replace(vdf.getName().toString(), ASTTest.myTokens.get(vdf.getName().toString()) + 1);
				}else{
					ASTTest.myTokens.put(vdf.getName().toString(), 1);
				}
				
				
				if (null != vdf.getInitializer() && vdf.getInitializer().getClass().getSimpleName().equals("MethodInvocation")) {
					parseMethodInvocation((MethodInvocation) vdf.getInitializer());
				}
				ASTTest.myStatements.add("06");
				//System.out.println("###################################");
			} else if (list.get(i).getClass().getSimpleName().equals("ExpressionStatement")) {
				//System.out.print(list.get(i));
				//System.out.println("it a ExpressionStatement");
				ExpressionStatement ifs = (ExpressionStatement) list.get(i);
				Expression ex = ifs.getExpression();
				if (ex.getClass().getSimpleName().equals("Assignment")) {
					Assignment as = (Assignment) ex;
					//System.out.println("get LeftHandSide: " + as.getLeftHandSide());
					//System.out.println("get RightHandSide: " + as.getRightHandSide());
					//System.out.println("get Operator: " + as.getOperator());
					
					if(as.getLeftHandSide().getClass().getSimpleName().equals("MethodInvocation")){
						parseMethodInvocation((MethodInvocation)as.getLeftHandSide());
					}else if(as.getLeftHandSide().getClass().getSimpleName().equals("SimpleName")){
						//System.out.println("SimpleName " + as.getLeftHandSide());
						if(ASTTest.myTokens.containsKey(as.getLeftHandSide().toString())){
							ASTTest.myTokens.replace(as.getLeftHandSide().toString(), ASTTest.myTokens.get(as.getLeftHandSide().toString()) + 1);
						}else{
							ASTTest.myTokens.put(as.getLeftHandSide().toString(), 1);
						}
						
					}else if(as.getLeftHandSide().getClass().getSimpleName().equals("NumberLiteral")){
						//System.out.println("NumberLiteral " + as.getLeftHandSide());
					}
					
					if(as.getRightHandSide().getClass().getSimpleName().equals("MethodInvocation")){
						parseMethodInvocation((MethodInvocation)as.getRightHandSide());
					}else if(as.getRightHandSide().getClass().getSimpleName().equals("SimpleName")){
				
						if(ASTTest.myTokens.containsKey(as.getRightHandSide().toString())){
							ASTTest.myTokens.replace(as.getRightHandSide().toString(), ASTTest.myTokens.get(as.getRightHandSide().toString()) + 1);
						}else{
							ASTTest.myTokens.put(as.getRightHandSide().toString(), 1);
						}
						
						//System.out.println("SimpleName " + as.getRightHandSide());
					}else if(as.getRightHandSide().getClass().getSimpleName().equals("NumberLiteral")){
						//System.out.println("NumberLiteral " + as.getRightHandSide());
					}
					ASTTest.myStatements.add("05");

				} else if (ex.getClass().getSimpleName().equals("MethodInvocation")) {
					MethodInvocation mi = (MethodInvocation) ex;
					//System.out.println("get Name: " + mi.getName());
					//System.out.println("get Arguments: " + mi.arguments());
					//System.out.println("get Expression: " + mi.getExpression());
					//System.out.println("get Operators: " + mi.properties());
					parseMethodInvocation(mi);

					ASTTest.myStatements.add("04");
				}
				//System.out.println("###################################");
			} else if(list.get(i).getClass().getSimpleName().equals("TryStatement")){
				//System.out.print(list.get(i));
				//System.out.println("it a TryStatement");
				TryStatement ifs = (TryStatement) list.get(i);
				//System.out.println(ifs.getBody().getClass().getSimpleName());
				//System.out.println(ifs.getFinally().getClass().getSimpleName());
				
				if(null != ifs.getBody() && !ifs.getBody().equals("") && ifs.getBody().getClass().getSimpleName().equals("Block")){
					parseBlock((Block)ifs.getBody());
				}
				
				if(null != ifs.getFinally() && !ifs.getFinally().equals("") && ifs.getFinally().getClass().getSimpleName().equals("Block")){
					parseBlock((Block)ifs.getFinally());
				}
				
				//System.out.println("###################################");
			}else {
				//System.out.print(list.get(i).getClass().getSimpleName());
				//System.out.println("\n###################################");
			}
		}
	}
	
	//解析方法调用语句;
	private void parseMethodInvocation(MethodInvocation mi) {
		//System.out.println("MethodInvocation - Invocation method way: " + mi.getExpression());
		//System.out.println("MethodInvocation - Invocation method name: " + mi.getName());
		//System.out.println("MethodInvocation - Invocation arguments: " + mi.arguments());
		
		String[] temp = null;
		if(null != mi.getExpression()){
			temp = mi.getExpression().toString().split("\\.");
			//System.out.println(mi.getExpression().toString());
			//System.out.println(temp.length + " 111111111111111111111111111111111122222222222222222222222222222");
			for(String s: temp){
				String s2 = s.replaceAll("\\([^\\)]+\\)", "").replaceAll("\\\"[^\\)]+\\\"", "").replaceAll("\\\"|\\(|\\)| ", "");
				s = s2;
				if(ASTTest.myMethods.containsKey(s)){
					ASTTest.myMethods.replace(s, ASTTest.myMethods.get(s) + 1);
				}else{
					ASTTest.myMethods.put(s, 1);
				}
				//System.out.println(s);
			}
		}
		/*
		for(Object s : mi.arguments()){
			temp = s.toString().split("\\.");
			for(String s1: temp){
				String s2 = s1.replaceAll("\\([^\\)]+\\)", "").replaceAll("\\\"[^\\)]+\\\"", "").replaceAll("\\\"|\\(|\\)| ", "");
				s1 = s2;
				if(myMethods.containsKey(s1)){
					myMethods.replace(s1, myMethods.get(s1) + 1);
				}else{
					myMethods.put(s1, 1);
				}
				System.out.println(s1);
			}
		}
		*/
		String s1 = mi.getName().toString();
		String s2 = s1.replaceAll("\\([^\\)]+\\)", "").replaceAll("\\\"[^\\)]+\\\"", "").replaceAll("\\\"|\\(|\\)| ", "");
		s1 = s2;
		if(ASTTest.myMethods.containsKey(s1)){
			ASTTest.myMethods.replace(s1, ASTTest.myMethods.get(s1) + 1);
		}else{
			ASTTest.myMethods.put(s1, 1);
		}
	}

	//解析条件判断语句;
	private void parseConditionalStatement(Expression ex) {
		if(null!= ex){
			String simpleName = ex.getClass().getSimpleName();
			if (simpleName.equals("PrefixExpression")) {
				//前缀表达式; 
				PrefixExpression pfe = (PrefixExpression) ex;
				parseConditionalStatement(pfe.getOperand());
			} else if (simpleName.equals("InfixExpression")) {
				//中缀表达式; 
				InfixExpression ife = (InfixExpression)ex;
				
				//System.out.println("left: " + ife.getLeftOperand() + " " + ife.getLeftOperand().getClass().getSimpleName());
				//System.out.println("right: " + ife.getRightOperand() + " " + ife.getRightOperand().getClass().getSimpleName());
				
				parseConditionalStatement(ife.getLeftOperand());
				parseConditionalStatement(ife.getRightOperand());
				
			} else if (simpleName.equals("PostfixExpression")) {
				//后缀表达式; 
			}else if(simpleName.equals("MethodInvocation")){
				//直接方法调用; 直接解析方法;
				parseMethodInvocation((MethodInvocation)ex);
			}else if(simpleName.equals("ParenthesizedExpression")){
				//括号内表达式; 去掉括号后递归;
				parseConditionalStatement(parseParenthesizedExpression(ex));
			}else if(simpleName.equals("SimpleName")){
				//System.out.println("SimpleName " + ex);
				if(ASTTest.myTokens.containsKey(simpleName)){
					ASTTest.myTokens.replace(simpleName, ASTTest.myTokens.get(simpleName) + 1);
				}else{
					ASTTest.myTokens.put(simpleName, 1);
				}
			}else if(simpleName.equals("NumberLiteral")){
				//System.out.println("NumberLiteral " + ex);
			}else if(simpleName.equals("ExpressionStatement")){
				log.error("ExpressionStatement in Conditional Statement, try to fix");
			}
		}
	}
	
	//解析括号内表达式; 实际就是去掉括号;
	private Expression parseParenthesizedExpression(Expression ex){
		ParenthesizedExpression pe = (ParenthesizedExpression)ex;
		//System.out.println(pe.getExpression());
		return pe.getExpression();
	}
	
	//解析if语句block;
	private void parseIfStatement(IfStatement ifs){
		//System.out.println("it a IF statement:");
		//System.out.println("get expression: " + ifs.getExpression());
		//System.out.println("get then statement: " + ifs.getThenStatement());
		//System.out.println("get else statement: " + ifs.getElseStatement());
		//解析条件判断语句;
		parseConditionalStatement(ifs.getExpression());
		
		ASTTest.myStatements.add("01");
		
		//解析if和else block中的语句,直接递归即可;
		//System.out.println("1111111111111111111111111 " + ifs.getThenStatement().getClass().getSimpleName());
		//System.out.println("1111111111111111111111111 " + ifs.getElseStatement().getClass().getSimpleName());
		
		if(ifs.getThenStatement().getClass().getSimpleName().equals("Block")){
			parseBlock((Block)ifs.getThenStatement());
		}else if(ifs.getThenStatement().getClass().getSimpleName().equals("IfStatement")){
			parseIfStatement((IfStatement)ifs.getThenStatement());
		}
		
		if(null != ifs.getElseStatement() && !ifs.getElseStatement().equals("") && ifs.getElseStatement().getClass().getSimpleName().equals("Block")){
			parseBlock((Block)ifs.getElseStatement());
		}else if(null != ifs.getElseStatement() && !ifs.getElseStatement().equals("") && ifs.getElseStatement().getClass().getSimpleName().equals("IfStatement")){
			parseIfStatement((IfStatement)ifs.getElseStatement());
		}
	}
	
	//解析while语句block;
	private void parseWhileStatement(WhileStatement ifs){
		//System.out.println("it a WHILE statement");
		//System.out.println("get expression: " + ifs.getExpression());
		//System.out.println("get body: " + ifs.getBody());
		
		//解析条件判断语句;
		parseConditionalStatement(ifs.getExpression());
		
		ASTTest.myStatements.add("02");
		
		//System.out.println("222222222222222222222222222222222222 " + ifs.getBody().getClass().getSimpleName());
		if(null != ifs.getBody() && !ifs.getBody().equals("") && ifs.getBody().getClass().getSimpleName().equals("Block")){
			parseBlock((Block)ifs.getBody());
		}
		
	}
	
	private void parseForStatement(ForStatement ifs){
		//System.out.println("it a FOR statement");
		//System.out.println("get expression: " + ifs.getExpression());
		//System.out.println("get body: " + ifs.getBody());
	
		//解析条件判断语句;
		parseConditionalStatement(ifs.getExpression());
		
		ASTTest.myStatements.add("03");
		
		//System.out.println("33333333333333333333333333333333333 " + ifs.getBody().getClass().getSimpleName());
		if(null != ifs.getBody() && !ifs.getBody().equals("") && ifs.getBody().getClass().getSimpleName().equals("Block")){
			parseBlock((Block)ifs.getBody());
		}
	}
	
	public boolean visit(TypeDeclaration node) {
		//System.out.println("TypeDeclaration - Class name: " + node.getName());
		return true;
	}

	public boolean visit(BodyDeclaration node) {
		//System.out.println("Body:\t" + node.getFlags());
		return true;
	}
}