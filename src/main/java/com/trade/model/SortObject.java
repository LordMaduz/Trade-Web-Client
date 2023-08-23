package com.trade.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SortObject {
    private String key;
    private String sortType;
}
