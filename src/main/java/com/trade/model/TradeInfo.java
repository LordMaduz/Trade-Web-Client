package com.trade.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeInfo {

    private String tradeId;
    private CustomerInfo customerInfo;
}
