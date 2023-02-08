package com.application.workshopapp.ui.workshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.workshopapp.data.model.Workshop
import com.application.workshopapp.repository.WorkshopRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AvailableWorkshopVM(
    private val workshopRepository: WorkshopRepository
) : ViewModel() {

    fun insertWorkshops(workshops: ArrayList<Workshop>) {
        workshopRepository.insertWorkshops(workshops)
    }

    fun getWorkshops(): ArrayList<Workshop> {
        return workshopRepository.getWorkshops()
    }

    fun applyToWorkshop(studentId: Int, workshopId: Int) {
            workshopRepository.applyToWorkshop(studentId, workshopId)
    }
    fun deleteAppliedWorkshops() {
            workshopRepository.deleteAppliedWorkshops()
    }
}