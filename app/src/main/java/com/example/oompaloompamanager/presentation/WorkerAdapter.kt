package com.example.oompaloompamanager.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.oompaloompamanager.R
import com.example.oompaloompamanager.commons.loadImage
import com.example.oompaloompamanager.databinding.ItemListBinding
import com.example.oompaloompamanager.presentation.models.OompaLoompaViewData

class WorkerAdapter(
    private val navController: NavController,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    private val adapterList =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<OompaLoompaViewData>() {
            override fun areItemsTheSame(
                oldItem: OompaLoompaViewData,
                newItem: OompaLoompaViewData
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: OompaLoompaViewData,
                newItem: OompaLoompaViewData
            ): Boolean {
                return oldItem.firstName == newItem.firstName &&
                        oldItem.lastName == newItem.lastName &&
                        oldItem.gender == newItem.gender &&
                        oldItem.image == newItem.image &&
                        oldItem.profession == newItem.profession &&
                        oldItem.email == newItem.email &&
                        oldItem.age == newItem.age &&
                        oldItem.height == newItem.height &&
                        oldItem.favoriteColor == newItem.favoriteColor &&
                        oldItem.favoriteFood == newItem.favoriteFood
            }
        })

    fun submitList(workerList: List<OompaLoompaViewData>) {
        adapterList.submitList(workerList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        return WorkerViewHolder(
            navController,
            viewModel,
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
        private val navController: NavController,
        private val viewModel: MainViewModel,
        private val itemListBinding: ItemListBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(itemListBinding.root) {

        fun bind(worker: OompaLoompaViewData) {
            with(itemListBinding) {

                clListItem.setOnClickListener {
                    viewModel.selectWorker(worker.id)
                    navController.navigate(R.id.action_list_fragment_to_detail_fragment)
                }

                ivProfileImage.loadImage(
                    itemView,
                    worker.image,
                    R.drawable.ic_void_image,
                    R.drawable.ic_broken_image
                )

                tvName.text = context.getString(
                    R.string.worker_name,
                    worker.firstName,
                    worker.lastName
                )
                tvProfession.text = worker.profession
                tvGender.text = worker.gender
            }
        }

    }
}