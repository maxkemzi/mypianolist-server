package com.maxkemzi.mypianolist.util;

public class PageRequestParams {
	private Integer page;
	private Integer limit;

	protected PageRequestParams() {
	}

	public PageRequestParams(Integer page, Integer limit) {
		this.page = page != null ? page : 0;
		this.limit = limit != null ? limit : 10;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getLimit() {
		return limit;
	}
}
