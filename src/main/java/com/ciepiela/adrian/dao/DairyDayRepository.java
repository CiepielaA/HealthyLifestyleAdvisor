package com.ciepiela.adrian.dao;

import com.ciepiela.adrian.model.DiaryDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DairyDayRepository extends JpaRepository<DiaryDay, Long> {
    Optional<DiaryDay> findByDiaryDayId (long diaryDayId);

    Optional<DiaryDay> findByDateAndUser (LocalDate date, long userId);
}
