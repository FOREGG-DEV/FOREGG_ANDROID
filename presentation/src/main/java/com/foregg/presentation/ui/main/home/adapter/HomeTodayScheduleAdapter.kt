package com.foregg.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.databinding.ItemTodayScheduleBinding


class HomeTodayScheduleAdapter(
    private val listener: HomeTodayScheduleDelegate
) : ListAdapter<HomeRecordResponseVo, RecyclerView.ViewHolder>(
    HomeTodayScheduleDiffUtilCallBack()
) {

    interface HomeTodayScheduleDelegate {
        fun onClickRecordTreatment(id: Long, type: CalendarType, recordType: RecordType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeTodayScheduleViewHolder -> holder.bind(currentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTodayScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTodayScheduleViewHolder(binding, listener)
    }
}

class HomeTodayScheduleDiffUtilCallBack: DiffUtil.ItemCallback<HomeRecordResponseVo>() {
    override fun areItemsTheSame(
        oldItem: HomeRecordResponseVo,
        newItem: HomeRecordResponseVo
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: HomeRecordResponseVo,
        newItem: HomeRecordResponseVo
    ): Boolean {
        return oldItem == newItem
    }
}