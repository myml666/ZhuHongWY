package com.zhwy.app;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app
 * @ClassName: MyApplication
 * @Description: java类作用描述 ：
 * @Author: 作者名：lml
 * @CreateDate: 2019/3/11 10:24
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/3/11 10:24
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"LYdTMhgI60zonQzucEG0nqq4-gzGzoHsz","r0OtsqelEfJXyafMABWBwpcr");
    }
}
