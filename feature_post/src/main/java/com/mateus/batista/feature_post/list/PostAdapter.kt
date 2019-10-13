package com.mateus.batista.feature_post.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateus.batista.base_feature.listeners.OnItemClickListener
import com.mateus.batista.feature_post.R
import com.mateus.batista.feature_post.model.ListPost
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(
    private val list: List<ListPost>,
    private val listener: OnItemClickListener<ListPost>
) :  RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindItemView(item){ listener.onItemClick(item, position) }
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItemView(item: ListPost, onItemClick: () -> Unit) {
            view.apply {
                title.text = item.title
                setOnClickListener { onItemClick() }
            }
        }
    }
}