package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class Mail{
    private final String mailFrom, mailTo, subject, message;

    @Builder.Default
    private final Optional<String> toCc = Optional.empty();
}
