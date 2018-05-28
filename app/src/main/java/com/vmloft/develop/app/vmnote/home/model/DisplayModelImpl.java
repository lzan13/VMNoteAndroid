package com.vmloft.develop.app.vmnote.home.model;

import com.vmloft.develop.app.vmnote.app.Callback;
import com.vmloft.develop.app.vmnote.bean.BaseResult;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.common.api.NetApi;
import com.vmloft.develop.app.vmnote.common.api.NoteApi;
import com.vmloft.develop.app.vmnote.common.db.DBManager;
import com.vmloft.develop.app.vmnote.home.MainContract.IDisplayModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Created by lzan13 on 2018/4/25.
 * 笔记列表数据操作实现类
 */
public class DisplayModelImpl implements IDisplayModel {

    @Override public void moveToTrash(List<Note> list, final Callback callback) {
        Observable<Note> observable = Observable.fromIterable(list);
        observable.flatMap(new Function<Note, Observable<BaseResult<Note>>>() {
            @Override public Observable<BaseResult<Note>> apply(Note note) throws Exception {
                return NoteApi.getInstance().noteApi().addNoteToTrash(note.getId());
            }
        })
            .map(new Function<BaseResult<Note>, Note>() {
                @Override public Note apply(BaseResult<Note> result) throws Exception {
                    return result.getData();
                }
            })
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<List<Note>>() {

                @Override public void accept(List<Note> list) throws Exception {
                    callback.onDone(list);
                }
            }, new Consumer<Throwable>() {
                @Override public void accept(Throwable throwable) {
                    NetApi.getInstance().parseThrowable(throwable, callback);
                }
            });
    }

    /**
     * 加载笔记列表
     */
    @Override public List<Note> loadAllNote() {
        return DBManager.getInstance().loadAllNote();
    }
}
