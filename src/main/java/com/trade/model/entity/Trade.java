package com.trade.model.entity;

import com.trade.model.TradeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.repository.Collection;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document()
@Collection("Trade")
public class Trade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String tradeId;
    private TradeInfo tradeInfo;

    private Integer age;
    @CreatedDate
    private LocalDateTime created;
    private Boolean status;
}
