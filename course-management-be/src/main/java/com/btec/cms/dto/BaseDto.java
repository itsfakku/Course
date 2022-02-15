package com.btec.cms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {
    private Long id;
    private Date createdDate;
    private Date modifiedDate;
}
