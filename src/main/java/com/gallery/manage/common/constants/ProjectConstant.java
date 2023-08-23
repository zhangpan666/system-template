package com.gallery.manage.common.constants;

import com.light.core.model.CommonConstantInfo;
import com.light.core.utils.BaseConstantClassUtil;
import com.light.redis.model.RedisInfo;

import java.util.Map;

public class ProjectConstant {

    public static final byte COMMON_STATUS_AVAILABLE = 1;

    public static final byte COMMON_STATUS_NOT_AVAILABLE = 0;

    public static final byte COMMON_STATUS_DELETE = -1;

    public static final int CG_PAY_AMOUNT_BASE = 100000000;

    public static final String credentialsSalt = "credentialsSalt";

    public static final int hashIterations = 1024;

    public static final String APP_API_PREFIX = "/gallery/manage/api/app";

    public static final String H5_API_PREFIX = "/gallery/manage/api/h5";

    public static final String OPEN_API_PREFIX = "/gallery/manage/api/open/";

    public static final String API_PREFIX = "/gallery/manage/api/";

    public static final String ADMIN_PREFIX = "/gallery/manage/admin/";


    public static class UserType {
        public static final CommonConstantInfo ADMIN = new CommonConstantInfo(1, "后台用户");
        public static final CommonConstantInfo APP = new CommonConstantInfo(2, "APP用户");
    }

    public static class RedisKey {
        public static final RedisInfo USER_LOGIN_VERIFY_CODE = new RedisInfo().setKeyPrefix("USER_LOGIN_VERIFY_CODE").setName("谷歌验证器").setDay(30 * 6).setDbIndex(2);
        public static final RedisInfo APP_USER_LOGIN_INFO = new RedisInfo().setKeyPrefix("APP_USER_LOGIN_INFO").setMinute(5).setDbIndex(3).setName("APP用户登录信息缓存");
        public static final RedisInfo CALL_SYNC_LOCK = new RedisInfo().setSecond(20).setKeyPrefix("CALL_SYNC_LOCK").setName("同步锁").setDbIndex(0);
        public static final RedisInfo ONLINE_ADMIN_COUNT = new RedisInfo().setSecond(-1).setKeyPrefix("ONLINE_ADMIN_COUNT").setName("管理员在线人数");
        public static final RedisInfo ONLINE_ADMIN = new RedisInfo().setSecond(-1).setKeyPrefix("ONLINE_ADMIN").setName("管理员在线人数");
        public static final RedisInfo ONLINE_USER_COUNT = new RedisInfo().setSecond(-1).setKeyPrefix("ONLINE_USER_COUNT").setName("用户在线人数");
        public static final RedisInfo ONLINE_USER = new RedisInfo().setSecond(-1).setKeyPrefix("ONLINE_USER").setName("用户在线人数");
    }


    public static class SmsType {
        public static final byte REGISTER = 1;
        public static final byte LOGIN = 2;
        public static final byte FIND_PASSWORD = 3;
        public static final byte CHANGE_MOBILE_OLD = 4;
        public static final byte CHANGE_MOBILE_NEW = 5;
        public static final byte BINDING_MOBILE = 6;
    }


    public static class Related {
        public static final byte ROLE = 0;
    }

    public static class TradeType {
        public static final CommonConstantInfo REFUND = new CommonConstantInfo(0, "退款");
        public static final CommonConstantInfo CONTROL = new CommonConstantInfo(1, "调控打码");
        public static final CommonConstantInfo PROMOTE_REWARD = new CommonConstantInfo(8, "推广奖励发放");
        public static final CommonConstantInfo WITHDRAW = new CommonConstantInfo(9, "提现");
        public static final CommonConstantInfo EXCHANGE = new CommonConstantInfo(10, "兑换");
        public static final CommonConstantInfo TRANSFER_AMOUNT = new CommonConstantInfo(19, "资金转移");
        public static final CommonConstantInfo ACTIVITY_REWARD = new CommonConstantInfo(20, "活动彩金");
        public static final CommonConstantInfo LOSE_AMOUNT = new CommonConstantInfo(21, "额度丢失(提现/余额宝/余额转入)");
        public static final CommonConstantInfo LOSE_AMOUNT_1 = new CommonConstantInfo(22, "额度丢失(免提直充)");
        public static final CommonConstantInfo RECHARGE = new CommonConstantInfo(24, "充值");
        public static final CommonConstantInfo MONEY_AVAILABLE_TRANSFER_OUT = new CommonConstantInfo(25, "余额转出");
        public static final CommonConstantInfo MONEY_AVAILABLE_TRANSFER_IN = new CommonConstantInfo(26, "余额转入");
        public static final CommonConstantInfo RECHARGE_BY_MANUAL = new CommonConstantInfo(27, "人工存入");
        public static final CommonConstantInfo REDUCE_BY_MANUAL = new CommonConstantInfo(28, "人工提出");
        public static final CommonConstantInfo RECHARGE_BY_SYSTEM = new CommonConstantInfo(29, "系统存入");
        public static final CommonConstantInfo REDUCE_BY_SYSTEM = new CommonConstantInfo(30, "系统提出");
        public static final CommonConstantInfo BROKERAGE = new CommonConstantInfo(31, "佣金派发");
        public static final CommonConstantInfo WW = new CommonConstantInfo(32, "冲正");
        public static final CommonConstantInfo REBATE = new CommonConstantInfo(33, "返点");
        public static final CommonConstantInfo AGENT_MONTH_REWARD = new CommonConstantInfo(34, "月负盈利分红");
        public static final CommonConstantInfo DEPOSIT_TRANSFER_OUT = new CommonConstantInfo(35, "转出余额宝");
        public static final CommonConstantInfo DEPOSIT_TRANSFER_IN = new CommonConstantInfo(36, "转入余额宝");
        public static final CommonConstantInfo DEPOSIT_SEND_PROFIT = new CommonConstantInfo(37, "余额宝收益");
        public static final CommonConstantInfo RECHARGE_BY_WITHDRAW_DIRECT = new CommonConstantInfo(38, "免提直充");
        public static final CommonConstantInfo MARKET_ARTICLE_REWARD = new CommonConstantInfo(39, "淘料市场打赏");
        public static final CommonConstantInfo MARKET_ARTICLE_RECEIVE_REWARD = new CommonConstantInfo(40, "淘料市场收到打赏");
        public static final CommonConstantInfo MARKET_ARTICLE_PURCHASE = new CommonConstantInfo(41, "淘料市场购买资料");
        public static final CommonConstantInfo MARKET_ARTICLE_INCOME = new CommonConstantInfo(42, "淘料市场资料收益");
        public static final Map<Byte, String> map = BaseConstantClassUtil.getMap(TradeType.class);
    }


