package com.example.covid_19_app.ui.fragment


import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.covid_19_app.R
import com.example.covid_19_app.databinding.FragmentSplashBinding
import com.example.covid_19_app.ui.base.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    override fun setContentToView(binding : FragmentSplashBinding) {


        val clk_rotate = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_clockwise
        )
        binding.ivVirus.startAnimation(clk_rotate)
       Handler(Looper.getMainLooper()).postDelayed(Runnable {
              findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
       },3000)


    }


}