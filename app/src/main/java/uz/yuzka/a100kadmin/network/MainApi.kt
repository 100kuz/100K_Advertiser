package uz.yuzka.a100kadmin.network

import retrofit2.Response
import retrofit2.http.*
import uz.yuzka.a100kadmin.data.response.CategoriesResponse
import uz.yuzka.a100kadmin.data.response.CreatePromoCodeRequest
import uz.yuzka.a100kadmin.data.response.GetMeDto
import uz.yuzka.a100kadmin.data.response.GetMoneyResponse
import uz.yuzka.a100kadmin.data.response.MessageResponse
import uz.yuzka.a100kadmin.data.response.NotificationsResponse
import uz.yuzka.a100kadmin.data.response.ProductItemDto
import uz.yuzka.a100kadmin.data.response.ProductsResponse
import uz.yuzka.a100kadmin.data.response.PromoCodeData
import uz.yuzka.a100kadmin.data.response.PromoCodeResponse
import uz.yuzka.a100kadmin.data.response.SalesResponse
import uz.yuzka.a100kadmin.data.response.StatisticsResponse
import uz.yuzka.a100kadmin.data.response.WithdrawsResponse
import uz.yuzka.seller.data.request.GetMoneyRequest
import uz.yuzka.seller.data.request.LogoutRequest
import uz.yuzka.seller.data.request.SetDeviceTokenRequest

interface MainApi {

    @GET("profile")
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
        @Query("status") status: String? = null,
        @Query("page") page: Int,
        @Query("query") search: String? = null,
    ): ProductsResponse

    @GET("advertiser/{store_id}/withdraws")
    suspend fun getStoreWithdraws(
        @Path("store_id") id: Int,
        @Query("page") page: Int
    ): WithdrawsResponse

    @POST("advertiser/{store}/withdraws")
    suspend fun getMoney(
        @Path("store") id: Int,
        @Body body: GetMoneyRequest
    ): Response<GetMoneyResponse>

    @GET("advertiser/{store}/statistics")
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

}