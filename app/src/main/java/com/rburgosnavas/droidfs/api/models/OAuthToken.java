package com.rburgosnavas.droidfs.api.models;

import com.google.gson.annotations.SerializedName;

public class OAuthToken {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("scope")
    private String scope;

    @SerializedName("expires_in")
    private long expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    public OAuthToken() { }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OAuthToken)) return false;

        OAuthToken that = (OAuthToken) o;

        if (expiresIn != that.expiresIn) return false;
        if (!accessToken.equals(that.accessToken)) return false;
        if (!refreshToken.equals(that.refreshToken)) return false;
        if (!scope.equals(that.scope)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessToken.hashCode();
        result = 31 * result + scope.hashCode();
        result = 31 * result + (int) (expiresIn ^ (expiresIn >>> 32));
        result = 31 * result + refreshToken.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OAuthToken{" +
                "accessToken='" + accessToken + '\'' +
                ", scope='" + scope + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
