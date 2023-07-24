package com.trade.vo;

import com.trade.model.TradeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TradeVo {
    private String tradeId;
    private TradeInfo tradeInfo;
}
