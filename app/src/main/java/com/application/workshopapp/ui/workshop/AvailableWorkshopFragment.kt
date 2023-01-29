package com.application.workshopapp.ui.workshop

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.workshopapp.R
import com.application.workshopapp.data.database.WorkshopSqliteHelper
import com.application.workshopapp.data.model.Workshop
import com.application.workshopapp.databinding.FragmentAvailableWorkshopBinding
import com.application.workshopapp.repository.WorkshopRepository
import com.application.workshopapp.ui.adapter.ClickListeners
import com.application.workshopapp.ui.adapter.WorkshopAdapter
import com.application.workshopapp.utils.Constant
import com.application.workshopapp.utils.addDummyData
import com.application.workshopapp.utils.progressDialog

class AvailableWorkshopFragment : Fragment() {
    private lateinit var binding: FragmentAvailableWorkshopBinding
    private var userSignedIn = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAvailableWorkshopBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Exit")
            builder.setMessage("Do you want to exit the app?")
            builder.setPositiveButton("Yes") { _, _ ->
                requireActivity().finish()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        (activity as AppCompatActivity).supportActionBar?.title = "Available Workshops"
        val workshopSqliteHelper = WorkshopSqliteHelper(requireContext())
        val workshopRepository = WorkshopRepository(workshopSqliteHelper)
        val viewModelFactory = AvailableWorkshopVMFactory(workshopRepository)
        val availableWorkshopVM = ViewModelProvider(this,viewModelFactory)[AvailableWorkshopVM::class.java]
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)
        userSignedIn = sharedPreferences?.getBoolean(Constant.USER_SIGNED_IN,false)==true;
        val workshops = ArrayList<Workshop>()
        workshops.addDummyData()
        availableWorkshopVM.insertWorkshops(workshops)
        setUpRecyclerView(availableWorkshopVM,workshopRepository)

        binding.goToDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_availableWorkshopFragment_to_dashboardFragment)
        }

    }

    private fun setUpRecyclerView(workshopVM: AvailableWorkshopVM,workshopRepository: WorkshopRepository){
        val progressBar = requireContext().progressDialog()
        progressBar.show()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.recyclerviewWorkshops.layoutManager = layoutManager
        val workshops = workshopVM.getWorkshops()
        val adapter = WorkshopAdapter(workshops,workshopRepository,object : ClickListeners{
            override fun onApply(btn: Button, id: Int) {
                if (userSignedIn) {
                    btn.text = "Applied"
                    workshopVM.applyToWorkshop(Constant.STUDENT_ID, id)
                    Toast.makeText(requireContext(),"Applied to workshop", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(),"Log in to apply for workshop", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_availableWorkshopFragment_to_loginFragment)
                }
            }
        })
        binding.recyclerviewWorkshops.adapter = adapter
        if (progressBar.isShowing){
            progressBar.dismiss()
        }
    }
}