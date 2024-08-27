package com.tinqinacademy.bff.core.util;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TextDifferenceFinder {
    public Set<String> findAdded(Set<String> oldText, Set<String> newText) {
        Set<String> copyNewText = new HashSet<>(newText);
        copyNewText.removeAll(oldText);
        return copyNewText;
    }

    public Set<String> findRemoved(Set<String> oldText, Set<String> newText) {
        Set<String> copyOldText = new HashSet<>(oldText);
        copyOldText.removeAll(newText);
        return copyOldText;
    }

}
