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
import com.example.assignmentnewz.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class SignUpFragment : Fragment() {

    private val PASSWORD_PATTERN = Pattern.compile(
        "^" + "(?=.*[0-9])" + "(?=.*[A-Z])" + ".{8,20}" + "$"
    )
    private val firebase = FirebaseAuth.getInstance()
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var validate: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.goToSignIn.setOnClickListener {
            //go to signup fragment
//            findNavController().navigate(R.id.logInFragment).also {
//                findNavController().popBackStack(R.id.signUpFragment,true)
//            }
        }

        binding.signupBtn.setOnClickListener {
            closeKeyboard()

            val email = binding.ettEmail.text.toString().trim()
            val pass = binding.ettPassword.text.toString()
            val name = binding.ettName.text.toString()
            val phoneNum = binding.ettPhone.text.toString().trim()

            validate = true

            if (name.isEmpty()) {
                binding.etName.error = "Name is Mandatory"
                binding.etName.requestFocus()
                validate = false
            } else if (email.isEmpty()) {
                binding.etEmail.error = "Email is Mandatory"
                binding.etEmail.requestFocus()
                validate = false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                validate = false
                binding.etEmail.error = "Please enter valid email"
                binding.etEmail.requestFocus()
            } else if (phoneNum.isEmpty()) {
                validate = false
                binding.etPhone.error = "Please enter phone Number"
                binding.etPhone.requestFocus()
            } else if (!Patterns.PHONE.matcher(phoneNum).matches()) {
                validate = false
                binding.etPhone.error = "Please enter valid phone Number"
                binding.etPhone.requestFocus()
            } else if (pass.isEmpty()) {
                validate = false
                binding.etPassword.setError("Password is Mandatory")
                binding.etPassword.requestFocus()
            } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                validate = false
                binding.etPassword.requestFocus()
                Toast.makeText(
                    this.context,
                    "Password must contain atleast a capital letter, a digit and should be of length 8-20",
                    Toast.LENGTH_LONG
                ).show()
            } else if (!binding.checkboxTnc.isChecked) {
                validate = false
                Toast.makeText(
                    this.context,
                    "Please accept terms and condition.",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (validate) {
                firebase.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.action_wrapperFragment_to_mainFragment)
                        } else {
                            Toast.makeText(
                                context,
                                "Sign Up fail",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("firebase", it.toString())
                        }
                    }

            }
        }
        return view
    }

    private fun closeKeyboard() {
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
}