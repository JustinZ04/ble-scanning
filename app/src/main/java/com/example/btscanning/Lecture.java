package com.example.btscanning;

import java.util.Date;
import android.os.ParcelUuid;

public class Lecture {

    public int id;
    public int class_id;
    public Date date;
    public ParcelUuid UUID;

    public String classCode;
    public String className;

    public Lecture(int id, int class_id, Date date, ParcelUuid UUID, String classCode, String className) {
        this.id = id;
        this.class_id = class_id;
        this.date = date;
        this.UUID = UUID;
        this.classCode = classCode;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public int getClass_id() {
        return class_id;
    }

    public Date getDate() {
        return date;
    }

    public ParcelUuid getUUID() {
        return UUID;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getClassName() {
        return className;
    }

}
