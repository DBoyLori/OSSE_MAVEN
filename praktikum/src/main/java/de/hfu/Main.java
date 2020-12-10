package de.hfu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String zeichenkette = reader.readLine();
		
		for(int i = 0; i < zeichenkette.length(); i++) {
			System.out.print((char)(zeichenkette.charAt(i)-32));
		}

	}

}
