package com.example.finaluri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.finaluri.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

@Suppress("ClassName")
class Fragment_SignUp : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        val btn: Button = binding.signupButton

        btn.setOnClickListener {

            val email = binding.editTextEmail2.text.toString()
            val password = binding.editTextTextPassword2.text.toString()
            //email and password validation
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(activity,
                                    "successfully signed up",
                                    Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(activity, "Sign up failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }else{Toast.makeText(activity, "Password should consist of at least 6 characters", Toast.LENGTH_SHORT).show()}

            }else{
                Toast.makeText(activity, "Fields should not be empty!", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


