package com.example.btscanning;

public class Class {

    public String course_id;
    public String name;
    public String startTime;
    public String endTime;

    public Class(String course_id, String name, String startTime, String endTime) {
        this.course_id = course_id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
