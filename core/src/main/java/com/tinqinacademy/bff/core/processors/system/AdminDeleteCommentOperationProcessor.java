package com.tinqinacademy.bff.core.processors.system;

import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentInput;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOperation;
import com.tinqinacademy.bff.api.operations.system.admindeletecomment.BffAdminDeleteCommentOutput;
import com.tinqinacademy.bff.core.errors.ErrorMapper;
import com.tinqinacademy.bff.core.processors.BaseOperationProcessor;
import com.tinqinacademy.bff.kafka.KafkaProducer;
import com.tinqinacademy.bff.kafka.model.DeleteMessage;
import com.tinqinacademy.comments.api.operations.system.admindeletecomment.AdminDeleteCommentOutput;
import com.tinqinacademy.comments.restexport.CommentsRestExport;
import com.tinqinacademy.search.api.operations.findwordsforcomment.FindWordsForCommentOutput;
import com.tinqinacademy.search.restexport.SearchRestExport;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminDeleteCommentOperationProcessor extends BaseOperationProcessor implements BffAdminDeleteCommentOperation {
    private final CommentsRestExport commentsRestExport;
    private final SearchRestExport searchRestExport;
    private final KafkaProducer kafkaProducer;

    public AdminDeleteCommentOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                                Validator validator, CommentsRestExport commentsRestExport,
                                                SearchRestExport searchRestExport, KafkaProducer kafkaProducer) {
        super(conversionService, errorMapper, validator);
        this.commentsRestExport = commentsRestExport;
        this.searchRestExport = searchRestExport;
        this.kafkaProducer = kafkaProducer;
    }

    private void deleteWordsForComment(String commentId){
        FindWordsForCommentOutput words = searchRestExport.findWords(commentId);

        words.getWords()
                .stream()
                .map(word -> DeleteMessage.builder()
                        .id(commentId)
                        .word(word)
                        .build())
                .forEach(kafkaProducer::sendDeleteMessage);
    }

    @Override
    public Either<Errors, BffAdminDeleteCommentOutput> process(BffAdminDeleteCommentInput input) {
        return Try.of(() -> {
                    log.info("Start process input:{}", input);
                    validate(input);

                    AdminDeleteCommentOutput commentsOutput = commentsRestExport
                            .adminDeleteComment(input.getCommentId());


                    BffAdminDeleteCommentOutput output = conversionService.convert(commentsOutput,
                            BffAdminDeleteCommentOutput.class);
                    deleteWordsForComment(input.getCommentId());

                    log.info("End process result:{}", output);

                    return output;
                })
                .toEither()
                .mapLeft(errorMapper::map);
    }
}
