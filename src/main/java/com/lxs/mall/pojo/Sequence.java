package com.lxs.mall.pojo;

import lombok.Data;

@Data
public class Sequence {
    private String name;

    private Integer currentValue;

    private Integer step;

}
