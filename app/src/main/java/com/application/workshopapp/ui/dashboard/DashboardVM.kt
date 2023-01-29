package com.application.workshopapp.ui.dashboard

import androidx.lifecycle.ViewModel
import com.application.workshopapp.data.model.Workshop
import com.application.workshopapp.repository.WorkshopRepository

class DashboardVM(
    private val workshopRepository: WorkshopRepository
) : ViewModel(){
    fun getAppliedWorkshops(studentId: Int): ArrayList<Workshop>{
        return workshopRepository.getAppliedWorkshops(studentId)
    }
}