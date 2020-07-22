package com.excilys.mapper;

import com.excilys.dto.PageDTO;
import com.excilys.model.Page;

public class PageMapper {

	public static Page dtoToPage(PageDTO pageDto) {
		
		return new Page(pageDto.getSearch(),pageDto.getOrder(),pageDto.getPageToDisplay(),pageDto.getComputerPerPage());
	}
}
