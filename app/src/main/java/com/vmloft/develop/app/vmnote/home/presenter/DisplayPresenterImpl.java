package com.vmloft.develop.app.vmnote.home.presenter;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.home.model.DisplayModelImpl;
import com.vmloft.develop.app.vmnote.home.MainContract.IDisplayModel;
import com.vmloft.develop.app.vmnote.home.MainContract.IDisplayView;
import com.vmloft.develop.app.vmnote.home.MainContract.IDisplayPresenter;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/25.
 * 笔记列表逻辑处理实现类
 */
public class DisplayPresenterImpl extends IDisplayPresenter<IDisplayView> {

    private IDisplayModel displayModel;

    public DisplayPresenterImpl() {
        displayModel = new DisplayModelImpl();
    }

    @Override public void onMoveToTrash(List<Note> list) {
        displayModel.moveToTrash(list, new ACallback() {
            @Override public void onSuccess(Object object) {
                onLoadAllNote();
            }

            @Override public void onError(int code, String desc) {
            }
        });
    }

    @Override public void onLoadAllNote() {
        List<Note> list = displayModel.loadAllNote();
        obtainView().loadNoteDone(list);
    }
}
