package com.application.workshopapp.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.application.workshopapp.R
import com.application.workshopapp.databinding.FragmentLoginBinding
import com.application.workshopapp.utils.Constant
import com.application.workshopapp.utils.progressDialog
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Login"
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        firebaseAuth = FirebaseAuth.getInstance()
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)

        binding.goToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
        }

        binding.btnLogin.setOnClickListener {
            val progressBar = requireContext().progressDialog()
            progressBar.show()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        sharedPreferences?.edit()?.putBoolean(Constant.USER_SIGNED_IN, true)
                            ?.apply()
                        Toast.makeText(
                            this.context,
                            "Successfully Logged In, Now you can apply",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.dismiss()
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                    } else {
                        Toast.makeText(
                            this.context,
                            "Incorrect Email or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.dismiss()
                    }
                }
            } else {
                Toast.makeText(this.context, "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
                progressBar.dismiss()
            }
        }
    }
}