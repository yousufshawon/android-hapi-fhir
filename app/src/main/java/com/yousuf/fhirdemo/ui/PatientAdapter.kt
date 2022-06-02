package com.yousuf.fhirdemo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yousuf.fhirdemo.databinding.ItemPatientBinding
import org.hl7.fhir.r4.model.Patient

class PatientAdapter(private val onItemClicked : (Patient) -> Unit) : ListAdapter<Patient, PatientViewHolder>(PatientItemDiffCallback()) {

    private var inflater : LayoutInflater? = null

    class PatientItemDiffCallback : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = ItemPatientBinding.inflate(getLayoutInflater(parent.context), parent, false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClicked)
    }

    private fun getLayoutInflater(context:Context) : LayoutInflater{
        return inflater ?: LayoutInflater.from(context).also {
            inflater = it
        }
    }

}