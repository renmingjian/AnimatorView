// IMyAidlInterface.aidl
package com.erge.animatorview;
// Declare any non-default types here with import statements
import com.erge.animatorview.MyPerson;
interface IMyAidlInterface {

    List<MyPerson> add(in MyPerson person);

}
