package com.cosmos.back.model;

import com.cosmos.back.annotation.RedisCachedKeyParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class RedisCacheParameterMethodInfo {
    List<IndexInfo> indexInfoList = new LinkedList<>();

    public void addInfo(RedisCachedKeyParam keyParam, Integer index ) {
        indexInfoList.add(new IndexInfo(keyParam, index));
    }

    @Data
    @AllArgsConstructor
    public class IndexInfo {
        RedisCachedKeyParam annotation;
        Integer index;

    }
}