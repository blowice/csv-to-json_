package com.test;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class Result {
    LinkedHashMap<String, AnnotatorParent> value;

    public Result() {
        value = new LinkedHashMap<>();
    }
}
