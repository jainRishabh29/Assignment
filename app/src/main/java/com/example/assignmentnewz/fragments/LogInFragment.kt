package com.example.assignmentnewz.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.assignmentnewz.R
import com.example.assignmentnewz.databinding.FragmentLogInBinding
import com.example.assignmentnewz.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth


class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private var validate: Boolean = true
    private val firebase = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.goToSignUp.setOnClickListener {
            //go to logIn fragment
//            findNavController().navigate(R.id.signUpFragment).also {
//                findNavController().popBackStack(R.id.logInFragment,true)
//            }
        }
        binding.loginBtn.setOnClickListener {
            closeKeyboard()
            val email = binding.email.text.toString().trim()
            val pass = binding.password.text.toString()
            Log.d("check", "$email and $pass")
            validate = true

            if (email.isEmpty()) {
                binding.etEmail.error = "Email is Mandatory"
                binding.etEmail.requestFocus()
                validate = false
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                validate = false
                binding.etEmail.error = "Please enter valid email"
                binding.etEmail.requestFocus()
            }
            else if (pass.isEmpty()){
                    validate = false
                    binding.etPassword.setError("Password is Mandatory")
                    binding.etPassword.requestFocus()
            }

            if (validate) {
                firebase.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        findNavController().navigate(R.id.action_wrapperFragment_to_mainFragment)
                    } else {
                        Toast.makeText(context, "Wrong Credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }


        return view
    }

    fun closeKeyboard() {
        val view = requireActivity().currentFocus
        val inputManager: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (firebase.currentUser != null) {
//            findNavController().navigate(R.id.mainFragment).also {
//                findNavController().popBackStack(R.id.wrapperFragment,true)
//            }
            findNavController().navigate(R.id.action_wrapperFragment_to_mainFragment)
        }
    }

}