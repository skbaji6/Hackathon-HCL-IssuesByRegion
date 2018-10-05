package com.example.hackathon;

public class Test {
	
	public static void main(String[] args){
		String test="120340300";
		System.out.println(Integer.valueOf(test.substring(0,2)));
		System.out.println(Integer.valueOf(test.substring(test.length()-1)));
		System.out.println(Integer.valueOf(test.substring(test.length()-1)) >0 && Integer.valueOf(test.substring(0,2))%Integer.valueOf(test.substring(test.length()-1)) == 0);
	}

}
