package com.eelessam.raw.feed.app.web;

import com.eelessam.raw.feed.app.domain.Food;
import com.eelessam.raw.feed.app.domain.FoodValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/food")
public class FoodController {

    @Autowired
    private FoodValidator foodValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(foodValidator);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public void get(@RequestParam(value = "foodId") Long foodId) {
        // TODO implement get

    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @Valid
    public void create(@Valid @RequestBody Food food) {
        // TODO implement create
    }
}
