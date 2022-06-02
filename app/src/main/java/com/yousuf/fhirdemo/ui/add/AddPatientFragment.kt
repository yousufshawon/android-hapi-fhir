package com.yousuf.fhirdemo.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.yousuf.fhirdemo.data.PatientData
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.databinding.FragmentAddPatientBinding
import java.util.*

/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class AddPatientFragment : Fragment() {

    private lateinit var binding : FragmentAddPatientBinding
    private val viewModel : AddPatientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentAddPatientBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observeData()
    }

    private fun initUi(){

        binding.btnAdd.setOnClickListener {
            if( binding.tilFirstName.editText?.text?.trim().isNullOrEmpty() ){
                binding.tilFirstName.error = "Required"
                return@setOnClickListener
            }

            if( binding.tilCity.editText?.text?.trim().isNullOrEmpty() ){
                binding.tilCity.error = "Required"
                return@setOnClickListener
            }

            if( binding.tilCountry.editText?.text?.trim().isNullOrEmpty() ){
                binding.tilCountry.error = "Required"
                return@setOnClickListener
            }

            val patientData = PatientData(
                id = UUID.randomUUID().toString(),
                firstName = binding.tilFirstName.editText?.text?.trim().toString(),
                familyName = binding.tilFamilyName.editText?.text?.trim().toString(),
                city = binding.tilCity.editText?.text?.trim().toString(),
                state = binding.tilState.editText?.text?.trim().toString(),
                country = binding.tilCountry.editText?.text?.trim().toString(),
            )

            viewModel.addPatient(patientData)
        }

    }

    private fun observeData() {
        viewModel.addPatientTaskStatus.observe(viewLifecycleOwner){
            when (it) {
                TaskStatus.Idle -> {
                    binding.btnAdd.isEnabled = true
                }
                is TaskStatus.Error -> {
                    binding.btnAdd.isEnabled = true
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is TaskStatus.Finished -> {
                    binding.btnAdd.isEnabled = true
                    Toast.makeText(requireContext(), "Patient saved", Toast.LENGTH_SHORT).show()
                    NavHostFragment.findNavController(this).navigateUp()
                }

                TaskStatus.Running -> {
                    binding.btnAdd.isEnabled = false
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddPatientFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}