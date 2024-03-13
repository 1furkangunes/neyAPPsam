package com.nfg.yemektarifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AllViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)
    private val _allrecipes = MutableLiveData<List<RecipeModel>>()
    val allRecipes: LiveData<List<RecipeModel>> get() = _allrecipes

    fun loadAllRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "all.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient ->
                    recipe.materials.split(" ").map { it.lowercase() }.contains(ingredient.lowercase())
                }
            }
            _allrecipes.postValue(filteredRecipes)
        }
    }
}