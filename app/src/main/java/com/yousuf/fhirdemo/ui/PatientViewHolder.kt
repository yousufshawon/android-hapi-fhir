package com.yousuf.fhirdemo.ui

import androidx.recyclerview.widget.RecyclerView
import com.yousuf.fhirdemo.databinding.ItemPatientBinding
import org.hl7.fhir.r4.model.Patient

class PatientViewHolder(private val binding:ItemPatientBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(patient: Patient, onItemClicked:(Patient) -> Unit) {
        val name = if( patient.name.size > 0 ) patient.name[0].givenAsSingleString else "not found"
        binding.tvName.text = if (name.isNotEmpty()) name else "Not found"
        itemView.setOnClickListener {
            onItemClicked(patient)
        }
    }
}