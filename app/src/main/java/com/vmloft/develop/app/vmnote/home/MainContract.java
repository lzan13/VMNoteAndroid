package com.vmloft.develop.app.vmnote.home;

import com.vmloft.develop.app.vmnote.bean.AUser;
import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.base.APresenter;
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
        void loadAccount(ACallback<AUser> callback);

    }

    public interface IMainView {
        void loadAccountDone(AUser user);
    }

    public static abstract class IMainPresenter<V> extends APresenter<V> {
        /**
         * 加载账户信息
         */
        public abstract void onLoadAccount();
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

    public static abstract class ICategoryPresenter<V> extends APresenter<V> {
        public abstract void onLoadAllCategory();
    }

    /**
     * -------------------- 数据列表展示界面 MVP 接口定义 --------------------
     */
    public interface IDisplayModel {
        /**
         * 移动笔记到回收站
         */
        void moveToTrash(List<Note> list, ACallback callback);

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

    public static abstract class IDisplayPresenter<V> extends APresenter<V> {
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
     * -------------------- 回收站 MVP 接口定义 --------------------
     */
    public interface ITrashModel {
        List<Note> loadTrashNote();
    }

    public interface ITrashView {
        void loadTrashNoteDone(List<Note> list);
    }

    public static abstract class ITrashPresenter<V> extends APresenter<V> {
        public abstract void onLoadTrashNote();
    }
}
