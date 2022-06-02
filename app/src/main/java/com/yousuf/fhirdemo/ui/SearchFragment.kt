package com.yousuf.fhirdemo.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousuf.fhirdemo.R
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.databinding.FragmentSearchBinding
import com.yousuf.fhirdemo.extension.showToast
import org.hl7.fhir.r4.model.Patient
import timber.log.Timber

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel : MainViewModel by activityViewModels()

    private val adapter  = PatientAdapter(this::onPatientItemClicked)

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
        binding =   FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUi()
        observeData()
    }

    private fun initializeUi(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.i("onQueryTextSubmit: $query")
                if (query.isNullOrEmpty()){
                    return true
                }
                viewModel.onSearchAction(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.i("onQueryTextChange: $newText")
                if (newText.isNullOrEmpty()){
                    viewModel.onSearchAction( )
                }
                return true
            }
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToAddPatientFragment())
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply { setDrawable(ColorDrawable(Color.GRAY)) })


    }

    private fun observeData(){
        viewModel.patientListLiveData.observe(viewLifecycleOwner){
            //Timber.i("Total ${it.size} Patient  found")
            //adapter.submitList(it)
        }

        viewModel.searchStatus.observe(viewLifecycleOwner){
            when(it){
                is TaskStatus.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    requireContext().showToast(it.throwable.message ?: it.throwable.javaClass.simpleName)
                }
                is TaskStatus.Finished -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapter.submitList(it.value)
                    Timber.i("Total ${it.value.size} Patient  found")
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

    private fun onPatientItemClicked(patient: Patient){
        findNavController()
            .navigate(SearchFragmentDirections.actionSearchFragmentToPatientDetailsFragment(patient.idElement.idPart))
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}