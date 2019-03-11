package com.zhwy.app.beans;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.beans
 * @ClassName: TabEntity
 * @Description: java类作用描述 ：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class TabEntity implements CustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
