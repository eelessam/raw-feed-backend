package com.eelessam.raw.feed.app.web;

import com.eelessam.raw.feed.app.domain.Food;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/food")
public class FoodController {

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
