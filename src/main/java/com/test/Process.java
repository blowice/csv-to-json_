package com.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Process {

    Random random = new Random();

    @Test
    void processSentence() throws IOException {
        Result result = new Result();
        AnnotatorParent annotatorParent = new AnnotatorParent();
        try (BufferedReader reader = new BufferedReader
                (new InputStreamReader(new FileInputStream("C:\\workspace\\csv-to-json\\src\\main\\resources\\input2.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank() && Character.isDigit(line.charAt(0)) && annotatorParent.getPost_id() != null) {
                    String randomText = randomText();
                    annotatorParent.setPost_id(randomText);
                    result.value.put(randomText, annotatorParent);
                    annotatorParent = new AnnotatorParent();
                }

                if (!line.isBlank()) {
                    processLine(line, annotatorParent);
                }
            }
            String randomText = randomText();
            annotatorParent.setPost_id(randomText);
            result.value.put(randomText, annotatorParent);
        }

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(result.getValue());
        System.out.println(json);
    }

    private String randomText() {
        StringBuilder sb = new StringBuilder();
        return sb.append("2022").append(random.nextInt(10000000)).append(random.nextInt(10000000)).append("_tweeter").toString();
    }

    private void processLine(String line, AnnotatorParent annotatorParent) {
        if (line != null) {
            if (line.startsWith("\uFEFF,Hatespeech,Target communities,,,,,,,,,,,,,,,")) {
                return; // header line, ignore
            }
            if (Character.isDigit(line.charAt(0))) {
                // sentence line
                annotatorParent.setPost_id(line.substring(0, line.indexOf(",")));
                annotatorParent.setPost_tokens(Arrays.asList(line.substring(4).split(",")));
                return;
            }

            // process annotator
            String[] tokens = line.split(",");
            AnnotatorChild annotator = new AnnotatorChild();
            annotator.setAnnotator_id(tokens[0]);
            annotator.setLabel(tokens[1]);
            annotator.setTarget(Arrays.asList(tokens[2]));
            List<Integer> rationales = new LinkedList<>();
            for (int i = 3; i < tokens.length; i++) {
                if (tokens[i].equals("")) {
                    System.out.println("!!!!!" + line);
                    continue;
                }
                rationales.add(Integer.valueOf(tokens[i]));
            }

            if (annotatorParent.getRationales() == null) {
                annotatorParent.setRationales(new LinkedList<>());
            }
            annotatorParent.getRationales().add(rationales);

            if (annotatorParent.getAnnotators() == null) {
                annotatorParent.setAnnotators(new LinkedList<>());
            }
            annotatorParent.getAnnotators().add(annotator);
        }

    }

}
