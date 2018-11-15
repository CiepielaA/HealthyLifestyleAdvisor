package com.ciepiela.adrian.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DiaryDayNotFoundException extends RuntimeException {
    private final static Logger LOGGER = LoggerFactory.getLogger(DiaryDayNotFoundException.class);

    public DiaryDayNotFoundException(long diaryDayId) {
        LOGGER.warn("Could not find diaryDay with id: {}", diaryDayId);
    }

    public DiaryDayNotFoundException(String date, long userId) {
        LOGGER.warn("Could not find diaryDay with date: {} and userId: {}", date, userId);
    }

}
