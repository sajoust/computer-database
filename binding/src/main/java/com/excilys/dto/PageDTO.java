package com.excilys.dto;

public class PageDTO {

	private String search="";
	private String order="";
	private int pageToDisplay=1;
	private int computerPerPage=10;
	
		
	
	public PageDTO(String search, String order, int pageToDisplay, int computerPerPage) {
		super();
		this.search = search;
		this.order = order;
		this.pageToDisplay = pageToDisplay;
		this.computerPerPage = computerPerPage;
	}
	public PageDTO() {
		
	}
	
	
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getPageToDisplay() {
		return pageToDisplay;
	}
	public void setPageToDisplay(int pageToDisplay) {
		this.pageToDisplay = pageToDisplay;
	}
	public int getComputerPerPage() {
		return computerPerPage;
	}
	public void setComputerPerPage(int computerPerPage) {
		this.computerPerPage = computerPerPage;
	}
	@Override
	public String toString() {
		return "PageDTO [search=" + search + ", order=" + order + ", pageToDisplay=" + pageToDisplay
				+ ", computerPerPage=" + computerPerPage + "]";
	}
	
	
	
	
}
