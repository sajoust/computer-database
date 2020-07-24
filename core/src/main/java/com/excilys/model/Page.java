package com.excilys.model;

public class Page {
	
	
	private String search="";
	private String order="";
	private int pageToDisplay=1;
	private int computerPerPage=10;
	

	
	
	
	public Page(String search, String order,int pageToDisplay, int computerPerPage) {
		super();
		this.computerPerPage = computerPerPage;
		this.pageToDisplay = pageToDisplay;
		this.search = search;
		this.order = order;
	}
	
	public Page() {
		
	}



	public int getComputerPerPage() {
		return computerPerPage;
	}



	public void setComputerPerPage(int nbLines) {
		this.computerPerPage = nbLines;
	}



	public int getPageToDisplay() {
		return pageToDisplay;
	}



	public void setPageToDisplay(int pageToDisplay) {
		this.pageToDisplay = pageToDisplay;
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
	
	
	
	
	
	
}
