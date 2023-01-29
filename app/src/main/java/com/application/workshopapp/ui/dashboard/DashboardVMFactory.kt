package com.application.workshopapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.workshopapp.repository.WorkshopRepository
import com.application.workshopapp.ui.workshop.AvailableWorkshopVM

class DashboardVMFactory(
private val workshopRepository: WorkshopRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardVM::class.java)) {
            return DashboardVM(workshopRepository) as T
        }
        throw IllegalArgumentException("ViewModel not created")
    }
}