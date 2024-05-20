package com.foregg.presentation.ui.main.home.challenge

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.foregg.domain.model.enums.ChallengeTapType
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentChallengeBinding
import com.foregg.presentation.ui.common.CommonDialog
import com.foregg.presentation.ui.main.home.challenge.adapter.ChallengeListAdapter
import com.foregg.presentation.ui.main.home.challenge.adapter.MyChallengeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChallengeFragment : BaseFragment<FragmentChallengeBinding, ChallengePageState, ChallengeViewModel> (
    FragmentChallengeBinding::inflate
) {
    override val viewModel: ChallengeViewModel by viewModels()

    @Inject
    lateinit var dialog: CommonDialog

    private val challengeListAdapter = ChallengeListAdapter()
    private val myChallengeListAdapter : MyChallengeListAdapter by lazy {
        MyChallengeListAdapter(object : MyChallengeListAdapter.DeleteMyChallengeDelegate {
            override fun deleteChallenge(id: Long) {
                viewModel.quitChallenge(id)
            }
        }, dialog)
    }

    override fun initView() {
        binding.apply {
            vm = viewModel
            viewPagerChallenge.adapter = challengeListAdapter

            viewPagerChallenge.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                private var previousPosition = viewPagerChallenge.currentItem

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (position > previousPosition) {
                        viewModel.swipeNextItem()
                    }
                    else if (position < previousPosition) {
                        viewModel.swipePreviousItem()
                    }
                    previousPosition = position
                }
            })
        }
        bindTab()
        viewModel.setView()
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.uiState.challengeList.collect {
                    challengeListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.uiState.myChallengeList.collect {
                    myChallengeListAdapter.submitList(it)
                }
            }

            launch {
                viewModel.uiState.challengeTapType.collect {
                    when (it) {
                        ChallengeTapType.ALL -> {
                            binding.viewPagerChallenge.adapter = challengeListAdapter
                        }
                        ChallengeTapType.MY -> {
                            binding.viewPagerChallenge.adapter = myChallengeListAdapter
                        }
                    }
                }
            }

            launch {
                viewModel.uiState.participateBtnBackground.collect {
                    binding.btnChallengeParticipate.setBackgroundResource(it)
                }
            }

            launch {
                viewModel.uiState.participateBtnText.collect {
                    binding.btnChallengeParticipate.text = it
                }
            }

            launch {
                viewModel.uiState.participateBtnTextColor.collect {
                    binding.btnChallengeParticipate.setTextColor(it)
                }
            }

            launch {
                viewModel.eventFlow.collect {
                    sortEvent(it as ChallengeEvent)
                }
            }
        }
    }

    private fun sortEvent(event: ChallengeEvent) {
        when(event) {
            ChallengeEvent.onClickParticipateBtn -> participateChallenge()
        }
    }

    private fun bindTab(){
        binding.apply {
            customTabBar.leftTab.setOnClickListener {
                customTabBar.leftBtnClicked()
                if (viewModel.uiState.challengeTapType.value == ChallengeTapType.ALL) return@setOnClickListener
                viewModel.updateTabType()
                viewModel.getAllChallenge()
            }
            customTabBar.rightTab.setOnClickListener {
                customTabBar.rightBtnClicked()
                if (viewModel.uiState.challengeTapType.value == ChallengeTapType.MY) return@setOnClickListener
                viewModel.updateTabType()
                viewModel.getMyChallenge()
            }
        }
    }

    private fun participateChallenge() {
        viewModel.participateChallenge()
    }
}