    public static class TradeStatus {
        public static final CommonConstantInfo FAIL = new CommonConstantInfo(0, "交易失败");
        public static final CommonConstantInfo SUCCESS = new CommonConstantInfo(1, "交易成功");
        public static final CommonConstantInfo FREEZE = new CommonConstantInfo(2, "冻结");
        public static final CommonConstantInfo CANCEL = new CommonConstantInfo(3, "取消");
        public static final Map<Byte, String> map = BaseConstantClassUtil.getMap(TradeStatus.class);
    }

    public static class AmountTransferType {
        public static final CommonConstantInfo TRANSFER_IN = new CommonConstantInfo(1, "转入");
        public static final CommonConstantInfo TRANSFER_OUT = new CommonConstantInfo(2, "转出");
    }


    public static class StatusColor {
        public static final byte white = 0;
        public static final byte blue = 1;
        public static final byte gray = 2;
        public static final byte red = 3;
        public static final byte black = 4;
    }


    public static class Sort {
        public static final Byte DESC = 1;
        public static final Byte ASC = 0;
    }


    public static final class PaymentType {
        public static final byte EXPENSE = -1;
        public static final byte INCOME = 1;
        public static final byte ONLY_RECORD = 0;
    }

    public static class Verify {
        public static final CommonConstantInfo STATUS_ING = new CommonConstantInfo(0, "审核中");
        public static final CommonConstantInfo STATUS_SUCCESS = new CommonConstantInfo(1, "审核通过");
        public static final CommonConstantInfo STATUS_FAIL = new CommonConstantInfo(2, "审核不通过");
        public static final Map<Byte, String> map = BaseConstantClassUtil.getMap(Verify.class);
    }


    public static class NoticeType {
        public static final byte ADMIN = 1;
        public static final byte USER = 2;
        public static final byte INDEX = 3;
        public static final byte COMMUNICATE = 4;
        public static final byte CHAT = 5;
    }


    public static class MessageType {
        public static final CommonConstantInfo LOGIN_NOTICE = new CommonConstantInfo(1, "账号异地登录提醒");
        public static final CommonConstantInfo NEW_RECHARGE_COMPANY_ACCOUNT = new CommonConstantInfo(2, "公司入款申请通知");
        public static final CommonConstantInfo NEW_WITHDRAW_APPLY = new CommonConstantInfo(3, "出款申请通知");
        public static final CommonConstantInfo RECHARGE_NOTICE = new CommonConstantInfo(4, "会员充值通知");
        public static final CommonConstantInfo RECHARGE_BY_WITHDRAW_DIRECT = new CommonConstantInfo(5, "免提直冲申请通知");
    }

    public static class WithdrawAccountType {
        public static final CommonConstantInfo CG_ACCOUNT = new CommonConstantInfo(1, "CG钱包");
        public static final CommonConstantInfo BANKCARD_ACCOUNT = new CommonConstantInfo(2, "银行卡");
        public static final CommonConstantInfo GB_ACCOUNT = new CommonConstantInfo(3, "购宝钱包");
        public static final CommonConstantInfo DIRECT_ACCOUNT = new CommonConstantInfo(4, "免提直充");
        public static final CommonConstantInfo GO_PAY_ACCOUNT = new CommonConstantInfo(5, "GoPay钱包");
        public static final CommonConstantInfo OK_PAY_ACCOUNT = new CommonConstantInfo(6, "OK钱包");
        public static final CommonConstantInfo MPAY_ACCOUNT = new CommonConstantInfo(7, "MPay钱包");
    }

    public enum DeviceType {
        APP, IOS, ANDROID, H5
    }


    public static void main(String[] args) {

    }

}
