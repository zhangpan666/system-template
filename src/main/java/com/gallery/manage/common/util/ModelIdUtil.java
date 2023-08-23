package com.gallery.manage.common.util;

import com.light.core.utils.DateUtils;

import java.util.Date;

public class ModelIdUtil {

    public static Long generateAgentRewardId(Long userId){
        return Long.valueOf(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_MONTH_COMPACT)+userId);
    }

    public static Long generateAgentRewardId(Long userId,Date date){
        return Long.valueOf(DateUtils.format(date, DateUtils.DATE_FORMAT_MONTH_COMPACT)+userId);
    }

    public static Long generateDepositStatDayId(Long userId){
        return Long.valueOf(DateUtils.format(new Date(), DateUtils.DATE_FORMAT_COMPACT)+userId);
    }

    public static Long generateDepositStatDayId(Long userId,Date date){
        return Long.valueOf(DateUtils.format(date, DateUtils.DATE_FORMAT_COMPACT)+userId);
    }

    public static String generateIssue(Integer year, Integer term, Integer lotteryId){
        if (lotteryId == 2041){
            return String.valueOf((year-1911)*1000000+term);
        }
        return String.valueOf((Integer.valueOf(year+""+(term+100))-100));
    }
}
