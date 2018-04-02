package cn.nuaa.ai.ast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClassFileAnalysis {

	public static void main(String[] args) throws IOException {
		Runtime run = Runtime.getRuntime();
		Process process = run.exec("javap -verbose .\\data\\Activiti-develop@AbstractActivitiTestCase");
		InputStream in = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String info = "";
		while ((info = reader.readLine()) != null) {
			System.out.println(info);
		}

		System.out.println("!!!!!!!!!!!!!!!!!!!!!");
	}
}
