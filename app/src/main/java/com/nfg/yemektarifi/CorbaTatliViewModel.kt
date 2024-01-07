package com.nfg.yemektarifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CorbaTatliViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)

    private val _corbaTatliRecipes = MutableLiveData<List<RecipeModel>>()
    val corbaTatliRecipes: LiveData<List<RecipeModel>> get() = _corbaTatliRecipes

    fun loadCorbaTatliRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "corbatatli.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient -> recipe.materials.contains(ingredient, ignoreCase = true) }
            }
            _corbaTatliRecipes.postValue(filteredRecipes)
        }
    }
}