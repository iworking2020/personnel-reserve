/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.iworking.personnel.reserve.utils;

/**
 *
 * @author pi
 */
public class TextUtils {
    public static String nameForNumbers(Integer count) {
        int ageLastNumber = count % 10;
        boolean isExclusion = 11 <= count % 100 && count % 100 <= 14;
        String name = "";

        if (ageLastNumber == 1) {
            name = "год";
        } else if (ageLastNumber == 0 || (5 <= ageLastNumber && ageLastNumber <= 9)) {
            name = "лет";
        } else if (2 <= ageLastNumber && ageLastNumber <= 4) {
            name = "года";
        }

        if (isExclusion) name = "лет";

        return name;
    }
}
