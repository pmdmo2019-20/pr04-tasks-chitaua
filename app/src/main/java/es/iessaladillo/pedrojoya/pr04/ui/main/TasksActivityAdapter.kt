package es.iessaladillo.pedrojoya.pr04.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr04.R
import es.iessaladillo.pedrojoya.pr04.data.entity.Task
import es.iessaladillo.pedrojoya.pr04.utils.strikeThrough
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.tasks_activity_item.*
import java.text.SimpleDateFormat
import java.util.*

class TasksActivityAdapter : RecyclerView.Adapter<TasksActivityAdapter.ViewHolder>() {

    private var data: List<Task> = emptyList()
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var onItemCheckBoxListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.tasks_activity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setOnItemClickListener(listener: ((Int) -> Unit)) {
        onItemClickListener = listener
    }

    fun setOnItemCheckBoxListener(listener: ((Int) -> Unit)) {
        onItemCheckBoxListener = listener
    }

    fun getItem(position: Int): Task {
        return data[position]
    }

    fun submitList(tasks: List<Task>) {
        data = tasks
        notifyDataSetChanged()
    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
            chkCompleted.setOnClickListener {
                onItemCheckBoxListener?.invoke(adapterPosition)
            }
        }

        fun bind(task: Task) {
            lblConcept.text = task.concept
            chkCompleted.isChecked = task.completed
            lblConcept.strikeThrough(task.completed)
            if (task.completed) {
                task.completedAt = "Completed at ${SimpleDateFormat(
                    "dd/MM/yyyy HH:mm:ss",
                    Locale.getDefault()
                ).format(Date())}"
                //viewBar.background = R.color.colorCompletedTask.toDrawable()
                viewBar.background = viewBar.resources.getDrawable(R.color.colorCompletedTask)
                lblCompleted.text = task.completedAt
            } else {
                //viewBar.background = R.color.colorPendingTask.toDrawable()
                viewBar.background = viewBar.resources.getDrawable(R.color.colorPendingTask)
                lblCompleted.text = task.createdAt
            }

        }
    }
}
