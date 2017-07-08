package com.example.mahendran.teacherspet.StudentDatabase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mahendran on 03-06-2017.
 */

public class StudentValues implements Serializable {

    public String studentName;
    public String emailID;
    public String studentph;
    public String parentph;
    public String address;
    public String id;
    public Map<String,String> map=new HashMap<String,String>();
}
