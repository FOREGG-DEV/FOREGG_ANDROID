package com.foregg.domain.repository

import com.foregg.domain.base.ApiState
import com.foregg.domain.model.request.profile.EditMyInfoRequestVo
import com.foregg.domain.model.response.profile.MyMedicineInjectionResponseVo
import com.foregg.domain.model.response.profile.ProfileDetailResponseVo
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getMyInfo() : Flow<ApiState<ProfileDetailResponseVo>>
    suspend fun editMyInfo(request: EditMyInfoRequestVo) : Flow<ApiState<Unit>>
    suspend fun getMyMedicineInjection(request : String) : Flow<ApiState<List<MyMedicineInjectionResponseVo>>>
}