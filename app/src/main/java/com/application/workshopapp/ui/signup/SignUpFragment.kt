package com.application.workshopapp.ui.signup

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.application.workshopapp.R
import com.application.workshopapp.databinding.FragmentSignUpBinding
import com.application.workshopapp.utils.Constant
import com.application.workshopapp.utils.progressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Signup"
        firebaseAuth = FirebaseAuth.getInstance()
        val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)

        binding.goToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.btnSignup.setOnClickListener {
            val progressBar = requireContext().progressDialog()
            progressBar.show()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val confirmPassword = binding.inputConfirmPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if(password == confirmPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if(it.isSuccessful){
                            sharedPreferences?.edit()?.putBoolean(Constant.USER_SIGNED_IN,true)?.apply()
                            Toast.makeText(this.context,"Successfully Signed Up, Now you can apply",Toast.LENGTH_SHORT).show()
                            progressBar.dismiss()
                            findNavController().navigate(R.id.action_signUpFragment_to_dashboardFragment)
                        }
                        else{
                            Toast.makeText(this.context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            progressBar.dismiss()
                        }
                    }
                }
                else{
                    Toast.makeText(this.context, "Password did not matched", Toast.LENGTH_SHORT).show()
                    progressBar.dismiss()
                }
            }
            else{
                Toast.makeText(this.context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                progressBar.dismiss()
            }
        }

    }
}