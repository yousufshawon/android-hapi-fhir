package com.yousuf.fhirdemo.ui.details.ecg

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
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

class EcgDataListAdapter : ListAdapter<EcgObservationData, EcgDataViewHolder>(EcgItemDifCallback()) {

    private var inflater : LayoutInflater? = null

    class EcgItemDifCallback : DiffUtil.ItemCallback<EcgObservationData>(){
        override fun areItemsTheSame(oldItem: EcgObservationData, newItem: EcgObservationData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EcgObservationData, newItem: EcgObservationData): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcgDataViewHolder {
        val binding = ItemEcgDataBinding.inflate(getLayoutInflater(parent.context), parent, false)
        return EcgDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EcgDataViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun getLayoutInflater(context: Context) : LayoutInflater {
        return inflater ?: LayoutInflater.from(context).also {
            inflater = it
        }
    }
}