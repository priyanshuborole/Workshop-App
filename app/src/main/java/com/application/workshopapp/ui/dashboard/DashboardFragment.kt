package com.application.workshopapp.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.workshopapp.R
import com.application.workshopapp.data.database.WorkshopSqliteHelper
import com.application.workshopapp.data.model.Workshop
import com.application.workshopapp.databinding.FragmentDashboardBinding
import com.application.workshopapp.repository.WorkshopRepository
import com.application.workshopapp.ui.adapter.ClickListeners
import com.application.workshopapp.ui.adapter.WorkshopAdapter
import com.application.workshopapp.ui.workshop.AvailableWorkshopVM
import com.application.workshopapp.ui.workshop.AvailableWorkshopVMFactory
import com.application.workshopapp.utils.Constant
import com.application.workshopapp.utils.addDummyData
import com.application.workshopapp.utils.progressDialog

class DashboardFragment : Fragment() {
    private var userSignedIn  = false
    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Dashboard"
        val workshopSqliteHelper = WorkshopSqliteHelper(requireContext())
        val workshopRepository = WorkshopRepository(workshopSqliteHelper)
        val viewModelFactory = DashboardVMFactory(workshopRepository)
        val dashboardVM = ViewModelProvider(this,viewModelFactory)[DashboardVM::class.java]
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)
        userSignedIn = sharedPreferences?.getBoolean(Constant.USER_SIGNED_IN,false)==true;
        if (!userSignedIn){
            findNavController().navigate(R.id.action_dashboardFragment_to_loginFragment)
        }
        setUpRecyclerView(dashboardVM,workshopRepository)
        binding.goToWorkshop.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_availableWorkshopFragment)
        }
    }
    private fun setUpRecyclerView(workshopVM: DashboardVM,workshopRepository: WorkshopRepository){
        val progressBar = requireContext().progressDialog()
        progressBar.show()
        val workshops = workshopVM.getAppliedWorkshops(Constant.STUDENT_ID)
        if (workshops.size > 0){
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            binding.recyclerviewWorkshops.layoutManager = layoutManager
            val adapter = WorkshopAdapter(workshops,workshopRepository,object : ClickListeners {
                override fun onApply(btn: Button, id: Int) {
                    Log.d("PRI", "onApply: Applied")
                }
            })
            binding.recyclerviewWorkshops.adapter = adapter
            binding.tvEmpty.visibility = View.GONE
            if (progressBar.isShowing){
                progressBar.dismiss()
            }
        }
        else{
            progressBar.dismiss()
            binding.tvEmpty.visibility = View.VISIBLE
        }

    }
}