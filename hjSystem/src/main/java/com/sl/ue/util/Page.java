package com.sl.ue.util;

import java.util.List;

/**
 * ˵�� [��ҳ������]
 *
 * @author L_����    @2018��3��30��
 */
public class Page<T> {

	/**
	 * ˵�� [ÿҳ��С]
	 */
	private Integer pageSize;
	/**
	 * ˵�� [�ڼ�ҳ]
	 */
	private Integer pageNum;
	/**
	 * ˵�� [����]
	 */
	private Integer count;
	/**
	 * ˵�� [��ҳ����]
	 */
	private List<T> data;
	
	
	
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
