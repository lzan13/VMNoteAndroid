package com.vmloft.develop.app.vmnote.common.router;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lzan13 on 2018/4/24.
 * Activity 间用于传递参数的可序列化对象，这里参数命名形式参考 Handler 的 message 对象
 */
public class NavParams implements Parcelable {
    // 下一步要做什么
    public int what = -1;
    public int arg0;
    public int arg1;
    public String str0;
    public String str1;
    public List<String> strList;

    public static final Creator<NavParams> CREATOR = new Creator<NavParams>() {
        @Override
        public NavParams createFromParcel(Parcel in) {
            NavParams p = new NavParams();
            p.what = in.readInt();
            p.arg0 = in.readInt();
            p.arg1 = in.readInt();
            p.str0 = in.readString();
            p.str1 = in.readString();
            p.strList = in.createStringArrayList();
            return p;
        }

        @Override
        public NavParams[] newArray(int size) {
            return new NavParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(what);
        dest.writeInt(arg0);
        dest.writeInt(arg1);
        dest.writeString(str0);
        dest.writeString(str1);
        dest.writeStringList(strList);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

