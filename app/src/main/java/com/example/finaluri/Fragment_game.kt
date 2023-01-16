package com.example.finaluri

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finaluri.databinding.FragmentGameBinding


@Suppress("ClassName")
class Fragment_game : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        val btn = binding.startButton

        //button just opens GameActivity
        btn.setOnClickListener{
            val intent = Intent (activity, GameActivity::class.java)
            activity?.startActivity(intent)
        }


        return view

    }

}

