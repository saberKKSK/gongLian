package com.gonglian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "分页数据")
public class PageDTO<T> {
    
    @Schema(description = "总记录数")
    private Long total;
    
    @Schema(description = "当前页数据")
    private List<T> records;
    
    @Schema(description = "当前页码")
    private Integer pageNum;
    
    @Schema(description = "每页大小")
    private Integer pageSize;
    
    @Schema(description = "总页数")
    private Integer pages;
} 