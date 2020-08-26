package com.erge.animatorview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mj on 2020/8/26 15:54
 */
public class MyPerson implements Parcelable {

    private String name;
    private int age;

    public MyPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected MyPerson(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<MyPerson> CREATOR = new Creator<MyPerson>() {
        @Override
        public MyPerson createFromParcel(Parcel in) {
            return new MyPerson(in);
        }

        @Override
        public MyPerson[] newArray(int size) {
            return new MyPerson[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
