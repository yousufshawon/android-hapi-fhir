package com.yousuf.fhirdemo.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.databinding.FragmentPatientEditBinding
import com.yousuf.fhirdemo.extension.*
import com.yousuf.fhirdemo.network.RestClient
import org.hl7.fhir.r4.model.Patient
import timber.log.Timber


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class PatientEditFragment : Fragment() {

    private lateinit var binding : FragmentPatientEditBinding
    private val viewModel : ProfileEditViewModel by viewModels()
    private var currentPatient : Patient? = null
    private val args : PatientEditFragmentArgs by navArgs()

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
        binding =  FragmentPatientEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        observeData()
        loadPatient()
    }

    private fun initUi(){
        binding.btnUpdate.setOnClickListener {
            currentPatient?.let {

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


                val humanName = if (it.name == null){
                    it.addName()
                }else{
                    if (it.name.isEmpty()){ it.addName() } else it.name[0]
                }

                humanName.family = binding.tilFamilyName.editText?.text.toString()
                if (humanName.given.isEmpty()){
                    humanName.addGiven(binding.tilFirstName.editText?.text.toString())
                }else{
                    humanName.given[0].value = binding.tilFirstName.editText?.text.toString()
                }

                // setting id again will work for update, skipping will not work ,
                //TODO need to find out the cause
                it.id = args.patientId

                val city = binding.tilCity.editText?.text.toString()
                val state = binding.tilState.editText?.text.toString()
                val country = binding.tilCountry.editText?.text.toString()

                val address =
                if (it.address.isEmpty()){
                    it.addAddress()
                }else{
                    it.address[0]
                }

                address.setCity(city).setState(state).country = country

                viewModel.updatePatient(it)
            } ?: run{
                requireContext().showToast("Patient details not found")
            }
        }
    }

    private fun observeData(){

        viewModel.detailsStatus.observe(viewLifecycleOwner){
            when(it){
                is TaskStatus.Error -> {
                    requireContext().showToast( "Profile fetch error ${it.throwable.message ?: it.message}")
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btnUpdate.isEnabled = false
                }
                is TaskStatus.Finished -> {
                    requireContext().showToast("Found Patient details")
                    binding.progressBar.visibility = View.INVISIBLE
                    currentPatient = it.value
                    binding.btnUpdate.isEnabled = true
                    updateUiWithPatient(it.value)
                }
                TaskStatus.Idle -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btnUpdate.isEnabled = false
                }
                TaskStatus.Running -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnUpdate.isEnabled = false
                }
            }
        }

        viewModel.editTaskStatus.observe(viewLifecycleOwner){
            when(it){
                is TaskStatus.Error -> {
                    requireContext().showToast("Update Error: ${it.throwable.message ?: it.message}")
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btnUpdate.isEnabled = true
                }
                is TaskStatus.Finished -> {
                    requireContext().showToast("Profile updated")
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btnUpdate.isEnabled = true
                    findNavController().navigateUp()
                }
                TaskStatus.Idle -> {
                    //binding.progressBar.visibility = View.INVISIBLE
                    binding.btnUpdate.isEnabled = currentPatient != null
                }
                TaskStatus.Running -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnUpdate.isEnabled = false
                }
            }
        }
    }

    private fun updateUiWithPatient(patient: Patient){
        val patientStr = RestClient.getParser().encodeResourceToString(patient)
        Timber.i("Patient:\n$patientStr")
        binding.tilFirstName.editText?.setText(patient.getGivenName())
        binding.tilFamilyName.editText?.setText(patient.getFamilyName())
        binding.tilCity.editText?.setText(patient.getCity())
        binding.tilState.editText?.setText(patient.getState())
        binding.tilCountry.editText?.setText(patient.getCountry())

    }

    private fun loadPatient(){
        if (args.patientId.isNotEmpty()) {
            viewModel.loadPatient(args.patientId)
        }else{
            requireContext().showToast("patient id not found")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PatientEditFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}