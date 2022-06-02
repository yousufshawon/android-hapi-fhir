package com.yousuf.fhirdemo.ui.observation

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yousuf.fhirdemo.databinding.FragmentAddObservationBinding
import com.yousuf.fhirdemo.extension.showToast
import com.yousuf.fhirdemo.network.RestClient
import com.yousuf.fhirdemo.repository.ObservationRepository
import com.yousuf.fhirdemo.usecase.AddObservationUseCase
import com.yousuf.fhirdemo.utility.DataGenerator
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  2/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class AddObservationFragment : Fragment() {

    private lateinit var binding : FragmentAddObservationBinding
    private val addObservationUseCase = AddObservationUseCase(ObservationRepository(RestClient.getClient()))
    private val args : AddObservationFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddObservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

    }

    private fun initUi(){

        binding.tvEcgData.movementMethod = ScrollingMovementMethod()
        binding.tvEcgData.text = DataGenerator.EcgData.sampleEcgData


        binding.btnUpload.setOnClickListener {
            lifecycleScope.launch{
                try {
                    binding.btnUpload.isEnabled = false
                    val outcome = addObservationUseCase.uploadEcg(args.patientId, binding.tvEcgData.text.toString())
                    Timber.i("MethodOutcome.created ${outcome.created}")
                    requireContext().showToast("Uploaded successfully")
                    binding.btnUpload.isEnabled = true
                    findNavController().navigateUp()
                }catch (ex:Exception){
                    binding.btnUpload.isEnabled = true
                    requireContext().showToast("Uploaded failed ${ex.message ?: ex::class.java.simpleName}")
                }

            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AddObservationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}