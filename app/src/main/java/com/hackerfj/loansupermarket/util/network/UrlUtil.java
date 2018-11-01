package com.hackerfj.loansupermarket.util.network;



public class UrlUtil {
    //外网服务器地址
//    public static final String BASE_URL = "http://api.aoyi.pro:8080/api/";
    /**
     * 加载图片域名
     */
     public static final String BANNER_URL = "https://www.yidongdaichao.com";
    public static final String IMAGE_URL = "https://www.yidongdaichao.com/Public/";

    //内网服务器地址
    public static final String BASE_URL = "https://www.yidongdaichao.com/index.php/";
//    public static final String BASE_URL = "http://192.168.0.102/index.php/";

    /**
     * 判断调取哪一个界面
     */
    public static final String JUDGE = "Appapi/jzb/api";

    /**
     *获取banner
     */
    public static final String BANNER = "appapi/Index/banner";

    /**
     * 获取全部产品
     */
    public static final String ALL_PRODUCTTS = "appapi/Index/article_goods";

    /**
     * 首页产品分类
     */
    public static final String HOME_PAGE = "appapi/Index/dome";

    /**
     * 获取短信验证码
     */
    public static final String GET_CODE = "appapi/User/verifiDriver";

    /**
     * 登录
     */
    public static final String LOGIN_USER = "appapi/User/loginact";

    /**
     * 退出登录
     */
    public static final String EXIT_LOGON = "appapi/User/logout";

    /**
     * 用户中心获取用户个人信息
     */
    public static final String GET_USER = "appapi/User/getUserInfo";

    /**
     * 分类页贷款类型
     */
    public static final String TYPE_OF_LOAN = "appapi/Classification/art";

    /**
     *  分类页贷款金额产品/贷款类型产品
     */
    public static final String CLASSIFICATION_PAGE = "appapi/Classification/all";

    //============================================华丽的分割线==================================================================

    /**
     *获取记录
     */
    public static final String JIZ_ALL = "Appapi/jzb/list1";

    /**
     * 添加
     */
    public static final String JIZ_ALL_PRODUCTTS = "Appapi/jzb/budget";

    /**
     * tjia
     */
    public static final String JIZ_ADD_ALL = "Appapi/jzb/income";

    /**
     * 三个
     */
    public static final String JIZ_ALL_MONEY = "Appapi/jzb/monthly";

    /**
     * 登录
     */
    public static final String JIZ_LOGIN_USER = "Appapi/jzb/login";

    /**
     * 退出登录
     */
    public static final String JIZ_EXIT_LOGON = "Appapi/jzb/logout";

    /**
     * 统计图
     */
    public static final String JIZ_TJITU = "Appapi/jzb/count";
}
