package com.nfg.yemektarifi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.nfg.yemektarifi.databinding.ActivityMainBinding
import com.nfg.yemektarifi.databinding.FragmentHomeeBinding

class HomeeFragment : Fragment() {
    lateinit var fragmentBinding: FragmentHomeeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentHomeeBinding.inflate(inflater,container,false)
        return fragmentBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val malzemelist = arrayListOf<String>()
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, malzemelist)
        fragmentBinding.malzemelist.adapter = adapter

        fragmentBinding.add.setOnClickListener {
            val malzemeinput = fragmentBinding.malzemeinput.text.toString()

            if (malzemeinput.isNotEmpty()) {
                malzemelist.add(malzemeinput)
                adapter.notifyDataSetChanged()
                fragmentBinding.malzemeinput.text.clear()
            }else {
                fragmentBinding.malzemeinput.setHint("Lütfen malzemeyi yazınız.")

            }
        }
        fragmentBinding.find.setOnClickListener {
            val navController = findNavController()
            val snackbar = Snackbar.make(fragmentBinding.root, "En az bir tane tarif türü seçiniz.", Snackbar.LENGTH_SHORT)

            if (malzemelist.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putStringArrayList("malzemelist", malzemelist)
                }
                when {
                    fragmentBinding.optionCorba.isChecked -> {
                        navController.navigate(R.id.action_homeeFragment_to_onlyCorba, bundle)
                    }
                    fragmentBinding.optionYemek.isChecked && fragmentBinding.optionCorba.isChecked -> {
                        navController.navigate(R.id.action_homeeFragment_to_corbaYemek, bundle)
                    }
                    fragmentBinding.optionCorba.isChecked && fragmentBinding.optionTatli.isChecked  -> {
                        navController.navigate(R.id.action_homeeFragment_to_corbaTatli, bundle)
                    }
                    fragmentBinding.optionYemek.isChecked && fragmentBinding.optionTatli.isChecked -> {
                        navController.navigate(R.id.action_homeeFragment_to_yemekTatli, bundle)
                    }
                    fragmentBinding.optionCorba.isChecked && fragmentBinding.optionTatli.isChecked && fragmentBinding.optionYemek.isChecked  -> {
                        navController.navigate(R.id.action_homeeFragment_to_allcyt, bundle)
                    }
                    fragmentBinding.optionYemek.isChecked -> {
                        navController.navigate(R.id.action_homeeFragment_to_onlyYemek, bundle)
                    }
                    fragmentBinding.optionTatli.isChecked -> {
                        navController.navigate(R.id.action_homeeFragment_to_onlyTatli, bundle)
                    }
                    else -> {
                        snackbar.show()
                    }
                }
            } else {
                val snackbar = Snackbar.make(fragmentBinding.root, "Lütfen malzeme listesine en az bir malzeme ekleyiniz.", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
        }


    }




  }
