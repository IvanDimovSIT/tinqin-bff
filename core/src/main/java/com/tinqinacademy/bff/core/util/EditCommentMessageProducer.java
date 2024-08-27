package com.tinqinacademy.bff.core.util;

import com.tinqinacademy.bff.kafka.KafkaProducer;
import com.tinqinacademy.bff.kafka.model.DeleteMessage;
import com.tinqinacademy.bff.kafka.model.WordMessage;
import com.tinqinacademy.search.restexport.SearchRestExport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EditCommentMessageProducer {
    private final KafkaProducer kafkaProducer;
    private final SearchRestExport searchRestExport;
    private final TextSplitter textSplitter;
    private final TextDifferenceFinder textDifferenceFinder;

    public void sendMessageForEditedComment(String commentId, String newContent) {
        Set<String> oldWords = searchRestExport.findWords(commentId)
                .getWords();
        Set<String> newWords = textSplitter.splitIntoUniqueWords(newContent);

        Set<String> wordsToAdd = textDifferenceFinder.findAdded(oldWords, newWords);
        Set<String> wordsToDelete = textDifferenceFinder.findRemoved(oldWords, newWords);

        wordsToAdd.stream()
                .map(word -> WordMessage.builder()
                        .id(commentId)
                        .word(word)
                        .build())
                .forEach(kafkaProducer::sendWordMessage);


        wordsToDelete.stream()
                .map(word -> DeleteMessage.builder()
                        .id(commentId)
                        .word(word)
                        .build())
                .forEach(kafkaProducer::sendDeleteMessage);

    }

}
