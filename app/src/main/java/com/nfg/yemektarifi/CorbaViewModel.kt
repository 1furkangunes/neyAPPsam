package com.nfg.yemektarifi

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CorbaViewModel(application: Application) : AndroidViewModel(application) {

    private val fileHelper = AssetReader(application)

    private val _corbaRecipes = MutableLiveData<List<RecipeModel>>()
    val corbaRecipes: LiveData<List<RecipeModel>> get() = _corbaRecipes

    fun loadCorbaRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            val allRecipes = fileHelper.readRecipesFromAssets( "corbatarif.txt")

            // Sadece belirli malzemeleri içeren tarifleri filtrele
            val filteredRecipes = allRecipes.filter { recipe ->
                ingredients.any { ingredient -> recipe.materials.contains(ingredient, ignoreCase = true) }
            }

            _corbaRecipes.postValue(filteredRecipes)
        }
    }
}