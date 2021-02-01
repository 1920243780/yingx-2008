package com.baizhi.wxh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto implements Serializable {
    private Integer page;
    private Integer total;
    private Integer records;
    private List<User> rows;
}
