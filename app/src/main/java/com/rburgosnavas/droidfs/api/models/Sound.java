package com.rburgosnavas.droidfs.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sound {
    private Integer id;
    private String url;
    private String name;
    private List<String> tags;
    private String description;
    private String geotag;
    private String created;
    private String license;
    private String type;
    private Integer channels;
    private Integer filesize;
    private Integer bitrate;
    private Integer bitdepth;
    private Double duration;
    private Double samplerate;
    private String username;
    private String pack;
    private String download;
    private String bookmark;
    private Previews previews;
    private Images images;
    @SerializedName("num_downloads")
    private Integer numDownloads;
    @SerializedName("avg_rating")
    private Double avgRating;
    @SerializedName("num_ratings")
    private Double numRatings;
    private String rate;
    private String comments;
    @SerializedName("num_comments")
    private Integer numComments;
    private String comment;
    @SerializedName("similar_sounds")
    private String similarSounds;
    private String analysis;
    @SerializedName("analysis_frames")
    private String analysisFrames;
    @SerializedName("analysis_stats")
    private String analysisStats;

    public Sound() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

    public Integer getFilesize() {
        return filesize;
    }

    public void setFilesize(Integer filesize) {
        this.filesize = filesize;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getBitdepth() {
        return bitdepth;
    }

    public void setBitdepth(Integer bitdepth) {
        this.bitdepth = bitdepth;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getSamplerate() {
        return samplerate;
    }

    public void setSamplerate(Double samplerate) {
        this.samplerate = samplerate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public Previews getPreviews() {
        return previews;
    }

    public void setPreviews(Previews previews) {
        this.previews = previews;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Integer getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(Integer numDownloads) {
        this.numDownloads = numDownloads;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Double numRatings) {
        this.numRatings = numRatings;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSimilarSounds() {
        return similarSounds;
    }

    public void setSimilarSounds(String similarSounds) {
        this.similarSounds = similarSounds;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnalysisFrames() {
        return analysisFrames;
    }

    public void setAnalysisFrames(String analysisFrames) {
        this.analysisFrames = analysisFrames;
    }

    public String getAnalysisStats() {
        return analysisStats;
    }

    public void setAnalysisStats(String analysisStats) {
        this.analysisStats = analysisStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sound)) return false;

        Sound sound = (Sound) o;

        if (!analysis.equals(sound.analysis)) return false;
        if (!analysisFrames.equals(sound.analysisFrames)) return false;
        if (!analysisStats.equals(sound.analysisStats)) return false;
        if (!avgRating.equals(sound.avgRating)) return false;
        if (!bitdepth.equals(sound.bitdepth)) return false;
        if (!bitrate.equals(sound.bitrate)) return false;
        if (!bookmark.equals(sound.bookmark)) return false;
        if (!channels.equals(sound.channels)) return false;
        if (!comment.equals(sound.comment)) return false;
        if (!comments.equals(sound.comments)) return false;
        if (!created.equals(sound.created)) return false;
        if (!description.equals(sound.description)) return false;
        if (!download.equals(sound.download)) return false;
        if (!duration.equals(sound.duration)) return false;
        if (!filesize.equals(sound.filesize)) return false;
        if (!geotag.equals(sound.geotag)) return false;
        if (!id.equals(sound.id)) return false;
        if (!images.equals(sound.images)) return false;
        if (!license.equals(sound.license)) return false;
        if (!name.equals(sound.name)) return false;
        if (!numComments.equals(sound.numComments)) return false;
        if (!numDownloads.equals(sound.numDownloads)) return false;
        if (!numRatings.equals(sound.numRatings)) return false;
        if (!pack.equals(sound.pack)) return false;
        if (!previews.equals(sound.previews)) return false;
        if (!rate.equals(sound.rate)) return false;
        if (!samplerate.equals(sound.samplerate)) return false;
        if (!similarSounds.equals(sound.similarSounds)) return false;
        if (!tags.equals(sound.tags)) return false;
        if (!type.equals(sound.type)) return false;
        if (!url.equals(sound.url)) return false;
        if (!username.equals(sound.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + geotag.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + license.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + channels.hashCode();
        result = 31 * result + filesize.hashCode();
        result = 31 * result + bitrate.hashCode();
        result = 31 * result + bitdepth.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + samplerate.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + pack.hashCode();
        result = 31 * result + download.hashCode();
        result = 31 * result + bookmark.hashCode();
        result = 31 * result + previews.hashCode();
        result = 31 * result + images.hashCode();
        result = 31 * result + numDownloads.hashCode();
        result = 31 * result + avgRating.hashCode();
        result = 31 * result + numRatings.hashCode();
        result = 31 * result + rate.hashCode();
        result = 31 * result + comments.hashCode();
        result = 31 * result + numComments.hashCode();
        result = 31 * result + comment.hashCode();
        result = 31 * result + similarSounds.hashCode();
        result = 31 * result + analysis.hashCode();
        result = 31 * result + analysisFrames.hashCode();
        result = 31 * result + analysisStats.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Sound{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                ", description='" + description + '\'' +
                ", geotag='" + geotag + '\'' +
                ", created='" + created + '\'' +
                ", license='" + license + '\'' +
                ", type='" + type + '\'' +
                ", channels=" + channels +
                ", filesize=" + filesize +
                ", bitrate=" + bitrate +
                ", bitdepth=" + bitdepth +
                ", duration=" + duration +
                ", samplerate=" + samplerate +
                ", username='" + username + '\'' +
                ", pack='" + pack + '\'' +
                ", download='" + download + '\'' +
                ", bookmark='" + bookmark + '\'' +
                ", previews=" + previews +
                ", images=" + images +
                ", numDownloads=" + numDownloads +
                ", avgRating=" + avgRating +
                ", numRatings=" + numRatings +
                ", rate='" + rate + '\'' +
                ", comments='" + comments + '\'' +
                ", numComments=" + numComments +
                ", comment='" + comment + '\'' +
                ", similarSounds='" + similarSounds + '\'' +
                ", analysis='" + analysis + '\'' +
                ", analysisFrames='" + analysisFrames + '\'' +
                ", analysisStats='" + analysisStats + '\'' +
                '}';
    }
}
