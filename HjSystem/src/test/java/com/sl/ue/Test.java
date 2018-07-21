package com.sl.ue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sl.ue.util.http.Result;

public class Test {

	public static void main(String[] args){
		double x=0;
		for(int i=1; i<=20; i++){
			x=x+((double)1/i);
			System.out.println(x);
		}
	}
}
