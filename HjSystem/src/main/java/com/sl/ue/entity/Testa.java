package com.sl.ue.entity;

import java.lang.reflect.Field;

import com.sl.ue.entity.sys.SysUser;
import com.sl.ue.util.anno.Id;

public class Testa {

	public static void main(String[] args){
		Field[] fields = SysUser.class.getDeclaredFields();
		for(Field field : fields){
			
			if(field.isAnnotationPresent(Id.class)){
				System.out.println(field.getName()+ " : 1");
			}else{
				System.out.println(field.getName()+ " : 2");
			}
		}
	}
}
