package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.dao.DiaryDayRepository;
import com.ciepiela.adrian.exceptions.DiaryDayNotFoundException;
import com.ciepiela.adrian.model.DiaryDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping(value = "/diaryDay")
@EnableWebMvc
public class DiaryDayController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DiaryDayController.class);
    private final DiaryDayRepository diaryDayRepository;

    @Autowired
    public DiaryDayController(DiaryDayRepository diaryDayRepository) {
        this.diaryDayRepository = diaryDayRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<DiaryDay> create(@RequestBody DiaryDay diaryDay) {
        DiaryDay savedDiaryDay = diaryDayRepository.save(diaryDay);
        LOGGER.info("Create diaryDay with id: {}", diaryDay.getDiaryDayId());
        return new ResponseEntity<>(savedDiaryDay, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{diaryDayId}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable long diaryDayId) {
        findIfDiaryDayExist(diaryDayId);
        diaryDayRepository.delete(diaryDayId);
        LOGGER.info("Delete diaryDay with id {}", diaryDayId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{diaryDayId}", method = RequestMethod.POST)
    public ResponseEntity<DiaryDay> update(@RequestBody DiaryDay updatedDiaryDay, @PathVariable long diaryDayId) {
        findIfDiaryDayExist(diaryDayId);
        DiaryDay exisitingDiaryDay = diaryDayRepository.getOne(diaryDayId);
        exisitingDiaryDay.setProducts(updatedDiaryDay.getProducts());
        DiaryDay savedDiaryDay = diaryDayRepository.save(exisitingDiaryDay);
        LOGGER.info("Update diaryDay with id: {}", diaryDayId);
        return new ResponseEntity<>(savedDiaryDay, HttpStatus.OK);
    }

    @RequestMapping(value = "/findById/{diaryDayId}", method = RequestMethod.GET)
    public ResponseEntity<DiaryDay> findById(@PathVariable long diaryDayId) {
        Optional<DiaryDay> diaryDay = diaryDayRepository.findByDiaryDayId(diaryDayId);
        if (diaryDay.isPresent()) {
            LOGGER.info("Found diaryDay with id: {} ", diaryDayId);
            return new ResponseEntity<>(diaryDay.get(), HttpStatus.OK);
        } else {
            throw new DiaryDayNotFoundException(diaryDayId);
        }
    }

    @RequestMapping(value = "/findByDateAndUserId/{date}/{userId}", method = RequestMethod.GET)
    public ResponseEntity<DiaryDay> findByDateAndUserId(@PathVariable String date, @PathVariable long userId) {
        LocalDate formattedDate = LocalDate.parse(date);
        Optional<DiaryDay> diaryDay = diaryDayRepository.findByDateAndUser_UserId(formattedDate, userId);
        if (diaryDay.isPresent()) {
            LOGGER.info("Found diaryDay with id: {} ", diaryDay.get().getDiaryDayId());
            return new ResponseEntity<>(diaryDay.get(), HttpStatus.OK);
        } else {
            throw new DiaryDayNotFoundException(formattedDate, userId);
        }
    }

    private void findIfDiaryDayExist(long diaryDayId) {
        diaryDayRepository.findByDiaryDayId(diaryDayId).orElseThrow(() -> new DiaryDayNotFoundException(diaryDayId));
    }
}
