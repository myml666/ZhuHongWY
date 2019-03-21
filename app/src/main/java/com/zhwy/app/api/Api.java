package com.zhwy.app.api;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @ProjectName: OcrInterface
 * @Package: com.itfitness.ocrinterface.api
 * @ClassName: Api
 * @Description: java类作用描述 ：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public interface Api {
    //POST请求
    @FormUrlEncoded
    @POST("ocridcard")
    Observable<JsonObject> ocrIdcard(@Field("base64img") String incTime);
    //GET请求
}
