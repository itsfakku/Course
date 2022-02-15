package com.btec.cms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryDto extends BaseDto{
    private String name;
    private String image;
    private String description;
}
