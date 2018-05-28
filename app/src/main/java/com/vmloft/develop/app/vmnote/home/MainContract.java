package com.vmloft.develop.app.vmnote.home;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.app.base.BPresenter;
import com.vmloft.develop.app.vmnote.bean.Account;
import com.vmloft.develop.app.vmnote.bean.Category;
import com.vmloft.develop.app.vmnote.bean.Note;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/26.
 * Home 契约类，包含主机面需要的 MVP 层的接口定义
 */
public class MainContract {

    /**
     * -------------------- 主界面 MVP 接口定义 --------------------
     */
    public interface IMainModel {
        /**
         * 同步账户信息
         */
        void syncAccount(Callback callback);

        /**
         * 同步笔记
         */
        void syncData(Callback callback);

        /**
         * 同步本地数据到服务器
         */
        void syncLocalToServer(Callback callback);
    }

    public interface IMainView {
        void loadAccountDone(Account entity);

        /**
         * 同步完成
         */
        void syncDataDone();

        /**
         * 同步出错
         */
        void syncDataError(int code, String desc);
    }

    public static abstract class IMainPresenter<V> extends BPresenter<V> {
        public abstract void loadAccount();

        /**
         * 同步远端数据到本地
         */
        public abstract void syncData();

        /**
         * 同步本地数据到服务器
         */
        public abstract void syncLocalToServer();
    }

    /**
     * -------------------- 分类列表展示界面 MVP 接口定义 --------------------
     */

    public interface ICategoryModel {
        /**
         * 加载分类列表
         */
        List<Category> loadAllCategory();
    }

    public interface ICategoryView {
        /**
         * 加载数据完成
         */
        void loadCategoryDone(List<Category> list);
    }

    public static abstract class ICategoryPresenter<V> extends BPresenter<V> {
        public abstract void onLoadAllCategory();
    }

    /**
     * -------------------- 数据列表展示界面 MVP 接口定义 --------------------
     */
    public interface IDisplayModel {
        /**
         * 移动笔记到回收站
         */
        void moveToTrash(List<Note> list, Callback callback);

        /**
         * 加载笔记列表
         */
        List<Note> loadAllNote();
    }

    public interface IDisplayView {
        /**
         * 加载数据完成
         */
        void loadNoteDone(List<Note> list);
    }

    public static abstract class IDisplayPresenter<V> extends BPresenter<V> {
        /**
         * 移动笔记到回收站
         */
        public abstract void onMoveToTrash(List<Note> list);

        /**
         * 加载全部笔记
         */
        public abstract void onLoadAllNote();
    }

    /**
     * -------------------- 数据列表展示界面 MVP 接口定义 --------------------
     */
    public interface ITrashModel {
        List<Note> loadTrashNote();
    }

    public interface ITrashView {
        void loadTrashNoteDone(List<Note> list);
    }

    public static abstract class ITrashPresenter<V> extends BPresenter<V> {
        public abstract void onLoadTrashNote();
    }
}
