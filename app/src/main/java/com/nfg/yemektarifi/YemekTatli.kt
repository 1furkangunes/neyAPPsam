package com.nfg.yemektarifi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nfg.yemektarifi.databinding.FragmentYemekTatliBinding

class YemekTatli : Fragment() {

    lateinit var fragmentBinding: FragmentYemekTatliBinding
    private lateinit var adapter: CorbaRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentYemekTatliBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CorbaRecipeAdapter { clickedRecipe ->
            val bundle = Bundle().apply {
                putParcelable("clickedRecipe", clickedRecipe)
            }
            findNavController().navigate(R.id.action_yemekTatli_to_recipeDetailFragment,bundle)
        }

        val yemekTatliViewModel: YemekTatliViewModel by viewModels()

        yemekTatliViewModel.yemektatliRecipes.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                adapter.setData(it)
            }
        }


        val selectedIngredients = arguments?.getStringArrayList("malzemelist") ?: emptyList()
        yemekTatliViewModel.loadyemekTatliRecipes(selectedIngredients)

        fragmentBinding.recyclerView4.layoutManager = LinearLayoutManager(requireContext())
        fragmentBinding.recyclerView4.adapter = adapter
    }
}
