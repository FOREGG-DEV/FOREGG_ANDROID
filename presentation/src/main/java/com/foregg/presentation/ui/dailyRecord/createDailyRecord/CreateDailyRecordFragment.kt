package com.foregg.presentation.ui.dailyRecord.createDailyRecord

import androidx.fragment.app.viewModels
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentCreateDailyRecordBinding
import com.foregg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDailyRecordFragment : BaseFragment<FragmentCreateDailyRecordBinding, CreateDailyRecordPageState, CreateDailyRecordViewModel>(
    FragmentCreateDailyRecordBinding::inflate
) {

    override val viewModel: CreateDailyRecordViewModel by viewModels()

    override fun initView() {
        binding.apply {
            vm = viewModel
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            viewModel.eventFlow.collect{
                sortEvent(it as CreateDailyRecordEvent)
            }
        }
    }

    private fun sortEvent(event: CreateDailyRecordEvent) {
        when(event) {
            CreateDailyRecordEvent.GoToCreateSideEffectEvent -> goToCreateSideEffect()
            CreateDailyRecordEvent.InsufficientEmotionDataEvent -> ForeggToast.createToast(requireContext(), "오늘의 감정을 선택해주세요.",Toast.LENGTH_SHORT).show()
            CreateDailyRecordEvent.OnClickBtnClose -> findNavController().popBackStack()
            CreateDailyRecordEvent.InsufficientTextDataEvent -> ForeggToast.createToast(requireContext(), "오늘의 컨디션을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToCreateSideEffect() {
        val action = CreateDailyRecordFragmentDirections.createDailyRecordToCreateSideEffect()
        findNavController().navigate(action)
    }
}