package com.example.mobileclient.presentation.adapters.recycleview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileclient.databinding.ItemShopBinding

class SearchResultAdapter() : RecyclerView.Adapter<SearchResultAdapter.ItemViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    class ItemViewHolder(   //FIXME будет сделано на два холдера
        val binding: ItemShopBinding,
    ) : RecyclerView.ViewHolder(binding.root)
}

