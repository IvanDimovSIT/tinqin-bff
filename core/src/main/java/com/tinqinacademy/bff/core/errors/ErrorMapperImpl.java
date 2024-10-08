package com.tinqinacademy.bff.core.errors;


import com.tinqinacademy.bff.api.errors.Errors;
import com.tinqinacademy.bff.api.exception.BaseException;
import com.tinqinacademy.bff.api.exception.exceptions.ViolationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Component
@RequiredArgsConstructor
public class ErrorMapperImpl implements ErrorMapper {
    private final FeignErrorMapper feignErrorMapper;

    private Errors convertCustomException(Throwable throwable) {
        BaseException exception = (BaseException) throwable;

        return Errors.builder()
                .error(throwable.getMessage(), exception.getHttpStatus())
                .build();
    }

    private Errors convertDefaultException(Throwable throwable) {
        return Errors.builder()
                .error(throwable.getMessage(), HttpStatus.BAD_REQUEST)
                .build();
    }

    private Errors convertViolationException(Throwable throwable) {
        ViolationException exception = (ViolationException) throwable;

        Errors.ErrorsBuilder errors = Errors.builder();
        exception.getErrors()
                .forEach(error -> errors.error(error, HttpStatus.BAD_REQUEST));

        return errors.build();

    }

    private Errors convertFeignException(Throwable throwable) {
        return feignErrorMapper.mapFeignException((FeignException) throwable);
    }

    @Override
    public Errors map(Throwable throwable) {
        return Match(throwable).of(
                Case($(instanceOf(ViolationException.class)),
                        this::convertViolationException
                ),
                Case($(instanceOf(BaseException.class)),
                        this::convertCustomException
                ),
                Case($(instanceOf(FeignException.class)),
                        this::convertFeignException
                ),
                Case($(),
                        this::convertDefaultException
                )
        );
    }
}
