package com.gallery.manage.common.util;

import com.alibaba.fastjson.JSONObject;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.service.ConfigurationService;
import com.light.config.util.ApplicationContextUtil;
import com.light.core.utils.DateUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConfigurableVarUtil {

    private static final ConcurrentMap<String, String> varMap = new ConcurrentHashMap<>();

    public static String BET_ORDER() {
        return get("BET_ORDER", String.class, "https://wssa-201.xhhuiheng.com:1086");
    }

    public static boolean IS_TEST() {
        return get("IS_TEST", Boolean.class, true);
    }

    public static int MAX_NOTICE_RETRY_COUNT() {
        return get("MAX_NOTICE_RETRY_COUNT", Integer.class, 20);
    }

    public static int RECHARGE_WITHDRAW_DIRECT_COUNT() {
        return get("RECHARGE_WITHDRAW_DIRECT_COUNT", Integer.class, 1);
    }

    public static int BANK_WITHDRAW_DAY_LIMIT_COUNT() {
        return get("BANK_WITHDRAW_DAY_LIMIT_COUNT", Integer.class, 3);
    }

    public static int WALLET_WITHDRAW_DAY_LIMIT_COUNT() {
        return get("WALLET_WITHDRAW_DAY_LIMIT_COUNT", Integer.class, 5);
    }

    public static int ALLOWED_SUBMIT_MAX_PROCESSING_RECHARGE_COMPANY_COUNT_DAY() {
        return get("ALLOWED_SUBMIT_MAX_PROCESSING_RECHARGE_COMPANY_COUNT_DAY", Integer.class, 5);
    }

    public static String ADMIN_ALLOWED_IP() {
        return get("ADMIN_ALLOWED_IP", String.class);
    }

    public static String API_ALLOWED_IP() {
        return get("API_ALLOWED_IP", String.class);
    }

    public static String OPEN_API_ALLOWED_IP() {
        return get("OPEN_API_ALLOWED_IP", String.class);
    }

    public static String USER_ALLOWED() {
        return get("USER_ALLOWED", String.class);
    }

    public static String START_TIME() {
        return get("START_TIME", String.class, "21:30");
    }

    public static String END_TIME() {
        return get("END_TIME", String.class, "21:50");
    }

    public static Date AGENT_TIME() {
        return get("AGENT_TIME", Date.class, DateUtils.parse(DateUtils.DATE_TIME_FORMAT, "2021-02-07 00:00:00"));
    }

    public static String AOMEN_TEMA_PRIZE() {
        return get("AOMEN_TEMA_PRIZE", String.class, "48.800");
    }

    public static String HONGKONG_TEMA_PRIZE() {
        return get("HONGKONG_TEMA_PRIZE", String.class, "48.000");
    }

    public static Byte ALLOWED_WITHDRAW() {
        return get("ALLOWED_WITHDRAW", Byte.class, ProjectConstant.COMMON_STATUS_NOT_AVAILABLE);
    }

    public static double ALLOWED_WITHDRAW_MIN_AMOUNT() {
        return get("ALLOWED_WITHDRAW_MIN_AMOUNT", Double.class, 100D);
    }

    public static double CARD_ALLOWED_WITHDRAW_MIN_AMOUNT() {
        return get("CARD_ALLOWED_WITHDRAW_MIN_AMOUNT", Double.class, 100D);
    }

    public static double ALLOWED_WITHDRAW_MAX_AMOUNT() {
        return get("ALLOWED_WITHDRAW_MAX_AMOUNT", Double.class, 1000000D);
    }

    //自动出款最低金额
    public static double AUTO_PAY_MIN_AMOUNT() {
        return get("AUTO_PAY_MIN_AMOUNT", Double.class, 100D);
    }

    //自动出款最高金额
    public static double AUTO_PAY_MAX_AMOUNT() {
        return get("AUTO_PAY_MAX_AMOUNT", Double.class, 5000D);
    }

    public static boolean IS_AUTO() {
        return get("IS_AUTO", Boolean.class, false);
    }

    public static double ALLOWED_EXCHANGE_MIN_AMOUNT() {
        return get("ALLOWED_EXCHANGE_MIN_AMOUNT", Double.class, 50D);
    }

    public static double ALLOWED_EXCHANGE_MAX_AMOUNT() {
        return get("ALLOWED_EXCHANGE_MAX_AMOUNT", Double.class, 100D);
    }

    public static BigDecimal EXCHANGE_RATIO() {
        return get("EXCHANGE_RATIO", BigDecimal.class, BigDecimal.valueOf(5));
    }

    public static int ALLOWED_EXCHANGE_MAX_COUNT_DAY() {
        return get("ALLOWED_EXCHANGE_MAX_COUNT_DAY", Integer.class, 1);
    }

    public static Integer DAILY_HELP_MAX_COUNT_PROVIDE() {
        return get("DAILY_HELP_MAX_COUNT_PROVIDE", Integer.class, 10);
    }

    public static Integer DAILY_HELP_MAX_COUNT_RECEIVE() {
        return get("DAILY_HELP_MAX_COUNT_RECEIVE", Integer.class, 5);
    }

    public static BigDecimal WITHDRAW_FEERATE() {
        return get("WITHDRAW_FEERATE", BigDecimal.class, new BigDecimal("0.02"));
    }

    public static String CHARGE_H5_OPEN_URL() {
        return get("RECHARGE_H5_OPEN_URL", String.class, "https://amtkpay.com");
    }

    public static BigDecimal COMPANY_RECHARGE_REWARD_RATIO() {
        return get("COMPANY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.01"));
    }

    public static BigDecimal CG_PAY_RECHARGE_REWARD_RATIO() {
        return get("CG_PAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.01"));
    }

    public static BigDecimal GO_PAY_RECHARGE_REWARD_RATIO() {
        return get("GO_PAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.01"));
    }

    public static BigDecimal OK_PAY_RECHARGE_REWARD_RATIO() {
        return get("OK_PAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.01"));
    }

    public static BigDecimal MPAY_RECHARGE_REWARD_RATIO(){
        return get("MPAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.00"));
    }

    public static BigDecimal COMMON_PAY_RECHARGE_REWARD_RATIO() {
        return get("COMMON_PAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.00"));
    }

    public static BigDecimal ONLINE_PAY_RECHARGE_REWARD_RATIO() {
        return get("ONLINE_PAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.00"));
    }

    public static BigDecimal WITHDRAW_DIRECT_RECHARGE_AOMEN_VALIDAMOUNT_REWARD_RATIO(){
        return get("WITHDRAW_DIRECT_RECHARGE_AOMEN_VALIDAMOUNT_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.00"));
    }

    public static BigDecimal RECHARGE_BY_WITHDRAW_DIRECT_REWARD_RATIO() {
        return get("RECHARGE_BY_WITHDRAW_DIRECT_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.012"));
    }

    public static BigDecimal GB_PAY_RECHARGE_REWARD_RATIO() {
        return get("GB_PAY_RECHARGE_REWARD_RATIO", BigDecimal.class, new BigDecimal("0.00"));
    }

    public static Byte ALLOWED_EXCHANGE() {
        return get("ALLOWED_EXCHANGE", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static BigDecimal MARKET_ARTICLE_REWARD_RATIO() {
        return get("MARKET_ARTICLE_REWARD_RATIO", BigDecimal.class, BigDecimal.valueOf(0.5));
    }

    public static BigDecimal PURCHASE_MARKET_ARTICLE_RATIO() {
        return get("PURCHASE_MARKET_ARTICLE_RATIO", BigDecimal.class, BigDecimal.valueOf(0.3));
    }

    public static Byte AG_STATUS() {
        return get("AG_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte BG_STATUS() {
        return get("BG_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte JB_STATUS() {
        return get("JB_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte AE_STATUS() {
        return get("AE_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte ALLOWED_RECHARGE_COMPANY_BANK_ACCOUNT() {
        return get("ALLOWED_RECHARGE_COMPANY_BANK_ACCOUNT", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static String BG_LINE_NAME() {
        return get("BG_LINE_NAME", String.class, "澳門線路,香港線路,大陸線路,菲律賓線路");
    }

    public static String BG_LINE_ID() {
        return get("BG_LINE_ID", String.class, "1,2,3,4");
    }

    public static String SYSTEM_FILE_HOST() {
        return get("SYSTEM_FILE_HOST", String.class, SystemUtil.defaultFileHost());
    }

    public static Byte GAME_COMMON_STATUS() {
        return get("GAME_COMMON_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte BG_GAME_COMMON_STATUS() {
        return get("BG_GAME_COMMON_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_NOT_AVAILABLE);
    }

    public static long CG_ORDER_EXPIRE_TIME_SPAN() {
        return get("CG_Order_Expire_Time_Span", Long.class, 1800L);
    }

    public static String CG_PAY_MERCHANT_ID() {
        return get("CG_PAY_MERCHANT_ID", String.class);
    }

    public static String CG_PAY_API_KEY() {
        return get("CG_PAY_API_KEY", String.class);
    }

    public static String CG_PAY_BASE_URL() {
        return get("CG_PAY_BASE_URL", String.class);
    }

    public static Byte CG_PAY_STATUS() {
        return get("CG_PAY_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte GB_PAY_STATUS() {
        return get("GB_PAY_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static Byte GO_PAY_STATUS() {
        return get("GO_PAY_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static String COMMON_PAY_SUCCESS_REFER_URL() {
        return get("COMMON_PAY_SUCCESS_REFER_URL", String.class, "https://xgtkpays.com/pay/success.html");
    }

    public static String GO_PAY_H5_URL() {
        return get("GO_PAY_H5_URL", String.class, "http://h5.gogrant.xyz/");
    }

    public static String PROMOTE_RANK_REWARD() {
        return get("PROMOTE_RANK_REWARD", String.class, "10,10,10,10,10,10,10,10,10,10");
    }

    public static byte PROMOTE_REWARD_STATUS() {
        return get("PROMOTE_REWARD_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_NOT_AVAILABLE);
    }

    public static BigDecimal MIN_LOTTERY_BET_AMOUNT() {
        return get("MIN_LOTTERY_BET_AMOUNT", BigDecimal.class, new BigDecimal("1000.00"));
    }

    public static BigDecimal MIN_GAME_BET_AMOUNT() {
        return get("MIN_GAME_BET_AMOUNT", BigDecimal.class, new BigDecimal("5000.00"));
    }

    public static BigDecimal MIN_USEFUL_BET_AMOUNT() {
        return get("MIN_USEFUL_BET_AMOUNT", BigDecimal.class, new BigDecimal("1000.00"));
    }


    public static String AGENT_RULE() {
        return get("AGENT_RULE", String.class,

                "1、代理工资直接派发到账号，可直接游戏娱乐，也可直接提现\n" +
                        "无流水限制。（单次提现最低100人民币）\n" +
                        "2、开通代理权限请联系在线客服（代理小纸条ID:888888）申请。");
    }

    public static String AGENT_REMARK() {
        return get("AGENT_REMARK", String.class,
                "注：代理在结算流水工资时，需同时满足“有效流水”与“最低有效会员”两个\n" +
                        "条件，才能达到相应比例工资。（彩票类会员有效投注5000元以上算有效会\n" +
                        "员，游戏类会员有效投注1000元以上算有效会员）");
    }

    public static String GB_PAY_CALLBACK_URL() {
        return get("GB_PAY_CALLBACK_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/GBPay/notify");
//        return get("GB_PAY_CALLBACK_URL", String.class, "http://18.166.216.186:9920/gallery/manage/api/open/account/GBPay/notify");
    }

    public static String GB_PAY_Bind_CALLBACK_URL() {
//        return get("GB_PAY_Bind_CALLBACK_URL", String.class, "http://18.166.216.186:9920/gallery/manage/api/open/account/bindGBPay/notify");
        return get("GB_PAY_Bind_CALLBACK_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/bindGBPay/notify");
    }

    public static String GO_PAY_CALLBACK_URL() {
        return get("GO_PAY_CALLBACK_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/GOPay/notify");
    }

    public static String OK_PAY_CALLBACK_URL() {
        return get("OK_PAY_CALLBACK_URL", String.class, "https://mhylpay.com/gallery/manage/api/open/account/OKPay/notify");
    }

    public static String GO_PAY_BASE_URL() {
        return get("GO_PAY_BASE_URL", String.class, "https://h2a5x89j1q.gopay001.com");
    }

    public static String OK_PAY_BASE_URL() {
        return get("OK_PAY_BASE_URL", String.class, "https://api.okpay777.com");
    }

    public static String GO_PAY_RECV_ID() {
        return get("GO_PAY_RECV_ID", String.class);
    }

    public static String OK_PAY_RECV_ID() {
        return get("OK_PAY_RECV_ID", String.class);
    }

    public static String GO_PAY_API_KEY() {
        return get("GO_PAY_API_KEY", String.class);
    }

    public static String OK_PAY_API_KEY() {
        return get("OK_PAY_API_KEY", String.class);
    }

    public static String GO_PAY_API_KEY2() {
        return get("GO_PAY_API_KEY2", String.class);
    }

    public static String GO_PAY_API_KEY3() {
        return get("GO_PAY_API_KEY3", String.class);
    }

    public static String OK_PAY_API_KEY2() {
        return get("OK_PAY_API_KEY2", String.class);
    }

    public static String OK_PAY_API_KEY3() {
        return get("OK_PAY_API_KEY3", String.class);
    }

    public static String BO_HUI_PAY_CALLBACK_URL() {
        return get("BO_HUI_PAY_CALLBACK_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/GOPay/notify");
    }

    public static String YUAN_PAY_NOTIFY_URL() {
        return get("YUAN_PAY_NOTIFY_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/YuanPay/notify");
    }

    public static String YUAN_PAY_WITHDRAW_NOTIFY_URL() {
        return get("YUAN_PAY_WITHDRAW_NOTIFY_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/YuanPay/withdraw/notify");
    }

    public static String GO_PAY_CALLBACK_URL_TEST() {
        return get("GO_PAY_CALLBACK_URL_TEST", String.class, "http://18.166.216.186:9990/gallery/manage/api/open/account/GOPay/notify");
    }

    public static String CG_PAY_CALLBACK_URL() {
        return get("CG_PAY_CALLBACK_URL", String.class, "https://xgtkpays.com/gallery/manage/api/open/account/CGPay/notify");
    }

    public static String LOTTERY_SERVER_API_ID() {
        return get("LOTTERY_SERVER_API_ID", String.class);
    }

    public static String LOTTERY_SERVER_API_KEY() {
        return get("LOTTERY_SERVER_API_KEY", String.class);
    }


    public static JSONObject getTotal() {
        if (CollectionUtils.isEmpty(varMap)) {
            init();
        }
        return JSONObject.parseObject(JSONObject.toJSONString(varMap));
    }


    public static synchronized void init() {
        ConfigurationService configurableVarService = ApplicationContextUtil.getBean(ConfigurationService.class);
        if (configurableVarService == null) {
            return;
        }
        List<Configuration> list = configurableVarService.list();
        list.forEach(var -> {
            varMap.put(var.getCode(), var.getValue());
        });
    }

    public static <T> T get(String key, Class<T> clazz, T defaultValue) {
        T val = get(key, clazz);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    public static <T> T get(String key, Class<T> clazz) {
        String val = varMap.get(key);
        if (val == null) {
            return null;
        }
        if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(val);
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(val);
        } else if (clazz.equals(Double.class)) {
            return (T) Double.valueOf(val);
        } else if (clazz.equals(Float.class)) {
            return (T) Float.valueOf(val);
        } else if (clazz.equals(Byte.class)) {
            return (T) Byte.valueOf(val);
        } else if (clazz.equals(String.class)) {
            return (T) val;
        } else if (clazz.equals(Boolean.class)) {
            return (T) Boolean.valueOf(val);
        } else if (clazz.equals(BigDecimal.class)) {
            return (T) new BigDecimal(val);
        } else if (clazz.equals(Date.class)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                return (T) sdf.parse(val);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        throw new RuntimeException("该类型不存在！！！");
    }

}
