package ru.netology.nework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nework.databinding.FragmentCardJobBinding
import ru.netology.nework.dto.Job
import java.text.SimpleDateFormat
import java.util.Date

interface OnInteractionListenerJob {
    fun onEdit(job: Job) {}
    fun onRemove(job: Job) {}
    fun onLink(job: Job) {}
    fun myOrNo(job: Job):Boolean {return true}
}

class JobAdapter(
    private val onInteractionListener: OnInteractionListenerJob
) : ListAdapter<Job,JobViewHolder>(JobDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding =
            FragmentCardJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = getItem(position)
        holder.renderingPostStructure(job)
    }
}

class JobDiffCallback : DiffUtil.ItemCallback<Job>() {
    override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
        return oldItem == newItem
    }
}

class JobViewHolder(
    private val binding: FragmentCardJobBinding,
    private val onInteractionListener: OnInteractionListenerJob,
) : RecyclerView.ViewHolder(binding.root) {


    fun renderingPostStructure(job: Job) {
        with(binding) {
            jobOrganization.text = job.name
            jobPosition.text = job.position

            val startingDate = job.start?.let {
                it.substring(8..9) + it.substring(4..7) + it.substring(0..3)
            } ?: "..."  // значение по умолчанию, если job.start == null

            val endingDate = job.finish?.let {
                it.substring(8..9) + it.substring(4..7) + it.substring(0..3)
            } ?: "..."  // значение по умолчанию, если job.finish == null

            val workingPeriod = "$startingDate\n$endingDate"
            jobWorking.text = workingPeriod

            jobLink.isVisible = (job.link != null)
            if (job.link != null) {
                jobLink.text = job.link
            }
            jobEdit.isVisible = onInteractionListener.myOrNo(job)
            jobRemove.isVisible = onInteractionListener.myOrNo(job)

            postListeners(job)
        }
    }

    private fun postListeners(job: Job) {
        with(binding) {

            jobLink.setOnClickListener {
                onInteractionListener.onLink(job)
            }
            jobEdit.setOnClickListener{
                onInteractionListener.onEdit(job)
            }
            jobRemove.setOnClickListener{
                onInteractionListener.onRemove(job)
            }
        }
    }
}