package com.github.dadchain.model.common;

import lombok.Data;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/4/26
 */
@Data
public class ResponseBean<T> {

    private Integer code;

    private String msg;

    private T result;

    public ResponseBean(Integer code, String msg, T result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public ResponseBean(){}


}
