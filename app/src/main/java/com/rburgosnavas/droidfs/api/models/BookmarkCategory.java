package com.rburgosnavas.droidfs.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rburgosnavas on 11/11/14.
 */
public class BookmarkCategory {
    String url;
    String name;
    @SerializedName("num_sounds")
    int numSounds;
    String sounds;

    public BookmarkCategory() { }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookmarkCategory)) return false;

        BookmarkCategory bookmarkCategory = (BookmarkCategory) o;

        if (numSounds != bookmarkCategory.numSounds) return false;
        if (name != null ? !name.equals(bookmarkCategory.name) : bookmarkCategory.name != null) return false;
        if (sounds != null ? !sounds.equals(bookmarkCategory.sounds) : bookmarkCategory.sounds != null)
            return false;
        if (url != null ? !url.equals(bookmarkCategory.url) : bookmarkCategory.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + numSounds;
        result = 31 * result + (sounds != null ? sounds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", numSounds=" + numSounds +
                ", sounds='" + sounds + '\'' +
                '}';
    }
}
