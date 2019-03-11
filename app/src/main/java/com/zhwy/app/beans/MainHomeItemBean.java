package com.zhwy.app.beans;

/**
 * @ProjectName: ZhuHongWY
 * @Package: com.zhwy.app.beans
 * @ClassName: MainHomeItemBean
 * @Description: java类作用描述 ：主界面菜单Item
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */

public class MainHomeItemBean {
    private String title;//主标题
    private String ftitle;//副标题
    private int icon;//图标

    public MainHomeItemBean(String title, String ftitle, int icon) {
        this.title = title;
        this.ftitle = ftitle;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFtitle() {
        return ftitle;
    }

    public void setFtitle(String ftitle) {
        this.ftitle = ftitle;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
