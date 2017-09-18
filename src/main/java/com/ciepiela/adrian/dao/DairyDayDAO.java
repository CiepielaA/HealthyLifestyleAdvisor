package com.ciepiela.adrian.dao;

import com.ciepiela.adrian.model.DiaryDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DairyDayDAO extends JpaRepository<DiaryDay, Long> {
    DiaryDay findByDateAndUser (LocalDate date, long userId);

}
