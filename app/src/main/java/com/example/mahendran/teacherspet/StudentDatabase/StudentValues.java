package com.example.mahendran.teacherspet.StudentDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mahendran on 03-06-2017.
 */

public class StudentValues {

    public String studentName;
    public String studentph;
    public String parentph;
    public String address;
    public String CGPA;
    public String rank;
    public String password;
    public String id;
    public Map<String,String> map=new HashMap<String,String>();

    public void setStudentName(String name)
    {
        studentName=name;
    }
    public void setStudentph(String ph)
    {
        studentph=ph;
    }
    public void setParentph(String ph)
    {
        parentph=ph;
    }
    public void setAddress(String address1)
    {
        address=address1;
    }

    public void setRank(String rank1)
    {
        rank=rank1;
    }
    public void setPassword(String password1)
    {
        password=password1;
    }
    public void setId(String id1)
    {
        id=id1;
    }
}
