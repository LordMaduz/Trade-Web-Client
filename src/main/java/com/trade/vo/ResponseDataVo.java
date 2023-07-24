package com.trade.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Data
public class ResponseDataVo {

    private boolean allow;
    private String reason;
    private Map<String,Object> headers;

    private Integer status_code;
}
