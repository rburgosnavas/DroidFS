package com.rburgosnavas.droidfs.models;

import java.util.List;

public class Result<T> {
    private int count;
    private String previous;
    private String next;
    private List<T> results;

    public Result() { }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;

        Result result = (Result) o;

        if (count != result.count) return false;
        if (next != null ? !next.equals(result.next) : result.next != null) return false;
        if (previous != null ? !previous.equals(result.previous) : result.previous != null)
            return false;
        if (results != null ? !results.equals(result.results) : result.results != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (previous != null ? previous.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "count=" + count +
                ", previous='" + previous + '\'' +
                ", next='" + next + '\'' +
                ", results=" + results +
                '}';
    }
}
