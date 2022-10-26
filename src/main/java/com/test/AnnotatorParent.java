package com.test;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "post_id", "annotators", "rationales", "post_tokens" })
@Data
@NoArgsConstructor
public class AnnotatorParent {
    private String post_id;
    private List<AnnotatorChild> annotators;
    private List<List<Integer>> rationales;
    private List<String> post_tokens;
}
