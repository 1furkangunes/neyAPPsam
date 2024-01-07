package com.nfg.yemektarifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class YemekViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)

    private val _yemekRecipes = MutableLiveData<List<RecipeModel>>()
    val yemekRecipes: LiveData<List<RecipeModel>> get() = _yemekRecipes

    fun loadYemekRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "yemektarif.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient -> recipe.materials.contains(ingredient, ignoreCase = true) }
            }

            _yemekRecipes.postValue(filteredRecipes)
        }
    }
}