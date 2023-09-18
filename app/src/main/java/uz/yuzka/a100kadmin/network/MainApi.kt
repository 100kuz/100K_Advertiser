package uz.yuzka.a100kadmin.network

import retrofit2.Response
import retrofit2.http.*
import uz.yuzka.a100kadmin.data.request.CreateStreamRequest
import uz.yuzka.a100kadmin.data.response.CategoriesResponse
import uz.yuzka.a100kadmin.data.response.CharitiesResponse
import uz.yuzka.a100kadmin.data.response.CreatePromoCodeRequest
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.MessageResponse
import uz.yuzka.a100kadmin.data.response.NotificationsResponse
import uz.yuzka.a100kadmin.data.response.ProductItemDto
import uz.yuzka.a100kadmin.data.response.ProductsResponse
import uz.yuzka.a100kadmin.data.response.PromoCodeData
import uz.yuzka.a100kadmin.data.response.PromoCodeResponse
import uz.yuzka.a100kadmin.data.response.SalesResponse
import uz.yuzka.a100kadmin.data.response.StatisticsResponse
import uz.yuzka.a100kadmin.data.response.StreamDto
import uz.yuzka.a100kadmin.data.response.StreamsResponse
import uz.yuzka.a100kadmin.data.response.TransactionsResponse
import uz.yuzka.a100kadmin.data.response.WithdrawItemData
import uz.yuzka.a100kadmin.data.response.WithdrawsResponse
import uz.yuzka.a100kadmin.data.request.GetMoneyRequest
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest

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

    @GET("advertiser/statistics")
    suspend fun getStatistics(
        @Path("store") id: Int
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


}