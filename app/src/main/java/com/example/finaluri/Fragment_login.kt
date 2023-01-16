package com.example.finaluri

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.finaluri.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


@Suppress("ClassName")
class Fragment_login : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        val btn: Button = binding.loginButton
        val btn2: Button = binding.logoutButton

        btn.setOnClickListener {

            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            //email and password validation
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful) {
                        Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                        val user = firebaseAuth.currentUser!!
                        binding.userEmail.text = ("logged in as: $email")
                        val intent = Intent(activity, GameActivity::class.java)
                        intent.putExtra("email", email)
                        intent.putExtra("userid", user.toString())
                        activity?.startActivity(intent)

                        btn2.setOnClickListener{
                            FirebaseAuth.getInstance().signOut()
                            binding.userEmail.text = "not signed in"
                            Toast.makeText(activity, "Signed out successfully", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }

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
