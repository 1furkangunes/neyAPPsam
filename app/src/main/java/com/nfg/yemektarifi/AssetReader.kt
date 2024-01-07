package com.nfg.yemektarifi

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

data class RecipeModel(
    val name: String,
    val materials: String,
    val recipe: String
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(materials)
        parcel.writeString(recipe)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeModel> {
        override fun createFromParcel(parcel: Parcel): RecipeModel {
            return RecipeModel(parcel)
        }

        override fun newArray(size: Int): Array<RecipeModel?> {
            return arrayOfNulls(size)
        }
    }
}

class AssetReader(private val context: Context) {

    fun readRecipesFromAssets(fileName: String): List<RecipeModel> {
        val recipes = mutableListOf<RecipeModel>()

        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var line: String?
            var recipeName = ""
            var materials = StringBuilder()
            var isRecipeSection = false
            var recipeText = StringBuilder()

            while (reader.readLine().also { line = it } != null) {
                when {
                    line!!.startsWith("Yapılışı:") -> {
                        isRecipeSection = true
                        recipeText.append(line!!.substringAfter("Yapılışı:")).append("\n")
                    }
                    line!!.isNotEmpty() -> {
                        if (isRecipeSection) {
                            recipeText.append("$line\n")
                        } else {
                            if (recipeName.isEmpty()) {
                                recipeName = line!!
                            } else {
                                materials.append("$line\n")
                            }
                        }
                    }
                    else -> { // Boş bir satır okuduğunuzda
                        if (recipeName.isNotEmpty() && materials.isNotEmpty() && recipeText.isNotEmpty()) {
                            val recipe = RecipeModel(recipeName.trim(), materials.toString().trim(), recipeText.toString().trim())
                            recipes.add(recipe)
                        }
                        // Değişkenleri sıfırlayın
                        recipeName = ""
                        materials = StringBuilder()
                        isRecipeSection = false
                        recipeText = StringBuilder()
                    }
                }
            }
            // Dosyanın sonunda kalan son tarifi ekleyin
            if (recipeName.isNotEmpty() && materials.isNotEmpty() && recipeText.isNotEmpty()) {
                val recipe = RecipeModel(recipeName.trim(), materials.toString().trim(), recipeText.toString().trim())
                recipes.add(recipe)
            }
            reader.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return recipes
    }
}

