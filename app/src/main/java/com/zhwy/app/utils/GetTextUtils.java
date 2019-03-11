package com.zhwy.app.utils;

import android.widget.TextView;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.utils
 * @ClassName: GetTextUtils
 * @Description: java类作用描述 ：
 * @CreateDate: 2019/3/11 12:36
 * @UpdateUser: 更新者：
 * @UpdateDate: 2019/3/11 12:36
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class GetTextUtils {
    public static String getText(TextView textView){
        return textView.getText().toString().trim();
    }
}
