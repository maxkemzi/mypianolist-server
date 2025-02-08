package com.maxkemzi.mypianolist.util;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageResponseDTO<T> {
	private List<T> content;
	private Integer page;
	private Integer limit;
	private Long totalCount;
	private Integer totalPages;
	public Boolean hasMore;

	protected PageResponseDTO() {
	}

	public PageResponseDTO(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber();
		this.limit = page.getSize();
		this.totalCount = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.hasMore = page.hasNext();
	}

	public List<T> getContent() {
		return content;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getLimit() {
		return limit;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Boolean getHasMore() {
		return hasMore;
	}
}
