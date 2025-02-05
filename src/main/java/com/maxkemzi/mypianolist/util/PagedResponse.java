package com.maxkemzi.mypianolist.util;

import java.util.List;

import org.springframework.data.domain.Page;

public class PagedResponse<T> {
	public List<T> content;
	public int page;
	public int limit;
	public long totalCount;
	public int totalPages;
	public boolean hasMore;

	public PagedResponse(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber();
		this.limit = page.getSize();
		this.totalCount = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.hasMore = page.hasNext();
	}
}
