package com.netcracker.shared;

import java.io.Serializable;
import java.util.List;

public class FetchEntriesResult implements Serializable {
    private int totalNumberOfEntries;
    private List<Book> entries;

    public FetchEntriesResult() {
    }

    public FetchEntriesResult(int totalNumberOfEntries, List<Book> entries) {
        this.totalNumberOfEntries = totalNumberOfEntries;
        this.entries = entries;
    }

    public int getTotalNumberOfEntries() {
        return totalNumberOfEntries;
    }

    public void setTotalNumberOfEntries(int totalNumberOfEntries) {
        this.totalNumberOfEntries = totalNumberOfEntries;
    }

    public List<Book> getEntries() {
        return entries;
    }

    public void setEntries(List<Book> entries) {
        this.entries = entries;
    }


    //getters,setters, constructor etc...
}
