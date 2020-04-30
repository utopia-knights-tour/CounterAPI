package com.smoothstack.utopia.counter.model;

import java.util.List;

public class PageDetails<T> {
	
	private List<T> pageContent;
	
	private Long totalRecords;
	
	public PageDetails(List<T> pageContent, Long totalRecords) {
		this.pageContent = pageContent;
		this.totalRecords = totalRecords;
	}

	public List<T> getPageContent() {
		return pageContent;
	}

	public void setPageContent(List<T> pageContent) {
		this.pageContent = pageContent;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
