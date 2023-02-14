package com.greedy.section01.javaconfig;

import org.apache.ibatis.annotations.Select;

public interface Mapper {
		
	@Select("SELECT SYSDATE FROM DUAL")
	java.util.Date selectSysdate();
	
}
