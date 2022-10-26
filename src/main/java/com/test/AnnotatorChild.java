package com.test;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonPropertyOrder({ "label", "annotator_id", "target" })
@Data
@NoArgsConstructor
public class AnnotatorChild {
    private String annotator_id;
    private String label;
    private List<String> target;
}