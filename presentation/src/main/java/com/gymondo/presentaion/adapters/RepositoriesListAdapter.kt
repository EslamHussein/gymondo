package com.gymondo.presentaion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.gymondo.presentaion.R
import com.gymondo.presentaion.model.RepositoryView

class RepositoriesListAdapter(
    private var data: List<RepositoryView> = emptyList(),
    private var onRepositoryClicked: OnRepositoryClickListener?
) :
    RecyclerView.Adapter<RepositoriesListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.repository_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = data[position]
        holder.ownerNameTextView.text = repository.owner.name
        holder.repositoryNameTextView.text = repository.name

        holder.avatarImageView.load(repository.owner.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        holder.itemView.setOnClickListener {
            onRepositoryClicked?.onItemClicked(repository)
        }
    }

    fun updateData(updatedList: List<RepositoryView>) {

        val diffResult = DiffUtil.calculateDiff(
            RepositoriesDiffCallback(
                updatedList,
                data
            )
        )
        this.data = updatedList
        diffResult.dispatchUpdatesTo(this)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImageView: ImageView
        var ownerNameTextView: TextView
        var repositoryNameTextView: TextView

        init {
            avatarImageView = view.findViewById(R.id.ownerAvatarImageView)
            ownerNameTextView = view.findViewById(R.id.ownerNameTextView)
            repositoryNameTextView = view.findViewById(R.id.repositoryNameTextView)
        }
    }

    interface OnRepositoryClickListener {
        fun onItemClicked(repository: RepositoryView)
    }


}