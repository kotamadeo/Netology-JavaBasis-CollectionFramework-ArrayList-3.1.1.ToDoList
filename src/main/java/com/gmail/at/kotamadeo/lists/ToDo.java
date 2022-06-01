package com.gmail.at.kotamadeo.lists;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDo {
    private LocalDateTime date;
    private String toDo;

    public ToDo(String toDo, LocalDateTime date) {
        this.toDo = toDo;
        this.date = date;
    }

    @Override
    public String toString() {
        return toDo + " нужно сделать к: " + date.getHour() + ":" + date.getMinute() + " " +
                date.format(DateTimeFormatter.ofPattern("d:MMM:yyyy")) + " г";
    }
}
