package com.rburgosnavas.droidfs.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarSound {
    private Integer id;
    private String name;
    private List<String> tags;
    private String license;
    private String username;
    @SerializedName("distance_to_target")
    private String distanceToTarget;

    public SimilarSound() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDistanceToTarget() {
        return distanceToTarget;
    }

    public void setDistanceToTarget(String distanceToTarget) {
        this.distanceToTarget = distanceToTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimilarSound)) return false;

        SimilarSound that = (SimilarSound) o;

        if (distanceToTarget != null ? !distanceToTarget.equals(that.distanceToTarget) : that.distanceToTarget != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (license != null ? !license.equals(that.license) : that.license != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (license != null ? license.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (distanceToTarget != null ? distanceToTarget.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SimilarSound{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", license='" + license + '\'' +
                ", username='" + username + '\'' +
                ", distanceToTarget='" + distanceToTarget + '\'' +
                '}';
    }
}
