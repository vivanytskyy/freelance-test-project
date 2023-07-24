package com.gmail.ivanytskyy.vitaliy.utils;

import java.util.function.Predicate;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 24/07/2023
 */
public enum HttpResponseCodeRanges {
    HTTP_100th(n -> n >= 100 && n < 200),
    HTTP_200th(n -> n >= 200 && n < 300),
    HTTP_300th(n -> n >= 300 && n < 400),
    HTTP_400th(n -> n >= 400 && n < 500),
    HTTP_500th(n -> n >= 500 && n < 600),
    HTTP_600th(n -> n >= 600 && n < 700);
    private final Predicate<Integer> inRange;
    HttpResponseCodeRanges(Predicate<Integer> predicate){
        this.inRange = predicate;
    }
    public boolean inRange(Integer code){
        return this.inRange.test(code);
    }
}