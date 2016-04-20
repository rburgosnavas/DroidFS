package com.rburgosnavas.droidfs.api.models;

import com.google.gson.annotations.SerializedName;

public class User {
    private String url;
    @SerializedName("username")
    private String userName;
    private String about;
    @SerializedName("home_page")
    private String homePage;
    private Avatar avatar;
    @SerializedName("date_joined")
    private String dateJoined;
    @SerializedName("num_sound")
    private int numSounds;
    private String sounds;
    @SerializedName("num_packs")
    private int numPacks;
    private String packs;
    @SerializedName("num_posts")
    private int numPosts;
    @SerializedName("num_comments")
    private int numComments;
    @SerializedName("bookmark_categories")
    private String bookmarkCategories;
    private String email;
    @SerializedName("unique_id")
    private int uniqueId;

    public User() { }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
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

    public int getNumPacks() {
        return numPacks;
    }

    public void setNumPacks(int numPacks) {
        this.numPacks = numPacks;
    }

    public String getPacks() {
        return packs;
    }

    public void setPacks(String packs) {
        this.packs = packs;
    }

    public int getNumPosts() {
        return numPosts;
    }

    public void setNumPosts(int numPosts) {
        this.numPosts = numPosts;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public String getBookmarkCategories() {
        return bookmarkCategories;
    }

    public void setBookmarkCategories(String bookmarkCategories) {
        this.bookmarkCategories = bookmarkCategories;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (numComments != user.numComments) return false;
        if (numPacks != user.numPacks) return false;
        if (numPosts != user.numPosts) return false;
        if (numSounds != user.numSounds) return false;
        if (uniqueId != user.uniqueId) return false;
        if (about != null ? !about.equals(user.about) : user.about != null) return false;
        if (avatar != null ? !avatar.equals(user.avatar) : user.avatar != null) return false;
        if (bookmarkCategories != null ? !bookmarkCategories.equals(user.bookmarkCategories) : user.bookmarkCategories != null)
            return false;
        if (dateJoined != null ? !dateJoined.equals(user.dateJoined) : user.dateJoined != null)
            return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (homePage != null ? !homePage.equals(user.homePage) : user.homePage != null)
            return false;
        if (packs != null ? !packs.equals(user.packs) : user.packs != null) return false;
        if (sounds != null ? !sounds.equals(user.sounds) : user.sounds != null) return false;
        if (url != null ? !url.equals(user.url) : user.url != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (about != null ? about.hashCode() : 0);
        result = 31 * result + (homePage != null ? homePage.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (dateJoined != null ? dateJoined.hashCode() : 0);
        result = 31 * result + numSounds;
        result = 31 * result + (sounds != null ? sounds.hashCode() : 0);
        result = 31 * result + numPacks;
        result = 31 * result + (packs != null ? packs.hashCode() : 0);
        result = 31 * result + numPosts;
        result = 31 * result + numComments;
        result = 31 * result + (bookmarkCategories != null ? bookmarkCategories.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + uniqueId;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", about='" + about + '\'' +
                ", homePage='" + homePage + '\'' +
                ", avatar=" + avatar +
                ", dateJoined='" + dateJoined + '\'' +
                ", numSounds=" + numSounds +
                ", sounds='" + sounds + '\'' +
                ", numPacks=" + numPacks +
                ", packs='" + packs + '\'' +
                ", numPosts=" + numPosts +
                ", numComments=" + numComments +
                ", bookmarkCategories='" + bookmarkCategories + '\'' +
                ", email='" + email + '\'' +
                ", uniqueId=" + uniqueId +
                '}';
    }

    public static class Avatar {
        String small;
        String large;
        String medium;

        public Avatar() { }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Avatar)) return false;

            Avatar avatar = (Avatar) o;

            if (large != null ? !large.equals(avatar.large) : avatar.large != null) return false;
            if (medium != null ? !medium.equals(avatar.medium) : avatar.medium != null)
                return false;
            if (small != null ? !small.equals(avatar.small) : avatar.small != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = small != null ? small.hashCode() : 0;
            result = 31 * result + (large != null ? large.hashCode() : 0);
            result = 31 * result + (medium != null ? medium.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Avatar{" +
                    "small='" + small + '\'' +
                    ", large='" + large + '\'' +
                    ", medium='" + medium + '\'' +
                    '}';
        }
    }
}
