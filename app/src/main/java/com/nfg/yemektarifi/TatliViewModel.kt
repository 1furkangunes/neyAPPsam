package com.nfg.yemektarifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TatliViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)

    private val _tatlirecipes = MutableLiveData<List<RecipeModel>>()
    val tatliRecipes: LiveData<List<RecipeModel>> get() = _tatlirecipes

    fun loadTatliRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "tatlitarif.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient -> recipe.materials.contains(ingredient, ignoreCase = true) }
            }

            _tatlirecipes.postValue(filteredRecipes)
        }
    }
}