package com.nfg.yemektarifi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nfg.yemektarifi.databinding.FragmentCorbaTatliBinding

class CorbaTatli : Fragment() {

    lateinit var fragmentBinding: FragmentCorbaTatliBinding
    private lateinit var adapter: CorbaRecipeAdapter

    // onCreateView ve onViewCreated metodlarını buraya ekleyin
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentCorbaTatliBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = CorbaRecipeAdapter { clickedRecipe ->
            val bundle = Bundle().apply {
                putParcelable("clickedRecipe", clickedRecipe)
            }
            findNavController().navigate(R.id.action_corbaTatli_to_recipeDetailFragment,bundle)
        }
        // Context parametresini geçirerek ViewModel'i oluşturun
        val corbaTatliViewModel: CorbaTatliViewModel by viewModels()

        corbaTatliViewModel.corbaTatliRecipes.observe(viewLifecycleOwner) { recipes ->
            recipes?.let {
                adapter.setData(it)
            }
        }

        val selectedIngredients = arguments?.getStringArrayList("malzemelist") ?: emptyList()
        corbaTatliViewModel.loadCorbaTatliRecipes(selectedIngredients)

        fragmentBinding.recyclerView6.layoutManager = LinearLayoutManager(requireContext())
        fragmentBinding.recyclerView6.adapter = adapter
    }
}
