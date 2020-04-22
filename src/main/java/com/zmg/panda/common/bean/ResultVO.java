package com.zmg.panda.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Andy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    private T data;
    private Integer code;
    private String message;

    public static <T> ResultVO success(T data) {
        return new ResultVO<T>(data, 200, "request success!");
    }

    public static <T> ResultVO success() {
        return new ResultVO<T>(null, 200, "request success!");
    }
}
