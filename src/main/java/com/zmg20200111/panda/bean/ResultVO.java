package com.zmg20200111.panda.bean;

import lombok.Data;

@Data
public class ResultVO<T> {
    private T data;
    private Integer code;
    private String message;
}
