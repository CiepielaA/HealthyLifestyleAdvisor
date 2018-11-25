package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.dao.DiaryDayRepository;
import com.ciepiela.adrian.dao.FrontEndProductRepository;
import com.ciepiela.adrian.dao.ProductRepository;
import com.ciepiela.adrian.dao.UserRepository;
import com.ciepiela.adrian.exceptions.DiaryDayNotFoundException;
import com.ciepiela.adrian.model.DiaryDay;
import com.ciepiela.adrian.model.FrontEndProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/diaryDay")
@EnableWebMvc
public class DiaryDayController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DiaryDayController.class);
    private final DiaryDayRepository diaryDayRepository;
    private final ProductRepository productRepository;
    private final FrontEndProductRepository frontEndProductRepository;
    private final UserRepository userRepository;

    @Autowired
    public DiaryDayController(DiaryDayRepository diaryDayRepository, ProductRepository productRepository, FrontEndProductRepository frontEndProductRepository, UserRepository userRepository) {
        this.diaryDayRepository = diaryDayRepository;
        this.productRepository = productRepository;
        this.frontEndProductRepository = frontEndProductRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<DiaryDay> create(@RequestBody DiaryDay diaryDay) {
        List<FrontEndProduct> frontEndProducts = diaryDay.getFrontEndProducts();
//        userRepository.save(diaryDay.getUser())
        for (FrontEndProduct frontEndProduct : frontEndProducts){
            productRepository.save(frontEndProduct.getProduct());
            frontEndProductRepository.save(frontEndProduct);

        }
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

    @RequestMapping(value = "/updateById/{diaryDayId}", method = RequestMethod.POST)
    public ResponseEntity<DiaryDay> update(@RequestBody DiaryDay updatedDiaryDay, @PathVariable long diaryDayId) {
        findIfDiaryDayExist(diaryDayId);
        DiaryDay exisitingDiaryDay = diaryDayRepository.getOne(diaryDayId);
        exisitingDiaryDay.setFrontEndProducts(updatedDiaryDay.getFrontEndProducts());
//        productRepository.save(exisitingDiaryDay.getFrontEndProducts());
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
//        LocalDate formattedDate = LocalDate.parse(date);
        Optional<DiaryDay> diaryDay = diaryDayRepository.findByDateAndUser_UserId(date, userId);
        if (diaryDay.isPresent()) {
            LOGGER.info("Found diaryDay with id: {} ", diaryDay.get().getDiaryDayId());
            return new ResponseEntity<>(diaryDay.get(), HttpStatus.OK);
        } else {
            throw new DiaryDayNotFoundException(date, userId);
        }
    }

    private void findIfDiaryDayExist(long diaryDayId) {
        diaryDayRepository.findByDiaryDayId(diaryDayId).orElseThrow(() -> new DiaryDayNotFoundException(diaryDayId));
    }
}
