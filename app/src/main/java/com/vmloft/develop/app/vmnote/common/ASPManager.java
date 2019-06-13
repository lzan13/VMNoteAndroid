package com.vmloft.develop.app.vmnote.common;

import com.vmloft.develop.library.tools.utils.VMSPUtil;
import com.vmloft.develop.library.tools.utils.VMSystem;

/**
 * Created by lzan13 on 2018/4/13.
 * SharePreference 操作管理类
 */
public class ASPManager {

    private final String KEY_RUN_VERSION = "key_run_version";
    private final String KEY_HISTORY_USER = "key_history_user";
    private final String KEY_CURRENT_USER = "key_current_user";
    private final String KEY_SYNC_TIME = "key_sync_time";
    private final String KEY_NIGHT_THEME = "key_night_theme";


    /**
     * 私有构造
     */
    private ASPManager() {
    }

    /**
     * 内部类实现单例模式
     */
    private static class InnerHolder {
        private static final ASPManager INSTANCE = new ASPManager();
    }

    /**
     * 获取的实例
     */
    public static final ASPManager getInstance() {
        return InnerHolder.INSTANCE;
    }

    /**
     * 夜间模式
     */
    public boolean isNight() {
        return (boolean) VMSPUtil.get(KEY_NIGHT_THEME, false);
    }

    public void putNight(boolean isNight) {
        VMSPUtil.put(KEY_NIGHT_THEME, isNight);
    }

    /**
     * 同步时间戳的获取
     */
    public String getSyncTime() {
        return (String) VMSPUtil.get(KEY_SYNC_TIME, "");
    }

    public void putSyncTime(String syncKey) {
        VMSPUtil.put(KEY_SYNC_TIME, syncKey);
    }

    /**
     * 当前运行版本
     */
    public long getRunVersion() {
        return (long) VMSPUtil.get(KEY_RUN_VERSION, 0l);
    }

    public void putRunVersion(long version) {
        VMSPUtil.put(KEY_RUN_VERSION, version);
    }

    /**
     * 判断启动时是否需要展示引导界面
     */
    public boolean isShowGuide() {
        // 上次运行保存的版本号
        long runVersion = getRunVersion();
        // 程序当前版本
        long version = VMSystem.getVersionCode();
        if (version > runVersion) {
            return true;
        }
        return false;
    }

    /**
     * 设置 Guide 状态
     */
    public void setGuideState() {
        // 保存新的版本
        putRunVersion(VMSystem.getVersionCode());
    }

    /**
     * 保存上一个登录的用户
     *
     * @param user 上一个登录的用户信息
     */
    public void putHistoryUser(String user) {
        VMSPUtil.put(KEY_HISTORY_USER, user);
    }

    /**
     * 获取上一个登录的用户
     *
     * @return 如果为空，说明没有登录
     */
    public String getHistoryUser() {
        return (String) VMSPUtil.get(KEY_HISTORY_USER, "");
    }
}

