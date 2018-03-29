package com.sl.ue.entity;

import com.sl.ue.util.Table;

@Table("user")
public class User {

	private Integer id;
	private String abgv;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAbgv() {
		return abgv;
	}
	public void setAbgv(String abgv) {
		this.abgv = abgv;
	}
	
	
}
