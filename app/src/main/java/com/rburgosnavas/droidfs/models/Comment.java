package com.rburgosnavas.droidfs.models;

public class Comment {
    private String username;
    private String comment;
    private String created;

    public Comment() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment1 = (Comment) o;

        if (comment != null ? !comment.equals(comment1.comment) : comment1.comment != null)
            return false;
        if (created != null ? !created.equals(comment1.created) : comment1.created != null)
            return false;
        if (username != null ? !username.equals(comment1.username) : comment1.username != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "username='" + username + '\'' +
                ", comment='" + comment + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
