package com.ai.ast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Program {
	private int field1;
	private int field2,a,s,d;
	static void main(String[] args){
		int i=10;
		int j=0;
		j=i;
		if(i==10){
			i+=10111;
		}
		System.out.println(j);
	}
}
