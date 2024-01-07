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
import com.nfg.yemektarifi.databinding.FragmentHomeeBinding
import com.nfg.yemektarifi.databinding.FragmentOnlyCorbaBinding

class OnlyCorba : Fragment() {

    lateinit var fragmentBinding: FragmentOnlyCorbaBinding
    private lateinit var adapter: CorbaRecipeAdapter

    // onCreateView ve onViewCreated metodlarını buraya ekleyin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentOnlyCorbaBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = CorbaRecipeAdapter { clickedRecipe ->
            val bundle = Bundle().apply {
                putParcelable("clickedRecipe", clickedRecipe)
            }
            findNavController().navigate(R.id.action_onlyCorba_to_recipeDetailFragment,bundle)
        }
        // Context parametresini geçirerek ViewModel'i oluşturun
        val corbaViewModel: CorbaViewModel by viewModels()

        corbaViewModel.corbaRecipes.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                adapter.setData(it)
            }
        }
        
        // Çorba tariflerini yükle
        val selectedIngredients = arguments?.getStringArrayList("malzemelist") ?: emptyList()
        corbaViewModel.loadCorbaRecipes(selectedIngredients)

        fragmentBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentBinding.recyclerView.adapter = adapter
    }
}
