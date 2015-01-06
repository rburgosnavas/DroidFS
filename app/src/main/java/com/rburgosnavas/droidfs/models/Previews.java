package com.rburgosnavas.droidfs.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rburgosnavas on 10/24/14.
 */
public class Previews {
    @SerializedName("preview-lq-mp3")
    private String previewLqMp3;

    @SerializedName("preview-hq-mp3")
    private String previewHqMp3;

    @SerializedName("preview-lq-ogg")
    private String previewLqOgg;

    @SerializedName("preview-hq-ogg")
    private String previewHqOgg;

    public Previews() { }

    public String getPreviewLqMp3() {
        return previewLqMp3;
    }

    public String getPreviewHqMp3() {
        return previewHqMp3;
    }

    public String getPreviewLqOgg() {
        return previewLqOgg;
    }

    public String getPreviewHqOgg() {
        return previewHqOgg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Previews)) return false;

        Previews previews = (Previews) o;

        if (!previewHqMp3.equals(previews.previewHqMp3)) return false;
        if (!previewHqOgg.equals(previews.previewHqOgg)) return false;
        if (!previewLqMp3.equals(previews.previewLqMp3)) return false;
        if (!previewLqOgg.equals(previews.previewLqOgg)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = previewLqMp3.hashCode();
        result = 31 * result + previewHqMp3.hashCode();
        result = 31 * result + previewLqOgg.hashCode();
        result = 31 * result + previewHqOgg.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Previews{" +
                "previewLqMp3='" + previewLqMp3 + '\'' +
                ", previewHqMp3='" + previewHqMp3 + '\'' +
                ", previewLqOgg='" + previewLqOgg + '\'' +
                ", previewHqOgg='" + previewHqOgg + '\'' +
                '}';
    }
}
