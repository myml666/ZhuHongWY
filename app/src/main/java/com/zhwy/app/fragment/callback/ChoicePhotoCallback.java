package com.zhwy.app.fragment.callback;

import java.io.File;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.fragment.callback
 * @ClassName: ChoicePhotoCallback
 * @Description: java类作用描述 ：我的界面选择头像的回调函数
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public interface ChoicePhotoCallback {
    void onPhotoChoice(File file);
}
