package com.application.workshopapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.application.workshopapp.R
import com.application.workshopapp.data.model.Workshop
import com.application.workshopapp.databinding.ItemWorkshopBinding
import com.application.workshopapp.repository.WorkshopRepository
import com.application.workshopapp.utils.Constant
import com.bumptech.glide.Glide

class WorkshopAdapter(
    private val workshops: List<Workshop>,
    private val workshopRepository: WorkshopRepository,
    private val listeners: ClickListeners
) : RecyclerView.Adapter<WorkshopAdapter.WorkShopViewHolder>() {
    inner class WorkShopViewHolder(val binding: ItemWorkshopBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkShopViewHolder {
        val binding = ItemWorkshopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkShopViewHolder, position: Int) {
        val workshop = workshops[position]
        Glide.with(holder.itemView.context).load(workshop.workshop_image).placeholder(R.drawable.placeholder).into(holder.binding.ivImage)
        Log.d("Adapter", "onBindViewHolder: ${workshop.workshop_image} ")
        holder.binding.apply {
            tvName.text = workshop.workshop_name
            tvDesc.text = workshop.workshop_desc
            tvDate.text = workshop.workshop_date
            if (workshopRepository.isWorkshopApplied(Constant.STUDENT_ID,workshop.workshop_id)){
                btnApply.isEnabled = false
                btnApply.text = "Applied"
            }
            btnApply.setOnClickListener {
                listeners.onApply(btnApply,workshop.workshop_id)
            }
        }
    }
    override fun getItemCount(): Int = workshops.size
}

interface ClickListeners {
    fun onApply(btn: Button, id: Int)
}