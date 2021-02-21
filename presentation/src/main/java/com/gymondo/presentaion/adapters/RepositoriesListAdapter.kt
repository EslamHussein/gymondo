package com.gymondo.presentaion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gymondo.presentaion.R
import com.gymondo.presentaion.model.RepositoryView

class RepositoriesListAdapter constructor(private var onProjectClicked: OnProjectClickListener?) :
    AbstractPagingAdapter<RepositoryView, RepositoriesListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.repository_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = data[position]
        holder.ownerNameText.text = project.owner.name
        holder.projectNameText.text = project.name

/*
        Glide.with(holder.itemView.context)
            .load(project.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.avatarImage)
*/


        holder.itemView.setOnClickListener {
            onProjectClicked?.onItemClickListener(project)

        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatarImage: ImageView
        var ownerNameText: TextView
        var projectNameText: TextView

        init {
            avatarImage = view.findViewById(R.id.ownerAvatarImageView)
            ownerNameText = view.findViewById(R.id.ownerNameTextView)
            projectNameText = view.findViewById(R.id.projectNameTextView)
        }
    }

    interface OnProjectClickListener {
        fun onItemClickListener(project: RepositoryView)
    }
}