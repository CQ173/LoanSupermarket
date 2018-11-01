package com.hackerfj.loansupermarket.util.network;

import com.hackerfj.loansupermarket.model.entity.res.GetHomePageRes;
import com.hackerfj.loansupermarket.model.entity.res.GetHomebannerRes;
import com.hackerfj.loansupermarket.model.entity.res.GetUserRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZAllRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZSangeRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZStingsRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZTuiRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZTunjiRes;
import com.hackerfj.loansupermarket.model.entity.res.JIZUserRes;
import com.hackerfj.loansupermarket.model.entity.res.JudgeRes;
import com.hackerfj.loansupermarket.model.entity.res.Param;
import com.hackerfj.loansupermarket.model.entity.res.TypeofloanRes;
import com.hackerfj.loansupermarket.model.entity.res.UserinfoRes;
import com.hackerfj.loansupermarket.model.entity.res.WholeInfoRes;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
public interface ApiService {

    /**
     * 判断调取哪一个界面
     */
    @GET(UrlUtil.JUDGE)
    Observable<BaseModel<JudgeRes>> getjudge(

    );

    /**
     * 获取banner
     */
    @GET(UrlUtil.BANNER)
    Observable<BaseModel<List<GetHomebannerRes>>> getHomeInfo(

    );
    /**
     * 首页产品分类
     */
    @GET(UrlUtil.HOME_PAGE)
    Observable<BaseModel<List<GetHomePageRes>>> getHomepage(

    );
    /**
     * 获取全部产品
     */
    @GET(UrlUtil.ALL_PRODUCTTS)
    Observable<BaseModel<List<WholeInfoRes>>> getallhome(

    );

    /**
     * 获取全部产品
     */
    @GET(UrlUtil.ALL_PRODUCTTS)
    Observable<BaseModel<List<WholeInfoRes>>> getlargeamount(
            @Query("article_id") Integer article_id
    );

    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST(UrlUtil.GET_CODE)
    Observable<BaseModel<Param>> getiphonecode(
            @Field("mobile") String mobile,
            @Field("sign") String sign
    );

    /**
     * 用户登录
     */
    @FormUrlEncoded
    @POST(UrlUtil.LOGIN_USER)
    Observable<BaseModel<UserinfoRes>> reloadlogin(
            @Field("mobile") String mobile,
            @Field("sign") String sign,
            @Field("verifyCode") String verifyCode,
            @Field("qudao") String qudao
    );

    /**
     * 退出登录
     */
    @GET(UrlUtil.EXIT_LOGON)
    Observable<BaseModel<List<String>>> exitlogon(
            @Query("token") String token
    );

    /**
     * 用户中心获取用户个人信息
     */
    @FormUrlEncoded
    @POST(UrlUtil.GET_USER)
    Observable<BaseModel<GetUserRes>> getuser(
            @Field("token") String token,
            @Field("uid") Integer uid
    );

    /**
     * 分类页贷款类型
     */
    @FormUrlEncoded
    @POST(UrlUtil.TYPE_OF_LOAN)
    Observable<BaseModel<List<TypeofloanRes>>> typeofloan(
            @Field("token") String token,
            @Field("uid") Integer uid
    );

    /**
     *  分类页贷款金额产品/贷款类型产品
     */
    @FormUrlEncoded
    @POST(UrlUtil.CLASSIFICATION_PAGE)
    Observable<BaseModel<List<WholeInfoRes>>> classificationpage(
            @Field("uid") Integer uid,
            @Field("token") String token,
            @Field("d_id") String d_id,
            @Field("a_id") String a_id
    );
//=============================================华丽的分割线====================================================
    /**
     * 获取
     */
    @GET(UrlUtil.JIZ_ALL)
    Observable<BaseModel<List<JIZAllRes>>> jizgetHomeInfo(
            @Query("uid") Integer uid,
            @Query("type") Integer type
    );

    /**
     * 添加
     */
    @GET(UrlUtil.JIZ_ALL_PRODUCTTS)
    Observable<BaseModel<JIZStingsRes>> jizaddmoney(
            @Query("uid") Integer uid,
            @Query("budget") String budget
    );

    /**
     * tjia
     */
    @GET(UrlUtil.JIZ_ADD_ALL)
    Observable<BaseModel<JIZStingsRes>> jizaddall(
            @Query("uid") Integer uid,
            @Query("type") Integer type ,
            @Query("aclass") Integer aclass ,
            @Query("money") String money
    );

    /**
     * 三个
     */
    @GET(UrlUtil.JIZ_ALL_MONEY)
    Observable<BaseModel<JIZSangeRes>> jizallmoney(
            @Query("uid") Integer uid
    );

    /**
     * 用户登录
     */
    @GET(UrlUtil.JIZ_LOGIN_USER)
    Observable<BaseModel<JIZUserRes>> jizreloadlogin(
            @Query("name") String name,
            @Query("passwd") String passwd
    );

    /**
     * 退出登录
     */
    @GET(UrlUtil.JIZ_EXIT_LOGON)
    Observable<BaseModel<JIZTuiRes>> jizexitlogon(

    );

    /**
     * 统计图
     */
    @GET(UrlUtil.JIZ_TJITU)
    Observable<BaseModel<JIZTunjiRes>> jizalltjt(
            @Query("uid") Integer uid
    );
}
