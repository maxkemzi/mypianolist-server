package com.maxkemzi.mypianolist.util;

import java.util.List;

public class PagedResponse<T> {
	public List<T> content;
	public int page;
	public int limit;
	public long totalCount;
	public int totalPages;
	public boolean hasMore;

	public PagedResponse(List<T> content, int page, int limit, long totalCount, int totalPages, boolean hasMore) {
		this.content = content;
		this.page = page;
		this.limit = limit;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.hasMore = hasMore;
	}
}
