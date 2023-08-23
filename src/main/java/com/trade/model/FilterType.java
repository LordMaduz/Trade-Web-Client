package com.trade.model;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum FilterType {
    TEXT("TEXT"), NUMERIC("NUMERIC"), BOOLEAN("BOOLEAN"), DATE("DATE"), UNDEFINED("UNDEFINED");

    private final String type;

    private static Map<String,Object> map = new HashMap<>();

    FilterType(String type){
        this.type = type;
    }

    static {
        for(FilterType filterType: FilterType.values()){
            map.put(filterType.type, filterType);
        }
    }

    public String getType(){
        return this.type;
    }

    public static FilterType value(String type){
        if(ObjectUtils.isEmpty(type))
            return UNDEFINED;
        type = StringUtils.upperCase(type);
        return map.get(type) != null ? (FilterType) map.get(type): UNDEFINED;
    }
}
