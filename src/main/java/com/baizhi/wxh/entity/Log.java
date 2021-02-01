package com.baizhi.wxh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "yx_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {
    @Id
    private String id;

    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "option_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date optionTime;
    @Column(name = "method_name")
    private String methodName;
    @Column(name = "status")
    private String status;
}