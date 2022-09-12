package com.exam.acronym.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exam.acronym.R
import com.exam.acronym.databinding.RowItemBinding
import com.exam.acronym.databinding.RowVarItemBinding
import com.exam.acronym.network.Response

class LfsItemListAdapter: ListAdapter<Response.LfsItem, LfsItemListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowItemBinding = RowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object DiffCallback: DiffUtil.ItemCallback<Response.LfsItem>() {
        override fun areItemsTheSame(
            oldItem: Response.LfsItem,
            newItem: Response.LfsItem
        ): Boolean {
            return oldItem.lf == newItem.lf
        }

        override fun areContentsTheSame(
            oldItem: Response.LfsItem,
            newItem: Response.LfsItem
        ): Boolean {
            return oldItem.freq == newItem.freq && oldItem.since == newItem.since && oldItem.vars == newItem.vars
        }
    }

    class ViewHolder(private val binding: RowItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Response.LfsItem) {
            binding.contentLf.text = item.lf
            binding.contentFreq.text = item.freq.toString()
            binding.contentSince.text = item.since.toString()

            // Setup var view
            binding.varView.removeAllViews()
            item.vars.forEach {
                val varBinding = RowVarItemBinding.inflate(
                    LayoutInflater.from(binding.root.context),
                    binding.varView,
                    false
                )
                varBinding.contentLf.text = it.lf
                varBinding.contentFreq.text = it.freq.toString()
                varBinding.contentSince.text = it.since.toString()

                binding.varView.addView(varBinding.root)
            }

            if (item.showVars) {
                binding.detailView.expand(false)
                binding.showDetails.setText(R.string.hide_vars)
            } else {
                binding.detailView.collapse(false)
                binding.showDetails.setText(R.string.show_vars)
            }

            binding.showDetails.setOnClickListener {
                if (binding.detailView.isExpanded) {
                    binding.detailView.collapse()
                    binding.showDetails.setText(R.string.show_vars)
                    item.showVars = false
                } else {
                    binding.detailView.expand()
                    binding.showDetails.setText(R.string.hide_vars)
                    item.showVars = true
                }
            }

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "Clicked the item: " + item.lf, Toast.LENGTH_SHORT).show()
            }
        }
    }

}