package com.foregg.data.api

import com.foregg.data.base.ApiResponse
import com.foregg.data.dto.profile.MyMedicineInjectionItemResponse
import com.foregg.data.dto.profile.MyMedicineInjectionResponse
import com.foregg.data.dto.profile.ProfileDetailResponse
import com.foregg.domain.model.request.profile.EditMyInfoRequestVo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileApi {

    companion object{
        const val QUERY_SORT = "sort"
    }

    @GET(Endpoints.PROFILE.MY_INFO)
    suspend fun getMyInfo() : Response<ApiResponse<ProfileDetailResponse>>

    @PUT(Endpoints.PROFILE.MODIFY)
    suspend fun editMyInfo(
        @Body request : EditMyInfoRequestVo
    ) : Response<ApiResponse<Unit>>

    @GET(Endpoints.PROFILE.GET_MEDICAL)
    suspend fun getMyMedicineInjectionInfo(
        @Query(QUERY_SORT) sort : String
    ) : Response<ApiResponse<MyMedicineInjectionResponse>>

    @POST(Endpoints.AUTH.LOGOUT)
    suspend fun logout() : Response<ApiResponse<Unit>>

    @POST(Endpoints.AUTH.UNREGISTER)
    suspend fun unRegister() : Response<ApiResponse<Unit>>
}