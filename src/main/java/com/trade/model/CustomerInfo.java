package com.trade.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInfo {

    private String customerId;
    private AccountInfo accountInfo;
}
