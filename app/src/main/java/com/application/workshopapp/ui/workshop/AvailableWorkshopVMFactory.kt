package com.application.workshopapp.ui.workshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.workshopapp.repository.WorkshopRepository

class AvailableWorkshopVMFactory(
    private val workshopRepository: WorkshopRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvailableWorkshopVM::class.java)) {
            return AvailableWorkshopVM(workshopRepository) as T
        }
        throw IllegalArgumentException("ViewModel not created")
    }
}