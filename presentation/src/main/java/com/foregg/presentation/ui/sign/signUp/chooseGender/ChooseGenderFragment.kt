package com.foregg.presentation.ui.sign.signUp.chooseGender

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.foregg.presentation.R
import com.foregg.presentation.base.BaseFragment
import com.foregg.presentation.databinding.FragmentSignUpChooseGenderBinding
import com.foregg.presentation.util.ForeggToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseGenderFragment : BaseFragment<FragmentSignUpChooseGenderBinding, ChooseGenderPageState, ChooseGenderViewModel>(
    FragmentSignUpChooseGenderBinding::inflate
) {

    override val viewModel: ChooseGenderViewModel by viewModels()

    private val chooseGenderFragmentArgs : ChooseGenderFragmentArgs by navArgs()

    override fun initView() {
        binding.apply {
            vm = viewModel
            bindEditText()
        }
    }

    override fun initStates() {
        super.initStates()

        repeatOnStarted(viewLifecycleOwner) {
            launch {
                viewModel.eventFlow.collect {
                    inspectEvent(it as ChooseGenderEvent)
                }
            }
        }
    }

    private fun inspectEvent(event: ChooseGenderEvent){
        when(event){
            is ChooseGenderEvent.OnClickFemaleEvent -> goToFemaleSignUp(event.ssn, event.shareCode)
            is ChooseGenderEvent.OnClickMaleEvent -> goToMaleSignUp(event.ssn)
            ChooseGenderEvent.GoToBackEvent -> findNavController().popBackStack()
            ChooseGenderEvent.ErrorEmpty -> ForeggToast.createToast(requireContext(), R.string.toast_error_empty_ssn, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToFemaleSignUp(ssn : String, shareCode : String){
        val action = ChooseGenderFragmentDirections.actionChooseGenderToFemale(chooseGenderFragmentArgs.accessToken, ssn, shareCode)
        findNavController().navigate(action)
    }

    private fun goToMaleSignUp(ssn : String){
        val action = ChooseGenderFragmentDirections.actionChooseGenderToMale(chooseGenderFragmentArgs.accessToken, ssn)
        findNavController().navigate(action)
    }

    private fun bindEditText(){
        binding.apply {
            editTextSsn1.setGenericTextListener(editTextSsn2)
            editTextSsn2.setGenericTextListener(editTextSsn3)
            editTextSsn3.setGenericTextListener(editTextSsn4)
            editTextSsn4.setGenericTextListener(editTextSsn5)
            editTextSsn5.setGenericTextListener(editTextSsn6)
            editTextSsn6.setGenericTextListener(editTextSsn7)

            editTextSsn2.setGenericKeyEvent(editTextSsn1)
            editTextSsn3.setGenericKeyEvent(editTextSsn2)
            editTextSsn4.setGenericKeyEvent(editTextSsn3)
            editTextSsn5.setGenericKeyEvent(editTextSsn4)
            editTextSsn6.setGenericKeyEvent(editTextSsn5)
            editTextSsn7.setGenericKeyEvent(editTextSsn6)

            editTextSsn7.addTextChangedListener {
                if(editTextSsn7.text.isNotEmpty()) hideKeyboard()
            }

            editTextSsn1.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editTextSsn1, 0)
        }
    }

    private fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().window.decorView.applicationWindowToken, 0)
    }

    class GenericTextListener(private val currentView: EditText, private val nextView: EditText?) : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            if (text.length == 1) nextView?.requestFocus()
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
    }

    private fun EditText.setGenericTextListener(nextView: EditText?) {
        addTextChangedListener(GenericTextListener(this, nextView))
    }

    private fun EditText.setGenericKeyEvent(previousView: EditText?) {
        setOnKeyListener { _, keyCode, event ->
            if (event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && text.isEmpty()) {
                previousView?.apply {
                    text = null
                    requestFocus()
                }
                return@setOnKeyListener true
            }
            false
        }
    }
}