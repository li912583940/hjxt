package com.sl.ue.entity;

import com.sl.ue.util.Table;

@Table("sl_user")
public class User {

	private Integer id;
	private String abgv;
	private String uhActiDes;
	private Double yhFGA;
	
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
