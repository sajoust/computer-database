package com.excilys.formation.CDB.service;

import java.util.Scanner;

public class CLI_UI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		showMenu();
		Scanner sc = new Scanner(System.in);
		switch (sc.nextInt()) {
		case 1:
			EntriesViewer.viewEntries("computer");
			break;
			
		case 2:
			EntriesViewer.viewEntries("company");
			break;
			
		case 3:
			System.out.println("computer's ID ?");			
			EntriesViewer.viewEntry(sc.nextLong());
			break;

		default:
			System.out.println("wrong entry or unimplemented");
		}
		sc.close();
		
	}
	
	
	public static void showMenu() {
		System.out.println("What now ?");
		System.out.println();
		System.out.println("1 - List computers");
		System.out.println("2 - List companies");
		System.out.println("3 - Show computer details");
		System.out.println("4 - Create a computer");
		
		
	}

}
