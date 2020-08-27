// IMyAidlInterface.aidl
package com.erge.animatorview;
import com.erge.animatorview.MyPerson;
interface IMyAidlInterface {

    List<MyPerson> add(in MyPerson person, int num);

}
