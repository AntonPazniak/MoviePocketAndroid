/*
 *
 *  * ******************************************************
 *  *  Copyright (C) MoviePocket <prymakdn@gmail.com>
 *  *  This file is part of MoviePocket.
 *  *  MoviePocket can not be copied and/or distributed without the express
 *  *  permission of Danila Prymak, Alexander Trafimchyk and Anton Pozniak
 *  * *****************************************************
 *
 */

package com.example.moviepocketandroid.util;

public class PasswordValidator {

    public static boolean isValidPassword(String password) {
        // Проверка на количество символов (минимум 8)
        if (password.length() < 8) {
            return false;
        }

        // Проверка наличия заглавной буквы
        boolean hasUppercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
                break;
            }
        }

        if (!hasUppercase) {
            return false;
        }

        // Проверка наличия специального символа
        String specialCharacters = "!@#$%^&*()_+";
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                hasSpecialCharacter = true;
                break;
            }
        }

        return hasSpecialCharacter;
    }
}
