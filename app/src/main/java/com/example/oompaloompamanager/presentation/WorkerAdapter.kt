package com.example.oompaloompamanager.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.oompaloompamanager.R
import com.example.oompaloompamanager.databinding.ItemListBinding
import com.example.oompaloompamanager.domain.models.OompaLoompa

class WorkerAdapter : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    private val adapterList = AsyncListDiffer(this, object : DiffUtil.ItemCallback<OompaLoompa>() {
        override fun areItemsTheSame(oldItem: OompaLoompa, newItem: OompaLoompa): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OompaLoompa, newItem: OompaLoompa): Boolean {
            return oldItem.firstName == newItem.firstName &&
                    oldItem.lastName == newItem.lastName &&
                    oldItem.gender == newItem.gender &&
                    oldItem.image == newItem.image &&
                    oldItem.profession == newItem.profession &&
                    oldItem.email == newItem.email &&
                    oldItem.age == newItem.age &&
                    oldItem.height == newItem.height &&
                    oldItem.additionalInfo.favoriteColor == newItem.additionalInfo.favoriteColor &&
                    oldItem.additionalInfo.favoriteFood == newItem.additionalInfo.favoriteFood
        }
    })

    fun submitList(workerList: List<OompaLoompa>) {
        adapterList.submitList(workerList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        return WorkerViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        holder.bind(adapterList.currentList[position])
    }

    override fun getItemCount(): Int = adapterList.currentList.size

    class WorkerViewHolder(
        private val itemListBinding: ItemListBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(itemListBinding.root) {

        fun bind(worker: OompaLoompa) {
            with(itemListBinding) {
                // TODO Cargar imágenes
//                ivProfileImage
                tvName.text = context.getString(
                    R.string.item_viewholder_worker_name,
                    worker.firstName,
                    worker.lastName
                )
                tvProfession.text = worker.profession
                tvGender.text = worker.gender.value
            }
        }

    }
}