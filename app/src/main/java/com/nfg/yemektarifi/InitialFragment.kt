package com.nfg.yemektarifi

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nfg.yemektarifi.databinding.ActivityMainBinding
import com.nfg.yemektarifi.databinding.FragmentHomeeBinding
import com.nfg.yemektarifi.databinding.FragmentInitialBinding

class InitialFragment : Fragment() {
    lateinit var fragmentBinding: FragmentInitialBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentInitialBinding.inflate(inflater,container,false)
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding.animationView.playAnimation()
    }
    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            val navController = findNavController()
            navController.navigate(R.id.action_initialFragment_to_homeeFragment)
        }, 3000)
    }
}

