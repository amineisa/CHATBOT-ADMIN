package com.chatbot.admin.utils;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortingUtil {
	
	public static Sort ascSorting(String property) {
		return 	new Sort(Direction.ASC,property);
	}
	
	public static Sort desSorting(String property) {
		return new Sort(Direction.DESC,property);
	}
	

}
