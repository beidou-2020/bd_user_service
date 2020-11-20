package com.bd.entitys.dto;


import lombok.Data;
import lombok.ToString;
import javax.validation.constraints.Min;

@Data
@ToString
public class PageParam {
	
	/**
	 * 当前页数的序号
	 */
	@Min(value = 1, message = "当前页数不能为0或者为负")
	private Integer currentPageNumber = 1;
	
	/**
	 * 每页显示的数据大小
	 */
	@Min(value = 5, message = "页面显示的数据不能小于5条")
	private Integer pageSize = 20;
}
