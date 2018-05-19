package com.sl.ue.util;

import java.util.List;


/**
 * 说明 [分页数据]
 * @author L_晓天    @2018年4月3日
 */
public class Page<T> {

	
	/**
	 * 说明 [每页的数据]
	 * @author L_晓天    @2018年4月3日
	 */
	private Integer pageSize;
	/**
	 * 说明 [页数]
	 * @author L_晓天    @2018年4月3日
	 */
	private Integer pageNum;
	/**
	 * 说明 [总数]
	 * @author L_晓天    @2018年4月3日
	 */
	private Integer count;

	/**
	 * 说明 [数据]
	 * @author L_晓天    @2018年4月3日
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
