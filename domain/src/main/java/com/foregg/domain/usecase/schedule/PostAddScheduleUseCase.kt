package com.foregg.domain.usecase.schedule

import com.foregg.domain.base.ApiState
import com.foregg.domain.base.UseCase
import com.foregg.domain.model.request.ScheduleDetailRequestVo
import com.foregg.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostAddScheduleUseCase @Inject constructor(
    private val recordRepository: ScheduleRepository
): UseCase<ScheduleDetailRequestVo, ApiState<Unit>>() {
    override suspend operator fun invoke(request: ScheduleDetailRequestVo): Flow<ApiState<Unit>> {
        return recordRepository.addSchedule(request)
    }
}