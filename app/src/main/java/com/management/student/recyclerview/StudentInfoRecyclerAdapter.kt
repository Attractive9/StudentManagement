package com.management.student.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.management.student.activity.AdminNoteActivity
import com.management.student.data.StudentInfo
import com.management.student.databinding.ListStudentInfoBinding

class StudentInfoRecyclerAdapter(data: ArrayList<StudentInfo>):
    RecyclerView.Adapter<StudentInfoRecyclerAdapter.StudentInfoViewHolder>(){
    private val mData: ArrayList<StudentInfo>
    private lateinit var context: Context

    init {
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentInfoViewHolder {

        context = parent.context
        // create a new view-

        return StudentInfoViewHolder(ListStudentInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudentInfoViewHolder, position: Int) {
        val mBinding = holder.mBinding
        bind(mData[position], mBinding)
    }

    private fun bind(item: StudentInfo, mBinding: ListStudentInfoBinding) {
        mBinding.txtStudentId.text = item.studentId
        mBinding.txtStudentName.text = item.studentName
        mBinding.txtStudentSymptom.text = item.studentSymptom
        mBinding.txtStudentDate.text = item.date


        mBinding.root.setOnClickListener {
            val intent = Intent(mBinding.root.context, AdminNoteActivity :: class.java)
            intent.putExtra("studentInfo", item)
            mBinding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = mData.size

    inner class StudentInfoViewHolder(val mBinding: ListStudentInfoBinding): RecyclerView.ViewHolder(mBinding.root)
}