package com.eelessam.raw.feed.app.domain;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class FoodValidator implements Validator {

    private static final String EMPTY_ERROR = "%s.empty";
    private static final String PERCENTAGES_DO_NOT_EQUAL_100_ERROR = "(%s, %s, %s).combination of percentages does not equal 100";
    private static final String MEAT_PERCENTAGE = "meatPercentage";
    private static final String OFFAL_PERCENTAGE = "offalPercentage";
    private static final String BONE_PERCENTAGE = "bonePercentage";
    private static final String NAME = "name";

    @Override
    public boolean supports(Class<?> aClass) {
        return Food.class.equals(aClass);
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        Food food = (Food) o;

        if (checkInputString(food.getName())) {
            errors.rejectValue(NAME, String.format(EMPTY_ERROR, NAME));
        }

        if (checkInputInteger(food.getBonePercentage())) {
            errors.rejectValue(BONE_PERCENTAGE, String.format(EMPTY_ERROR, BONE_PERCENTAGE));
        }

        if (checkInputInteger(food.getMeatPercentage())) {
            errors.rejectValue(MEAT_PERCENTAGE, String.format(EMPTY_ERROR, MEAT_PERCENTAGE));
        }

        if (checkInputInteger(food.getOffalPercentage())) {
            errors.rejectValue(OFFAL_PERCENTAGE, String.format(EMPTY_ERROR, OFFAL_PERCENTAGE));
        }

        if(calculateTotalPercentage(food) != 100) {
            errors.reject(String.format(PERCENTAGES_DO_NOT_EQUAL_100_ERROR, BONE_PERCENTAGE, MEAT_PERCENTAGE, OFFAL_PERCENTAGE));
        }

    }

    private Integer calculateTotalPercentage(Food food) {
        Integer bonePercentage = Optional.ofNullable(food.getBonePercentage()).orElse(0);
        Integer meatPercentage = Optional.ofNullable(food.getMeatPercentage()).orElse(0);
        Integer offalPercentage = Optional.ofNullable(food.getOffalPercentage()).orElse(0);

        return bonePercentage + meatPercentage + offalPercentage;
    }

    private boolean checkInputString(String input) {
        return (null == input || input.trim().length() == 0);
    }

    private boolean checkInputInteger(Integer input) {
        return (null == input || input < 0);
    }
}
