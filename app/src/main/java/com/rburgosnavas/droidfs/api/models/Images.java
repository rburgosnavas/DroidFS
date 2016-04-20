package com.rburgosnavas.droidfs.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rburgosnavas on 10/24/14.
 */
public class Images {
    @SerializedName("spectral_l")
    private String spectralL;

    @SerializedName("spectral_m")
    private String spectralM;

    @SerializedName("waveform_l")
    private String waveformL;

    @SerializedName("waveform_m")
    private String waveformM;

    public Images() { }

    public String getSpectralL() {
        return spectralL;
    }

    public String getSpectralM() {
        return spectralM;
    }

    public String getWaveformL() {
        return waveformL;
    }

    public String getWaveformM() {
        return waveformM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Images)) return false;

        Images images = (Images) o;

        if (!spectralL.equals(images.spectralL)) return false;
        if (!spectralM.equals(images.spectralM)) return false;
        if (!waveformL.equals(images.waveformL)) return false;
        if (!waveformM.equals(images.waveformM)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = spectralL.hashCode();
        result = 31 * result + spectralM.hashCode();
        result = 31 * result + waveformL.hashCode();
        result = 31 * result + waveformM.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Images{" +
                "spectralL='" + spectralL + '\'' +
                ", spectralM='" + spectralM + '\'' +
                ", waveformL='" + waveformL + '\'' +
                ", waveformM='" + waveformM + '\'' +
                '}';
    }
}
