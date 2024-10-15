package com.flipfit.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

        public static void displayCurrentDate() {

            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = localDateTime.format(formatter);
            System.out.println(formattedDateTime);

        }
    }

