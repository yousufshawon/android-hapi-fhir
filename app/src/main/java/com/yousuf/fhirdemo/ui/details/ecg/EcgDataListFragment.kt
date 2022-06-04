package com.yousuf.fhirdemo.ui.details.ecg

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yousuf.fhirdemo.R
import com.yousuf.fhirdemo.data.TaskStatus
import com.yousuf.fhirdemo.databinding.FragmentEcgDataListBinding
import com.yousuf.fhirdemo.extension.showToast


class EcgDataListFragment : Fragment() {

    private lateinit var binding : FragmentEcgDataListBinding
    private val viewModel : EcgDataListViewModel by viewModels()
    private val adapter = EcgDataListAdapter()
    private val args : EcgDataListFragmentArgs by navArgs()

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
        binding = FragmentEcgDataListBinding.inflate( inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        observeData()
        viewModel.getAllEcgData(args.patientId)
    }

    private fun initUi(){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply { setDrawable(
            ColorDrawable(Color.GRAY)
        ) })

    }

    private fun observeData(){
        viewModel.ecgListDataTaskStatus.observe(viewLifecycleOwner){
            when(it){
                is TaskStatus.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    requireContext().showToast(it.message)
                }
                is TaskStatus.Finished -> {
                    adapter.submitList(it.value)
                    if (it.value.isEmpty()){
                        requireContext().showToast("No data found")
                    }
                    binding.progressBar.visibility = View.INVISIBLE
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


    companion object {

        @JvmStatic
        fun newInstance() =
            EcgDataListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}