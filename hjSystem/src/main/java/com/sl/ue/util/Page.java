package com.sl.ue.util;

import java.util.List;

/**
 * 说明 [分页工具类]
 *
 * @author L_晓天    @2018年3月30日
 */
public class Page<T> {

	/**
	 * 说明 [每页大小]
	 */
	private Integer pageSize;
	/**
	 * 说明 [第几页]
	 */
	private Integer pageNum;
	/**
	 * 说明 [总数]
	 */
	private Integer count;
	/**
	 * 说明 [分页数据]
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
