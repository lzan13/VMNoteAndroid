package com.vmloft.develop.app.vmnote.ui.home.model;

import com.vmloft.develop.app.vmnote.common.ACallback;
import com.vmloft.develop.app.vmnote.bean.Note;
import com.vmloft.develop.app.vmnote.ui.home.MainContract.IDisplayModel;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/25.
 * 笔记列表数据操作实现类
 */
public class DisplayModelImpl implements IDisplayModel {

    @Override public void moveToTrash(List<Note> list, final ACallback callback) {
//        Observable<Note> observable = Observable.fromIterable(list);
//        observable.flatMap(new Function<Note, Observable<BaseResult<Note>>>() {
//            @Override public Observable<BaseResult<Note>> apply(Note note) throws Exception {
//                return NoteApi.getInstance().noteApi().addNoteToTrash(note.getId());
//            }
//        })
//            .map(new Function<BaseResult<Note>, Note>() {
//                @Override public Note apply(BaseResult<Note> result) throws Exception {
//                    return result.getData();
//                }
//            })
//            .toList()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Consumer<List<Note>>() {
//
//                @Override public void accept(List<Note> list) throws Exception {
//                    callback.onDone(list);
//                }
//            }, new Consumer<Throwable>() {
//                @Override public void accept(Throwable throwable) {
//                    NetApi.getInstance().parseThrowable(throwable, callback);
//                }
//            });
    }

    /**
     * 加载笔记列表
     */
    @Override public List<Note> loadAllNote() {
        return null;
    }
}
