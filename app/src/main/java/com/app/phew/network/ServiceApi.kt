package com.app.phew.network

import com.app.phew.models.BaseResponse
import com.app.phew.models.aboutApp.AboutAppResponse
import com.app.phew.models.auth.LoginResponse
import com.app.phew.models.chatDetails.ChatDetailsResponse
import com.app.phew.models.chatDetails.SendMessageResponse
import com.app.phew.models.chats.ChatsResponse
import com.app.phew.models.home.HomeResponse
import com.app.phew.models.cities.CitiesResponse
import com.app.phew.models.comment.CommentResponse
import com.app.phew.models.countries.CountriesResponse
import com.app.phew.models.findly.contries.FindlyCountriesResponse
import com.app.phew.models.findly.findlyCities.FindlyCitiesResponse
import com.app.phew.models.followUs.FollowUsResponse
import com.app.phew.models.friendRquestes.FriendRequestesResponse
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.notifications.NotificationsResponse
import com.app.phew.models.secretMessages.SecretMessageResponse
import com.app.phew.models.home.ScreenShotBody
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import com.app.phew.models.packages.PackagesResponse
import com.app.phew.models.places.PlacesResponse
import com.app.phew.models.searchResponse.SearchResponse
import okhttp3.MultipartBody
import org.androidannotations.annotations.rest.Post
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {
    //Auth
    @GET(Urls.COUNTRIES)
    fun getCountries(): Call<CountriesResponse>

    @GET(Urls.CITIES)
    fun getCities(@Path("id") countryId: Int): Call<CitiesResponse>

    @POST(Urls.REGISTER)
    fun registerWithoutImages(
            @Query("fullname") fullname: String,
            @Query("mobile") mobile: String,
            @Query("email") email: String,
            @Query("password") password: String,
            @Query("country_id") countryId: Int?,
            @Query("city_id") cityId: Int?,
            @Query("device_type") deviceType: String?,
            @Query("device_token") deviceToken: String?
    ): Call<BaseResponse>

    @Multipart
    @POST(Urls.REGISTER)
    fun registerWithImages(
            @Query("fullname") fullname: String,
            @Query("mobile") mobile: String,
            @Query("email") email: String,
            @Query("password") password: String,
            @Part images: ArrayList<MultipartBody.Part>,
            @Query("country_id") countryId: Int?,
            @Query("city_id") cityId: Int?,
            @Query("device_type") deviceType: String?,
            @Query("device_token") deviceToken: String?
    ): Call<BaseResponse>

    @POST(Urls.LOGIN)
    fun login(
            @Query("identifier") identifier: String,
            @Query("password") password: String,
            @Query("device_type") deviceType: String?,
            @Query("device_token") deviceToken: String?
    ): Call<LoginResponse>

    @POST(Urls.SOCIAL_LOGIN)
    fun socialLogin(
            @Query("fullname") fullName: String,
            @Query("provider_type") providerType: String,
            @Query("provider_id") providerId: String,
            @Query("device_type") deviceType: String?,
            @Query("device_token") deviceToken: String?
    ): Call<LoginResponse>

    @POST(Urls.FORGET_PASSWORD)
    fun forgetPassword(
            @Query("mobile") mobile: String
    ): Call<BaseResponse>

    @POST(Urls.VERIFY)
    fun verify(
            @Query("mobile") mobile: String,
            @Query("code") code: String,
            @Query("verify_type") verifyType: String,

            ): Call<BaseResponse>

    @POST(Urls.RESEND_CODE)
    fun resendCode(
            @Query("mobile") mobile: String
    ): Call<BaseResponse>

    @POST(Urls.RESET_PASSWORD)
    fun resetPassword(
            @Query("mobile") mobile: String,
            @Query("code") code: String,
            @Query("password") password: String,
    ): Call<BaseResponse>

    @POST(Urls.ACCEPT_REQUEST)
    fun acceptFriendRequest(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int,
    ): Call<BaseResponse>

    @POST(Urls.REJECT_REQUEST)
    fun rejectFriendRequest(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int,
    ): Call<BaseResponse>

    @GET(Urls.NOTIFICATIONS)
    fun getNotifications(
            @Header("authorization") auth: String,
    ): Call<NotificationsResponse>

    @DELETE(Urls.DELETE_NOTIFICATIONS)
    fun deleteNotifications(
            @Header("authorization") auth: String,
            @Path("notificationId") notificationId: String,
    ): Call<BaseResponse>

    @GET(Urls.REQUESTS_LIST)
    fun getFriendRequestsList(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int,
            @Query("filter") filter: String
    ): Call<FriendRequestesResponse>

    @GET(Urls.CHATS_LIST)
    fun getChatList(
            @Header("authorization") auth: String
    ): Call<ChatsResponse>

    @GET(Urls.SECRET_MESSAGE_LIST)
    fun getSecretMessagesList(
            @Header("authorization") auth: String
    ): Call<SecretMessageResponse>

    @GET(Urls.FINDLY_COUNTRIES)
    fun findlyCountries(): Call<FindlyCountriesResponse>

    @GET(Urls.FINDLY_CITIES)
    fun findlyCities(
            @Path("id") countryId: Int
    ): Call<FindlyCitiesResponse>

    //Main
    @GET
    fun getHome(
            @Url url: String,
            @Header("authorization") auth: String,
            @Query("type") type: String,
            @Query("page") page: Int
    ): Call<HomeResponse>

    @POST(Urls.POSTS_FAVORITE)
    fun setPostFavorite(
            @Header("authorization") auth: String,
            @Path("post_id") postId: Int
    ): Call<BaseResponse>

    @POST(Urls.SCREEN_SHOT)
    fun screenShot(
            @Header("authorization") auth: String,
            @Body screenShotBody: ScreenShotBody
    ): Call<BaseResponse>

    @GET(Urls.CHAT_DETAILS)
    fun chatDetails(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int
    ): Call<ChatDetailsResponse>

    @POST(Urls.CHAT_DETAILS)
    fun sendMessage(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int,
            @Query("message_type") messageType: String,
            @Query("message") message: String,
    ): Call<SendMessageResponse>

    @Multipart
    @POST(Urls.CHAT_DETAILS)
    fun sendMediaMessage(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int,
            @Query("message_type") messageType: String,
            @Part message: MultipartBody.Part
    ): Call<SendMessageResponse>

    @POST
    fun createPost(
            @Url url: String,
            @Header("authorization") auth: String,
            @Query("post_id") postId: Int?,
            @Query("post_type") postType: String,
            @Query("activity_type") activityType: String,
            @Query("text") text: String?,
            @Query("location") location: String?,
            @Query("watching") watching: String?,
            @Query("friends_with") friendsWith: String?,
            @Query("show_privacy") showPrivacy: String?,
            @Query("show_in_findly") showInFindly: Int?
    ): Call<PostResponse>

    @Multipart
    @POST
    fun createPostWithMedia(
            @Url url: String,
            @Header("authorization") auth: String,
            @Query("post_id") postId: Int?,
            @Query("post_type") postType: String,
            @Query("activity_type") activityType: String,
            @Query("text") text: String?,
            @Query("location") location: String?,
            @Query("watching") watching: String?,
            @Query("friends_with") friendsWith: String?,
            @Query("show_privacy") showPrivacy: String?,
            @Query("show_in_findly") showInFindly: Int?,
            @Part images: ArrayList<MultipartBody.Part>?,
            @Part videos: ArrayList<MultipartBody.Part>?
    ): Call<PostResponse>

    @DELETE(Urls.POST)
    fun deletePost(
            @Header("authorization") auth: String,
            @Path("post_id") postId: Int
    ): Call<BaseResponse>

    @POST(Urls.POST_FINDLAY)
    fun findlayPost(
            @Header("authorization") auth: String,
            @Path("post_id") postId: Int
    ): Call<BaseResponse>

    @POST(Urls.POST_PRIVACY)
    fun updatePostPrivacy(
            @Header("authorization") auth: String,
            @Path("post_id") postId: Int,
            @Query("_method") method: String = "PUT",
            @Query("show_privacy") showPrivacy: String
    ): Call<BaseResponse>

    @POST(Urls.POST_REACT)
    fun reactPost(
            @Header("authorization") auth: String,
            @Path("post_id") postId: Int,
            @Query("type") type: String
    ): Call<BaseResponse>

    @GET(Urls.MOVIES)
    fun getMovies(): Call<MoviesResponse>

    @GET(Urls.MOVIES_SEARCH)
    fun searchMovies(
            @Query("api_key") apiKey: String = "b9aa09eb38643436e7f8e12a1ba2e953",
            @Query("query") query: String,
            @Query("page") page: Int,
            @Query("include_adult") includeAdult: Boolean = true
    ): Call<MoviesSearchResponse>

    @GET(Urls.PLACES_SEARCH)
    fun searchPlaces(
            @Query("input") input: String,
            @Query("inputtype") inputType: String = "textquery",
            @Query("fields") fields: String = "formatted_address,name",
            @Query("key") key: String = "AIzaSyDRymdCLWxCwLHFnwv36iieKAMjiwk8sdc"
    ): Call<PlacesResponse>

    @GET(Urls.FRIENDS)
    fun getFriends(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int
    ): Call<FriendsResponse>

    @GET(Urls.PROFILE)
    fun getProfile(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int
    ): Call<LoginResponse>

    @POST(Urls.UPDATE_SETTING)
    fun notificationSettings(
            @Header("authorization") auth: String,
            @Query("_method") _method: String,
            @Query("all_notices") allNotices: Int?,
            @Query("notification_to_new_followers") newFollowers: Int?,
            @Query("notification_to_mention") mentions: Int?,
    ): Call<LoginResponse>

    @POST(Urls.UPDATE_PACKAGE_SETTINGS)
    fun updatePackageSettings(
            @Header("authorization") auth: String,
            @Query("_method") _method: String,
            @Query("delete_inactive_followers_and_friends") inactive: Int?,
    ): Call<LoginResponse>

    @GET(Urls.ABOUT_APP)
    fun getAboutApp(): Call<AboutAppResponse>

    @GET(Urls.TERMS)
    fun terms(): Call<AboutAppResponse>

    @GET(Urls.FOLLOW_US)
    fun followUs(): Call<FollowUsResponse>

    @POST(Urls.EDIT_PASSWORD)
    fun editPassword(
            @Header("authorization") auth: String,
            @Query("_method") _method: String?,
            @Query("old_password") old_password: String?,
            @Query("password") password: String?
    ): Call<BaseResponse>

    @POST(Urls.UPDATE_PROFILE)
    fun updateProfileWithoutImages(
            @Header("authorization") auth: String,
            @Query("_method") _method: String,
            @Query("fullname") fullname: String,
            @Query("mobile") mobile: String,
            @Query("email") email: String
    ): Call<LoginResponse>

    @Multipart
    @POST(Urls.UPDATE_PROFILE)
    fun updateProfileWithImages(
            @Header("authorization") auth: String,
            @Query("_method") _method: String,
            @Query("fullname") fullname: String,
            @Query("mobile") mobile: String,
            @Query("email") email: String,
            @Part images: ArrayList<MultipartBody.Part>
    ): Call<LoginResponse>

    @DELETE(Urls.DELETE_IMAGE)
    fun deleteImage(
            @Header("authorization") auth: String,
            @Path("id") id: Int
    ): Call<BaseResponse>

    @POST(Urls.LOGOUT)
    fun logout(
            @Header("authorization") auth: String,
            @Query("device_type") deviceType: String,
            @Query("device_token") deviceToken: String
    ): Call<BaseResponse>

    @GET(Urls.SEARCH)
    fun search(
            @Query("username") username: String
    ): Call<SearchResponse>

    @POST(Urls.FOLLOW)
    fun follow(
            @Header("authorization") auth: String,
            @Path("id") userId: Int
    ): Call<BaseResponse>

    @POST(Urls.SEND_REQUEST)
    fun sendRequest(
            @Header("authorization") auth: String,
            @Path("id") userId: Int
    ): Call<BaseResponse>

    @POST(Urls.CANCEL_REQUEST)
    fun cancelRequest(
            @Header("authorization") auth: String,
            @Path("id") userId: Int
    ): Call<BaseResponse>

    @GET(Urls.PACKAGEE)
    fun getPackages(): Call<PackagesResponse>

    @POST(Urls.SUBSCRIBE_PACKAGEE)
    fun subscribePackage(
            @Header("authorization") auth: String,
            @Path("id") packageId: Int,
            @Query("_method") _method: String
    ): Call<BaseResponse>


    @GET(Urls.POST_COMMENTS)
    fun postComments(
            @Header("authorization") auth: String,
            @Path("id") postId: Int,
    ): Call<CommentResponse>

    @POST(Urls.POST_COMMENTS)
    fun addComment(
            @Header("authorization") auth: String,
            @Path("id") postId: Int,
            @Query("text") text: String?
    ): Call<BaseResponse>

    @Multipart
    @POST(Urls.POST_COMMENTS)
    fun addCommentWithMedia(
            @Header("authorization") auth: String,
            @Path("id") postId: Int,
            @Part images: ArrayList<MultipartBody.Part>?
    ): Call<BaseResponse>

    @POST(Urls.REMOVE_FRIENDS)
    fun removeFriend(
            @Header("authorization") auth: String,
            @Path("userId") userId: Int,
    ): Call<BaseResponse>

    @POST(Urls.SEND_SECRET_MESSAGE)
    fun sendSecretMessage(
        @Header("authorization") auth: String,
        @Path("userId") userId: Int,
        @Query("message")message:String
    ): Call<BaseResponse>

}