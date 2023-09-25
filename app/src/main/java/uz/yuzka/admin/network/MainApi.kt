package uz.yuzka.admin.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import uz.yuzka.admin.data.request.CreateStreamRequest
import uz.yuzka.admin.data.request.GetMoneyRequest
import uz.yuzka.admin.data.request.SetDeviceTokenRequest
import uz.yuzka.admin.data.response.CategoriesResponse
import uz.yuzka.admin.data.response.CharitiesResponse
import uz.yuzka.admin.data.response.ChartResponse
import uz.yuzka.admin.data.response.CreatePromoCodeRequest
import uz.yuzka.admin.data.response.GetMeDto
import uz.yuzka.admin.data.response.MessageResponse
import uz.yuzka.admin.data.response.NotificationsResponse
import uz.yuzka.admin.data.response.ProductItemDto
import uz.yuzka.admin.data.response.ProductsResponse
import uz.yuzka.admin.data.response.PromoCodeData
import uz.yuzka.admin.data.response.PromoCodeResponse
import uz.yuzka.admin.data.response.RegionResponse
import uz.yuzka.admin.data.response.SalesResponse
import uz.yuzka.admin.data.response.StatisticsResponse
import uz.yuzka.admin.data.response.StreamDto
import uz.yuzka.admin.data.response.StreamsResponse
import uz.yuzka.admin.data.response.TransactionsResponse
import uz.yuzka.admin.data.response.WithdrawItemData
import uz.yuzka.admin.data.response.WithdrawsResponse
import uz.yuzka.seller.data.request.LogoutRequest

interface MainApi {

    @GET("advertiser/profile")
    suspend fun getMe(): Response<GetMeDto>

    @GET("advertiser/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int,
    ): Response<ProductItemDto>

    @GET("advertiser/categories")
    suspend fun getCategories(
        @Query("page") page: Int
    ): CategoriesResponse

    @GET("advertiser/orders")
    suspend fun getSales(
        @Query("page") page: Int,
        @Query("status") status: String? = null,
    ): SalesResponse

    @GET("advertiser/products")
    suspend fun getStoreProducts(
        @Query("page") page: Int,
        @Query("category_id") category: Int? = null,
    ): ProductsResponse

    @GET("advertiser/withdraws")
    suspend fun getWithdraws(
        @Query("page") page: Int
    ): WithdrawsResponse

    @POST("advertiser/withdraws")
    suspend fun createWithdraw(
        @Body body: GetMoneyRequest
    ): Response<WithdrawItemData>

    @GET("advertiser/streams/statistics")
    suspend fun getStatistics(
        @Query("page") page: Int = 1,
        @Query("status") status: String?
    ): StatisticsResponse

    @POST("passport/logout")
    suspend fun logout(@Body body: LogoutRequest): Response<MessageResponse>

    @POST("user/set-device-token")
    suspend fun setDeviceToken(
        @Body body: SetDeviceTokenRequest
    ): Response<MessageResponse>

    @GET("advertiser/notifications")
    suspend fun getNotifications(
        @Query("page") page: Int?
    ): NotificationsResponse

    @DELETE("advertiser/chats/{id}")
    suspend fun deleteChatById(
        @Path("id") id: Int
    ): Response<MessageResponse>

    @GET("advertiser/promo-codes")
    suspend fun getPromoCodes(
        @Query("page") page: Int = 1
    ): PromoCodeResponse

    @POST("advertiser/promo-codes")
    suspend fun createPromoCode(
        @Body name: CreatePromoCodeRequest
    ): Response<PromoCodeData>

    @GET("advertiser/transactions")
    suspend fun getTransactions(
        @Query("page") page: Int = 1
    ): TransactionsResponse

    @GET("advertiser/streams/charity")
    suspend fun getCharities(
        @Query("page") page: Int = 1
    ): CharitiesResponse

    @GET("advertiser/streams/{id}")
    suspend fun getStreamById(
        @Path("id") id: Int
    ): Response<StreamDto>

    @GET("advertiser/streams")
    suspend fun getStreams(
        @Query("page") page: Int
    ): StreamsResponse

    @POST("advertiser/streams")
    suspend fun createStreams(
        @Body data: CreateStreamRequest
    ): Response<StreamDto>

    @POST("advertiser/withdraws/{id}/cancel")
    suspend fun cancelWithdraw(
        @Path("id") id: Int
    ): Response<WithdrawItemData>

    @GET("locations")
    suspend fun getLocations(): RegionResponse

    @POST("user/update")
    @Multipart
    suspend fun updateUser(
        @Part("name") name: RequestBody,
        @Part("surname") surname: RequestBody,
        @Part("region_id") regionId: RequestBody,
        @Part("district_id") districtId: RequestBody,
        @Part("address") address: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part avatar: MultipartBody.Part? = null
    ): Response<GetMeDto>

    @DELETE("advertiser/streams/{id}")
    suspend fun deleteStream(
        @Path("id") id: Int
    ): Response<MessageResponse>


    @GET("advertiser/transactions/stats")
    suspend fun getTransactionStatistics(
    ): ChartResponse

    @GET("advertiser/products/{id}/generate-tg-post")
    suspend fun generatePost(
        @Path("id") id: Int
    ): Response<MessageResponse>

}