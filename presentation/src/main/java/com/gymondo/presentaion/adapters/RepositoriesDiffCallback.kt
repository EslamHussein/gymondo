package com.gymondo.presentaion.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gymondo.presentaion.model.RepositoryView

class RepositoriesDiffCallback(
    private val newRepositories: List<RepositoryView>,
    private val oldRepositories: List<RepositoryView>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldRepositories.size
    }

    override fun getNewListSize(): Int {
        return newRepositories.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRepositories[oldItemPosition].id == newRepositories[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRepositories[oldItemPosition] == newRepositories[newItemPosition]
    }
}