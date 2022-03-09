package com.example.myapplication;

import java.time.LocalDate;
import java.util.Date;

public class DateHandler {
    String StartDate;
    String end_date;

    public DateHandler(String StartDate, String end_date) {
        this.StartDate = StartDate;
        this.end_date = end_date;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public DateHandler() {
    }
    public String getNextDayDate()
    {
        CharSequence cs_date = getStartDate();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate mydate= LocalDate.parse(cs_date).plusDays(1);
            end_date = mydate.toString();
        }
        return end_date;
    }
    public boolean isValid(Date startdate,Date currentdate)
    {
        if(startdate.compareTo(currentdate)>0)
            return true;
        else
            return false;
    }


}
