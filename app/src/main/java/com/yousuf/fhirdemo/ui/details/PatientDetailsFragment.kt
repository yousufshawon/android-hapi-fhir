package com.yousuf.fhirdemo.ui.details

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yousuf.fhirdemo.R
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.databinding.FragmentPatientDetailsBinding
import com.yousuf.fhirdemo.extension.getDisplayAddress
import com.yousuf.fhirdemo.extension.getDisplayName
import com.yousuf.fhirdemo.extension.showToast
import org.hl7.fhir.r4.model.Patient


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */


class PatientDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPatientDetailsBinding
    private val viewModel: PatientDetailsViewModel by viewModels()
    private val args: PatientDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPatientDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        loadPatientDetails(args.patientId)
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavHostFragment.findNavController(this).navigateUp()
                true
            }
            R.id.menu_patient_edit -> {
                if (args.patientId.isNotEmpty()) {
                    findNavController()
                        .navigate(
                            PatientDetailsFragmentDirections.actionPatientDetailsFragmentToPatientEditFragment(
                                args.patientId
                            )
                        )
                } else {
                    requireContext().showToast("Patient Id not found")
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUi() {

        binding.btnEcgData.setOnClickListener{
            findNavController()
                .navigate(PatientDetailsFragmentDirections.actionPatientDetailsFragmentToEcgDataListFragment(args.patientId))
        }


        binding.fabUpload.setOnClickListener {
            findNavController()
                .navigate(
                    PatientDetailsFragmentDirections.actionPatientDetailsFragmentToAddObservationFragment(
                        args.patientId
                    )
                )
        }
    }


    private fun loadPatientDetails(patientId: String) {
        if (patientId.isNotEmpty()) {
            viewModel.loadPatientDetails(patientId)
        }
    }


    private fun observeData() {
        viewModel.detailsStatus.observe(viewLifecycleOwner) {
            when (it) {
                is TaskStatus.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is TaskStatus.Finished -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    updateUi(it.value)

                }
                TaskStatus.Idle -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                TaskStatus.Running -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        }

    }

    private fun updateUi(patient: Patient) {
        binding.tvName.text = patient.getDisplayName()
        binding.tvId.text = patient.idElement.idPart
        binding.tvAddress.text = patient.getDisplayAddress()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PatientDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}