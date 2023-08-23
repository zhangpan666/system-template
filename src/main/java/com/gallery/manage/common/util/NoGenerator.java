package com.gallery.manage.common.util;

import com.light.core.utils.DateUtils;

import java.util.Date;
import java.util.Random;

public class NoGenerator {

    public static String getTimeStr() {
        return DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT_COMPACT);
    }

    public static String generate(Long userId, byte tradeType) {
        return new StringBuilder().append(getTimeStr()).append(userId).append(tradeType).append(getRandomNumberString()).toString();
    }

    public static String generate(byte tradeType) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        String dateStr = DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT_HOUR_COMPACT);
        stringBuilder.append(dateStr).append(tradeType).append(random.nextInt(9000000) + 1000000);
        return stringBuilder.toString();
    }


    public static void main(String[] args) {

    }


    public static String getRandomNumberString() {
        Random random = new Random();
        return String.valueOf(random.nextInt(900000) + 100000);
    }
}
