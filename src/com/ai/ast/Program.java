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
	private int field2, a, s, d;

	/**
	 * ÎÒÔÚÐ´×¢ÊÍ;
	 * */
	static void main(String[] args) {
		int i = 10;
		int j = 0;
		j = i;
		i+=j+100;
		if (i == 10) {
			i += 10111;
		} else if (i == 1222) {
			i += 20222;
		} else if (i == 1333) {
			i += 30333;
		}
		
		while(i<100){
			i+=1234555;
		}
		
		for(int k=0;k<111;k++){
			System.out.println("asdasdasdasdasdasd");
		}
		System.out.println(j);
	}
}
