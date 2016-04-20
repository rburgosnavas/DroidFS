package com.rburgosnavas.droidfs.api.services;

import com.rburgosnavas.droidfs.api.models.BookmarkCategory;
import com.rburgosnavas.droidfs.api.models.Comment;
import com.rburgosnavas.droidfs.api.models.OAuthToken;
import com.rburgosnavas.droidfs.api.models.Pack;
import com.rburgosnavas.droidfs.api.models.Result;
import com.rburgosnavas.droidfs.api.models.SimilarSound;
import com.rburgosnavas.droidfs.api.models.Sound;
import com.rburgosnavas.droidfs.api.models.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

public interface FreesoundApiService {
    @FormUrlEncoded
    @POST("oauth2/access_token/")
    public Observable<OAuthToken> getAccessToken(@Field("client_id") String clientId,
                                                 @Field("client_secret") String clientSecret,
                                                 @Field("grant_type") String grantType,
                                                 @Field("code") String code);

    // curl -X POST -d
    //   "client_id=YOUR_CLIENT_ID&
    //    client_secret=YOUR_CLIENT_SECRET&
    //    grant_type=refresh_token&
    //    refresh_token=REFRESH_TOKEN"
    // "https://www.freesound.org/apiv2/oauth2/access_token/"
    @FormUrlEncoded
    @POST("oauth2/access_token/")
    public Observable<OAuthToken> getRefreshToken(@Field("client_id") String clientId,
                                                  @Field("client_secret") String clientSecret,
                                                  @Field("grant_type") String grantType,
                                                  @Field("refresh_token") String refreshToken);

    // --------------------------------------------------------------------------------------------
    // Sound resources
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("sounds/{id}/")
    public Observable<Sound> getSound(@Path("id") String soundId);

    @GET("sounds/{id}/download/")
    @Streaming
    public Response downloadSound(@Path("id") String soundId);

    @GET("sounds/{id}/similar/")
    public Observable<Result<SimilarSound>> getSimilarSounds(@Path("id") String soundId);

    @GET("sounds/{id}/comment/")
    public Observable<Result<Comment>> getComments(@Path("id") String soundId);

    // TODO: GET - pending uploads

    // POST
    @Multipart
    @POST("sounds/upload/")
    public Observable<ResponseBody> uploadSound(@Part("audiofile") MultipartBody.Part audioFile,
                                                @Part("name") String name,
                                                @Part("tags") String tags,
                                                @Part("description") String description,
                                                @Part("license") String license,
                                                @Part("pack") String pack,
                                                @Part("geotag") String geoTag);

    @Multipart
    @POST("sounds/describe/")
    public Observable<ResponseBody> describeSound(@Part("uploaded_filename") String uploadedFileName,
                                                  @Part("name") String name,
                                                  @Part("tags") String tags,
                                                  @Part("description") String description,
                                                  @Part("license") String license,
                                                  @Part("pack") String pack,
                                                  @Part("geotag") String geoTag);


    @Multipart
    @POST("sounds/{id}/edit/")
    public Observable<ResponseBody> editSound(@Part("name") String name,
                                              @Part("tags") String tags,
                                              @Part("description") String description,
                                              @Part("license") String license,
                                              @Part("pack") String pack,
                                              @Part("geotag") String geoTag);

    @Multipart
    @POST("sounds/{id}/bookmark/")
    public Observable<ResponseBody> bookmarkSound(@Part("name") String name,
                                                  @Part("category") String category);

    @Multipart
    @POST("sounds/{id}/rate/")
    public Observable<ResponseBody> rateSound(@Part("rating") int rating);

    @Multipart
    @POST("sounds/{id}/comment/")
    public Observable<ResponseBody> commentSound(@Part("comment") String comment);

    // --------------------------------------------------------------------------------------------
    // User resources
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("users/{id}/")
    public Observable<User> getUser(@Path("id") String userId);

    @GET("users/{id}/sounds/")
    public Observable<Result<Sound>> getUserSounds(@Path("id") String userId);

    @GET("users/{id}/packs/")
    public Observable<Result<Pack>> getUserPacks(@Path("id") String userId);

    @GET("users/{id}/bookmark_categories/")
    public Observable<Result<BookmarkCategory>> getUserBookmarkCategories(@Path("id") String userId);

    @GET("users/{id}/bookmark_categories/{cat-id}/")
    public Observable<Result<Sound>> getUserBookmarkCategorySounds(@Path("id") String userId,
                                                                   @Path("cat-id") String categoryId);
    // --------------------------------------------------------------------------------------------
    // Pack resources
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("packs/{id}/")
    public Observable<Pack> getPack(@Path("id") String packId);

    @GET("packs/{id}/sounds/")
    public Observable<Result<Sound>> getPackSounds(@Path("id") String packId);

    @GET("packs/{id}/download/")
    @Streaming
    public Observable<Pack> downloadPack(@Path("id") String packId);


    // --------------------------------------------------------------------------------------------
    // Text search
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("search/text/")
    public Observable<Result<Sound>> getResults(@Query("query") String query,
                                                @Query("filter") String filter,
                                                @Query("fields") String fields,
                                                @Query("sort") String sort);

    // --------------------------------------------------------------------------------------------
    // Me
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("me/")
    public Observable<User> getMe();
}
