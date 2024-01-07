package com.nfg.yemektarifi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nfg.yemektarifi.databinding.FragmentCorbaYemekBinding
import com.nfg.yemektarifi.databinding.FragmentHomeeBinding
import com.nfg.yemektarifi.databinding.FragmentOnlyCorbaBinding
import com.nfg.yemektarifi.databinding.FragmentOnlyTatliBinding
import com.nfg.yemektarifi.databinding.FragmentOnlyYemekBinding

class CorbaYemek : Fragment() {

    lateinit var fragmentBinding: FragmentCorbaYemekBinding
    private lateinit var adapter: CorbaRecipeAdapter

    // onCreateView ve onViewCreated metodlarını buraya ekleyin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentCorbaYemekBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CorbaRecipeAdapter { clickedRecipe ->
            val bundle = Bundle().apply {
                putParcelable("clickedRecipe", clickedRecipe)
            }
            findNavController().navigate(R.id.action_corbaYemek_to_recipeDetailFragment,bundle)
        }
        val corbaYemekViewModel: CorbaYemekViewModel by viewModels()

        corbaYemekViewModel.yemekCorbaRecipes.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                adapter.setData(it)
            }
        }


        val selectedIngredients = arguments?.getStringArrayList("malzemelist") ?: emptyList()
        corbaYemekViewModel.loadYemekCorbaRecipes(selectedIngredients)

        fragmentBinding.recyclerView5.layoutManager = LinearLayoutManager(requireContext())
        fragmentBinding.recyclerView5.adapter = adapter
    }
}
