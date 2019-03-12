package com.zhwy.app.utils;

import com.blankj.utilcode.util.SPUtils;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.utils
 * @ClassName: ValuesUtils
 * @Description: java类作用描述 ：操作存储在本地的值
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class ValuesUtils {
    public static final String IDENTITY = "identity";//身份
    public static final String IDENTITY_YZ = "yz";//业主身份
    public static final String IDENTITY_WY = "wy";//物业身份
    public static final int REQUEST_CODE_CHOOSE = 1;//选择图片的状态码
    /**
     * 获取身份
     * @return
     */
    public static String getIdentity(){
        return SPUtils.getInstance().getString(IDENTITY, IDENTITY_YZ);
    }

    /**
     * 设置身份
     * @param identity
     */
    public static void putIdentity(String identity){
        SPUtils.getInstance().put(IDENTITY,identity);
    }
}
