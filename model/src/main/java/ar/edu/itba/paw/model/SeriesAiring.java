package ar.edu.itba.paw.model;

import java.util.Date;

public class SeriesAiring {

    private DayOfWeek dayOfWeek;
    private Date time;
    private String country;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static enum DayOfWeek {
        MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4), SATURDAY(5), SUNDAY(6);

        private int number;

        DayOfWeek(int number) {
            this.number = number;
        }

    }

}
