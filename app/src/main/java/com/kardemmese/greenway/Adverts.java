package com.kardemmese.greenway;

public class Adverts {
    String Mname;
    String Pname;

    public Adverts(String mname, String pname) {
        Mname = mname;
        Pname = pname;
    }

    public String getMname() {
        return Mname;
    }

    public void setMname(String mname) {
        Mname = mname;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }
}
