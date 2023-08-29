package com.trade.util;

import com.trade.model.CustomerInfo;
import com.trade.model.TradeInfo;
import com.trade.model.entity.Trade;

import java.time.LocalDateTime;
import java.util.List;

public final class TestUtil {

    public static Trade trade(final String id) {
        final CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setCustomerInfoId("customer-1");

        final TradeInfo tradeInfo = new TradeInfo();
        tradeInfo.setTradeInfoId("trade-info-1");
        tradeInfo.setCustomerInfo(customerInfo);

        final Trade trade = new Trade();
        trade.setTradeId(id);
        trade.setHeight(10);
        trade.setCreated(LocalDateTime.now());
        trade.setStatus(true);
        trade.setTradeInfo(tradeInfo);

        return trade;
    }

    public static List<Trade> tradeList() {
        Trade t1 = trade("trade-1");
        Trade t2 = trade("trade-2");
        Trade t3 = trade("trade-3");

        return List.of(t1, t2, t3);
    }


}
