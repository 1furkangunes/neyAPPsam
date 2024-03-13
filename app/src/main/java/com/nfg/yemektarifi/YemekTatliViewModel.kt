package com.nfg.yemektarifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class YemekTatliViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)

    private val _yemektatlirecipes = MutableLiveData<List<RecipeModel>>()
    val yemektatliRecipes: LiveData<List<RecipeModel>> get() = _yemektatlirecipes

    fun loadyemekTatliRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "yemektatli.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient ->
                    recipe.materials.split(" ").map { it.lowercase() }.contains(ingredient.lowercase())
                }
            }
            _yemektatlirecipes.postValue(filteredRecipes)
        }
    }
}