package com.example.fetchawslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val groupedItems: Map<Int, List<ResponseDataListItem>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    private val itemList: List<Any> = groupedItems.flatMap { (listId, items) ->
        listOf(listId) + items
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position] is Int) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_HEADER) {
            val headerView = inflater.inflate(R.layout.group_view, parent, false)
            GroupHeaderViewHolder(headerView)
        } else {
            val itemView = inflater.inflate(R.layout.item_view, parent, false)
            ItemViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GroupHeaderViewHolder) {
            holder.bind(itemList[position] as Int)
        } else if (holder is ItemViewHolder) {
            holder.bind(itemList[position] as ResponseDataListItem)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class GroupHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.headerTextView)

        fun bind(listId: Int) {
            headerTextView.text = "List ID: $listId"
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewListId: TextView = itemView.findViewById(R.id.textViewListId)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)

        fun bind(item: ResponseDataListItem) {
            textViewListId.text = "List ID: ${item.listId}"
            textViewName.text = item.name
        }
    }
}