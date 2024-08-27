package com.tinqinacademy.bff.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TextDifferenceFinder.class)
public class TextDifferenceFinderTests {
    @Autowired
    private TextDifferenceFinder textDifferenceFinder;

    @Test
    public void testFindAddedAndRemoved(){
        Set<String> oldWords = Set.of("aa", "ab");
        Set<String> newWords = Set.of("abc", "ab");

        Set<String> added = textDifferenceFinder.findAdded(oldWords, newWords);
        Set<String> removed = textDifferenceFinder.findRemoved(oldWords, newWords);

        assertEquals(added.size(), 1);
        assertEquals(removed.size(), 1);

        assertEquals(added.toArray()[0], "abc");
        assertEquals(removed.toArray()[0], "aa");
    }

    @Test
    public void testFindAdded(){
        Set<String> oldWords = Set.of("abc");
        Set<String> newWords = Set.of("abc", "ab");

        Set<String> added = textDifferenceFinder.findAdded(oldWords, newWords);
        Set<String> removed = textDifferenceFinder.findRemoved(oldWords, newWords);

        assertEquals(added.size(), 1);
        assertEquals(removed.size(), 0);

        assertEquals(added.toArray()[0], "ab");
    }

    @Test
    public void testFindRemoved(){
        Set<String> oldWords = Set.of("abc", "ab", "bc");
        Set<String> newWords = Set.of("abc", "ab");

        Set<String> added = textDifferenceFinder.findAdded(oldWords, newWords);
        Set<String> removed = textDifferenceFinder.findRemoved(oldWords, newWords);

        assertEquals(added.size(), 0);
        assertEquals(removed.size(), 1);

        assertEquals(removed.toArray()[0], "bc");
    }

    @Test
    public void testFindAddedAndRemovedMultiple(){
        Set<String> oldWords = Set.of("aa", "ab", "ddd", "bb", "e");
        Set<String> newWords = Set.of("abc", "ab", "ddd", "ccc");

        Set<String> added = textDifferenceFinder.findAdded(oldWords, newWords);
        Set<String> removed = textDifferenceFinder.findRemoved(oldWords, newWords);

        assertEquals(added.size(), 2);
        assertEquals(removed.size(), 3);

        assertTrue(added.contains("abc"));
        assertTrue(added.contains("ccc"));

        assertTrue(removed.contains("aa"));
        assertTrue(removed.contains("bb"));
        assertTrue(removed.contains("e"));
    }

    @Test
    public void testNoDiffereces(){
        Set<String> oldWords = Set.of("abc", "ab");
        Set<String> newWords = Set.of("abc", "ab");

        Set<String> added = textDifferenceFinder.findAdded(oldWords, newWords);
        Set<String> removed = textDifferenceFinder.findRemoved(oldWords, newWords);

        assertEquals(added.size(), 0);
        assertEquals(removed.size(), 0);


        oldWords = Set.of();
        newWords = Set.of();
        added = textDifferenceFinder.findAdded(oldWords, newWords);
        removed = textDifferenceFinder.findRemoved(oldWords, newWords);

        assertEquals(added.size(), 0);
        assertEquals(removed.size(), 0);
    }



}
