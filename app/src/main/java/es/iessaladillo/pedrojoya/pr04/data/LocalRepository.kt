package es.iessaladillo.pedrojoya.pr04.data

import es.iessaladillo.pedrojoya.pr04.data.entity.Task
import java.text.SimpleDateFormat
import java.util.*

object LocalRepository : Repository {

    private val tasks: MutableList<Task> = mutableListOf()

    override fun queryAllTasks(): List<Task> {
        return tasks.sortedByDescending { it.id }
    }

    override fun queryCompletedTasks(): List<Task> {
        return tasks.filter { x -> x.completed }.sortedByDescending { it.id }
    }

    override fun queryPendingTasks(): List<Task> {
        return tasks.filter { x -> !x.completed }.sortedByDescending { it.id }
    }

    override fun addTask(concept: String) {
        tasks.add(Task(giveIdToTask(), concept, "Created at ${setTime()}", false, ""))
    }

    override fun insertTask(task: Task) {
        tasks.add(task)
    }


    override fun deleteTask(taskId: Long) {
        var position: Int = -1
        tasks.forEach {
            if (it.id == taskId) {
                position = tasks.indexOf(it)
            }
        }
        if (position>-1){
            tasks.removeAt(position)
        }
    }

    override fun deleteTasks(taskIdList: List<Long>) {
        taskIdList.forEach {
            deleteTask(it)
        }
    }

    override fun markTaskAsCompleted(taskId: Long) {
        tasks.forEach {
            if (it.id == taskId) {
                it.completed = true
                it.completedAt = "Completed at ${setTime()}"
            }
        }
    }

    override fun markTasksAsCompleted(taskIdList: List<Long>) {
        taskIdList.forEach {
            markTaskAsCompleted(it)
        }
    }

    override fun markTaskAsPending(taskId: Long) {
        tasks.forEach {
            if (it.id == taskId) {
                it.completed = false
            }
        }
    }

    override fun markTasksAsPending(taskIdList: List<Long>) {
        taskIdList.forEach {
            markTaskAsPending(it)
        }
    }

    private fun giveIdToTask(): Long {
        val id: Long
        if (tasks.isEmpty()) {
            id = 1
        } else {
            id = tasks[tasks.size-1].id + 1
        }
        return id
    }

    private fun setTime(): String =
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
}
