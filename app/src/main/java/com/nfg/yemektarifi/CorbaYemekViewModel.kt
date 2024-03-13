package com.nfg.yemektarifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CorbaYemekViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)

    private val _yemekcorbarecipes = MutableLiveData<List<RecipeModel>>()
    val yemekCorbaRecipes: LiveData<List<RecipeModel>> get() = _yemekcorbarecipes

    fun loadYemekCorbaRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "corbayemek.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient ->
                    recipe.materials.split(" ").map { it.lowercase() }.contains(ingredient.lowercase())
                }
            }
            _yemekcorbarecipes.postValue(filteredRecipes)
        }
    }
}