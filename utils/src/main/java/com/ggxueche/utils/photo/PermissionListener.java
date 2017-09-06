package com.ggxueche.utils.photo;

import java.util.List;

/**
 * 申请权限的回掉接口
 * Created by yaofclc on 2017/5/9.
 */

public interface PermissionListener {
    /**
     * 允许
     */
    void onGranted();

    /**
     * 拒绝
     * @param deniedPermissions
     */
    void onDenied(List<String> deniedPermissions);
}
