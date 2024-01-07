package com.nfg.yemektarifi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nfg.yemektarifi.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    lateinit var fragmentBinding: FragmentRecipeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tıklanan çorba verilerini al
        val clickedRecipe = arguments?.getParcelable<RecipeModel>("clickedRecipe") as RecipeModel?

        // Verileri göster
        fragmentBinding.recipeNameTextView.text = clickedRecipe?.name
        fragmentBinding.materialsTextView.text = clickedRecipe?.materials
        fragmentBinding.recipeTextView.text = clickedRecipe?.recipe
    }
    companion object {
        fun newInstance(recipe: RecipeModel): RecipeDetailFragment {
            val fragment = RecipeDetailFragment()
            val bundle = Bundle().apply {
                putParcelable("clickedRecipe", recipe)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}