package com.application.workshopapp.repository

import com.application.workshopapp.data.database.WorkshopSqliteHelper
import com.application.workshopapp.data.model.Workshop

class WorkshopRepository(private  val workshopSqliteHelper : WorkshopSqliteHelper) {
    fun insertWorkshops(workShops: ArrayList<Workshop>) = workshopSqliteHelper.insertWorkshops(workShops)
    fun getWorkshops(): ArrayList<Workshop>  = workshopSqliteHelper.getWorkshops()
    fun getAppliedWorkshops(studentId: Int): ArrayList<Workshop>  = workshopSqliteHelper.getAppliedWorkshops(studentId)
    fun applyToWorkshop(studentId: Int, workshopId: Int) = workshopSqliteHelper.applyToWorkshop(studentId, workshopId)
    fun isWorkshopApplied(studentId: Int, workshopId: Int) = workshopSqliteHelper.isWorkshopApplied(studentId, workshopId)
}