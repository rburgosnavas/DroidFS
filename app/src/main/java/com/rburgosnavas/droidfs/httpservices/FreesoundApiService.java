package com.rburgosnavas.droidfs.httpservices;

import com.rburgosnavas.droidfs.models.BookmarkCategory;
import com.rburgosnavas.droidfs.models.Comment;
import com.rburgosnavas.droidfs.models.Pack;
import com.rburgosnavas.droidfs.models.Result;
import com.rburgosnavas.droidfs.models.SimilarSound;
import com.rburgosnavas.droidfs.models.Sound;
import com.rburgosnavas.droidfs.models.User;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Streaming;
import retrofit.mime.TypedFile;

public interface FreesoundApiService {

    // TODO: convert to callback OR add overloaded methods with callbacks

    // --------------------------------------------------------------------------------------------
    // Sound resources
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("/sounds/{id}/")
    public void getSound(@Path("id") String soundId,
                         Callback<Sound> callback);

    @GET("/sounds/{id}/download/")
    @Streaming
    public Response downloadSound(@Path("id") String soundId);

    @GET("/sounds/{id}/similar/")
    public Result<SimilarSound> getSimilarSounds(@Path("id") String soundId);

    @GET("/sounds/{id}/comment/")
    public Result<Comment> getComments(@Path("id") String soundId);

    // TODO: GET - pending uploads

    // POST
    @Multipart
    @POST("/sounds/upload/")
    public Response uploadSound(@Part("audiofile") TypedFile audioFile,
                                @Part("name") String name,
                                @Part("tags") String tags,
                                @Part("description") String description,
                                @Part("license") String license,
                                @Part("pack") String pack,
                                @Part("geotag") String geoTag);

    @Multipart
    @POST("/sounds/describe/")
    public Response describeSound(@Part("uploaded_filename") String uploadedFileName,
                                  @Part("name") String name,
                                  @Part("tags") String tags,
                                  @Part("description") String description,
                                  @Part("license") String license,
                                  @Part("pack") String pack,
                                  @Part("geotag") String geoTag);


    @Multipart
    @POST("/sounds/{id}/edit/")
    public Response editSound(@Part("name") String name,
                              @Part("tags") String tags,
                              @Part("description") String description,
                              @Part("license") String license,
                              @Part("pack") String pack,
                              @Part("geotag") String geoTag);

    @Multipart
    @POST("/sounds/{id}/bookmark/")
    public Response bookmarkSound(@Part("name") String name,
                                  @Part("category") String category);

    @Multipart
    @POST("/sounds/{id}/rate/")
    public Response rateSound(@Part("rating") int rating);

    @Multipart
    @POST("/sounds/{id}/comment/")
    public Response commentSound(@Part("comment") String comment);

    // --------------------------------------------------------------------------------------------
    // User resources
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("/users/{id}/")
    public User getUser(@Path("id") String userId);

    @GET("/users/{id}/sounds/")
    public Result<Sound> getUserSounds(@Path("id") String userId);

    @GET("/users/{id}/packs/")
    public Result<Pack> getUserPacks(@Path("id") String userId);

    @GET("/users/{id}/bookmark_categories/")
    public Result<BookmarkCategory> getUserBookmarkCategories(@Path("id") String userId);

    @GET("/users/{id}/bookmark_categories/{cat-id}/")
    public Result<Sound> getUserBookmarkCategorySounds(@Path("id") String userId,
                                                       @Path("cat-id") String categoryId);
    // --------------------------------------------------------------------------------------------
    // Pack resources
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("/packs/{id}/")
    public Pack getPack(@Path("id") String packId);

    @GET("/packs/{id}/sounds/")
    public Result<Sound> getPackSounds(@Path("id") String packId);

    @GET("/packs/{id}/download/")
    @Streaming
    public Pack downloadPack(@Path("id") String packId);


    // --------------------------------------------------------------------------------------------
    // Text search
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("/search/text/")
    public Result<Sound> getResults(@Query("query") String query,
                                    @Query("filter") String filter,
                                    @Query("fields") String fields,
                                    @Query("sort") String sort);

    // --------------------------------------------------------------------------------------------
    // Me
    // --------------------------------------------------------------------------------------------

    // GET
    @GET("/me/")
    public User getMe();
}
