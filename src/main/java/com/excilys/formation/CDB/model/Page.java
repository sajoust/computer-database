package com.excilys.formation.CDB.model;

public class Page {

	private int nbLines=10;
	private int pageToDisplay=1;
	private String filter="";
	private String order="";
	
	
	
	public Page(int nbLines, int pageToDisplay, String filter, String order) {
		super();
		this.nbLines = nbLines;
		this.pageToDisplay = pageToDisplay;
		this.filter = filter;
		this.order = order;
	}
	
	public Page() {
		
	}



	public int getNbLines() {
		return nbLines;
	}



	public void setNbLines(int nbLines) {
		this.nbLines = nbLines;
	}



	public int getPageToDisplay() {
		return pageToDisplay;
	}



	public void setPageToDisplay(int pageToDisplay) {
		this.pageToDisplay = pageToDisplay;
	}



	public String getFilter() {
		return filter;
	}



	public void setFilter(String filter) {
		this.filter = filter;
	}



	public String getOrder() {
		return order;
	}



	public void setOrder(String order) {
		this.order = order;
	}
	
	
	
	
	
	
}
