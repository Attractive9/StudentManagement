package com.management.student.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.management.student.data.Student
import com.management.student.databinding.ListStudentBinding

class StudentRecyclerAdapter(data: ArrayList<Student>):
    RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder>(){
    private val mData: ArrayList<Student>
    private lateinit var context: Context

    init {
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {

        context = parent.context
        // create a new view-

        return StudentViewHolder(ListStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val mBinding = holder.mBinding
        bind(mData[position], mBinding)
    }

    private fun bind(item: Student, mBinding: ListStudentBinding) {
        mBinding.txtStudentName.text = item.studentName
    }

    override fun getItemCount(): Int = mData.size

    inner class StudentViewHolder(val mBinding: ListStudentBinding): RecyclerView.ViewHolder(mBinding.root)
}