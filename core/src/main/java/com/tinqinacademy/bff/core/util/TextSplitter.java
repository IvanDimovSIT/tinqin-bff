package com.tinqinacademy.bff.core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TextSplitter {
    public List<String> splitIntoWords(String text) {
        return Arrays.stream(text.split("[\\s\\p{Punct}]+"))
                .filter(word -> !word.isBlank())
                .toList();
    }

    public Set<String> splitIntoUniqueWords(String text) {
        return new HashSet<>(splitIntoWords(text));
    }
}
