package com.foregg.presentation.ui.main.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foregg.domain.model.enums.CalendarType
import com.foregg.domain.model.enums.GenderType
import com.foregg.domain.model.enums.RecordType
import com.foregg.domain.model.response.HomeRecordResponseVo
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentHomeBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.home.adapter.HomeChallengeAdapter
import com.foregg.presentation.ui.main.home.adapter.HomeIntroductionAdapter
import com.foregg.presentation.ui.main.home.adapter.HomeTodayScheduleAdapter
import com.foregg.presentation.util.UserInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomePageState, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    private val homeIntroductionAdapter = HomeIntroductionAdapter()

    private val todayScheduleAdapter : HomeTodayScheduleAdapter by lazy {
        HomeTodayScheduleAdapter(object : HomeTodayScheduleAdapter.HomeTodayScheduleDelegate {
            override fun onClickRecordTreatment(id: Long, recordType: RecordType) {
                goToCreateEditSchedule(id, recordType)
            }
        })
    }

    private val homeChallengeAdapter: HomeChallengeAdapter by lazy {
        HomeChallengeAdapter( object : HomeChallengeAdapter.HomeChallengeDelegate {
            override fun showDialog(id: Long) {
                showCompleteDialog(id)
            }
        })
    }

    override fun initView() {
        val position = viewModel.uiState.scheduleStartPosition.value
        binding.apply {
            vm = viewModel
            todayScheduleViewPager.adapter = todayScheduleAdapter
            todayScheduleViewPager.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            if (position != 0) todayScheduleViewPager.scrollToPosition(position)
            challengeRecyclerView.adapter = homeChallengeAdapter
            challengeRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            advertiseViewPager.adapter = homeIntroductionAdapter
            indicatorView.attachTo(advertiseViewPager)
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.todayScheduleList.collect {
                    todayScheduleAdapter.submitList(it)
                }
            }
            launch {
                viewModel.uiState.challengeList.collect {
                    homeChallengeAdapter.submitList(it)
                }
            }
            launch {
                viewModel.uiState.scheduleStartPosition.collect {
                    if (it != 0) binding.todayScheduleViewPager.scrollToPosition(it)
                }
            }
            launch {
                viewModel.uiState.homeIntroductionItemList.collect {
                    homeIntroductionAdapter.submitList(it)
                }
            }
            launch {
                viewModel.uiState.dailyContent.collect {
                    if (it.isEmpty()) binding.textHusbandDailyRecord.text = requireContext().getString(R.string.home_empty_record)
                    else binding.textHusbandDailyRecord.text = it
                }
            }
            launch {
                viewModel.uiState.medicalRecord.collect {
                    if (it.isEmpty()) binding.textHusbandMedicalRecord.text = requireContext().getString(R.string.home_empty_record)
                    else binding.textHusbandMedicalRecord.text = it
                }
            }
            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as HomeEvent)
                }
            }
        }

        viewModel.initScheduleStates()
    }

    private fun sortEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.GoToChallengeEvent -> goToChallenge()
            HomeEvent.GoToDailyRecordEvent -> goToDailyRecord()
            HomeEvent.GoToCalendarEvent -> goToCalendar()
        }
    }

    private fun goToChallenge() {
        val action = HomeFragmentDirections.actionHomeToChallege()
        findNavController().navigate(action)
    }

    private fun goToDailyRecord() {
        val action = HomeFragmentDirections.actionHomeToDailyRecord()
        findNavController().navigate(action)
    }

    private fun showCompleteDialog(id: Long) {
        dialog
            .setTitle(R.string.home_challenge_complete_dialog_text)
            .setPositiveButton(R.string.word_yes) {
                viewModel.completeChallenge(id)
                dialog.dismiss()
            }
            .setNegativeButton(R.string.word_no) {
                dialog.dismiss()
            }
            .show()
    }

    private fun goToCreateEditSchedule(id: Long, recordType: RecordType) {
        val action = HomeFragmentDirections.actionHomeToCreateEditSchedule(id = id, type = CalendarType.EDIT, scheduleType = recordType, isHome = true)
        findNavController().navigate(action)
    }

    private fun goToCalendar() {
        val action = HomeFragmentDirections.actionHomeToCalendar()
        findNavController().navigate(action)
    }
}