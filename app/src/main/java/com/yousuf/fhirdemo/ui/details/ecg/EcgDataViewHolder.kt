package com.yousuf.fhirdemo.ui.details.ecg

import android.text.method.ScrollingMovementMethod
import androidx.recyclerview.widget.RecyclerView
import com.yousuf.fhirdemo.data.EcgObservationData
import com.yousuf.fhirdemo.databinding.ItemEcgDataBinding


/**
 * Created by Android Studio.
 * User:  Yousuf
 * Email:
 * Date:  3/6/22
 * To change this template use File | Settings | File and Code Templates.
 *
 */

class EcgDataViewHolder(private val binding:ItemEcgDataBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.tvEcgData.movementMethod = ScrollingMovementMethod()
    }

    fun bind(ecgData:EcgObservationData){
        binding.tvId.text = "Id: ${ecgData.id}"
        binding.tvReference.text = "Subject: ${ecgData.subject}"
        binding.tvDateTime.text = "Date Time: ${ecgData.dateTime}"
        binding.tvEcgData.text = ecgData.data.data

    }
}