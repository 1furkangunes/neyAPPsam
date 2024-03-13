package com.nfg.yemektarifi

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CorbaRecipeAdapter(
    private val itemClickListener: (RecipeModel) -> Unit
) : RecyclerView.Adapter<CorbaRecipeAdapter.ViewHolder>() {

    private val recipes = mutableListOf<RecipeModel>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(recipe: RecipeModel) {
            // Tarife ait bilgileri görüntüle
            val recipeNameTextView: TextView = itemView.findViewById(R.id.recipeNameTextView)
            recipeNameTextView.text = recipe.name
            recipeNameTextView.setTextColor(Color.BLACK)

            // Tarife tıklandığında listener'ı çağır
            itemView.setOnClickListener {
                itemClickListener.invoke(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Tarif verilerini onBindViewHolder içinde göster
        holder.bind(recipes[position])
    }

    fun setData(recipes: List<RecipeModel>) {
        // Adapter içindeki veri listesini güncelle ve ekrandaki verileri yeniden çiz
        this.recipes.clear()
        this.recipes.addAll(recipes)
        notifyDataSetChanged()
    }
}
