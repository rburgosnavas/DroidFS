package com.rburgosnavas.droidfs.api.models;

import com.google.gson.annotations.SerializedName;

public class Pack {
    int id;
    String url;
    String description;
    String created;
    String name;
    @SerializedName("username")
    String userName;
    @SerializedName("num_sounds")
    int numSounds;
    String sounds;
    @SerializedName("num_downloads")
    int numDownloads;

    public Pack() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNumSounds() {
        return numSounds;
    }

    public void setNumSounds(int numSounds) {
        this.numSounds = numSounds;
    }

    public String getSounds() {
        return sounds;
    }

    public void setSounds(String sounds) {
        this.sounds = sounds;
    }

    public int getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pack)) return false;

        Pack pack = (Pack) o;

        if (id != pack.id) return false;
        if (numDownloads != pack.numDownloads) return false;
        if (numSounds != pack.numSounds) return false;
        if (created != null ? !created.equals(pack.created) : pack.created != null) return false;
        if (description != null ? !description.equals(pack.description) : pack.description != null)
            return false;
        if (name != null ? !name.equals(pack.name) : pack.name != null) return false;
        if (sounds != null ? !sounds.equals(pack.sounds) : pack.sounds != null) return false;
        if (url != null ? !url.equals(pack.url) : pack.url != null) return false;
        if (userName != null ? !userName.equals(pack.userName) : pack.userName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + numSounds;
        result = 31 * result + (sounds != null ? sounds.hashCode() : 0);
        result = 31 * result + numDownloads;
        return result;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", created='" + created + '\'' +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", numSounds=" + numSounds +
                ", sounds='" + sounds + '\'' +
                ", numDownloads=" + numDownloads +
                '}';
    }
}
