package com.management.student.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.management.student.data.Note
import com.management.student.databinding.ListNoteBinding
import kotlin.collections.ArrayList

class NoteRecyclerAdapter(data: ArrayList<Note>):
    RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>(){
    private val mData: ArrayList<Note>

    init {
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(ListNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val mBinding = holder.mBinding
        bind(mData[position], mBinding)
    }

    private fun bind(item: Note, mBinding: ListNoteBinding) {
        mBinding.txtStudentName.text = item.name
        mBinding.txtMessage.text = item.message
        mBinding.txtDate.text = item.date
    }

    override fun getItemCount(): Int = mData.size

    inner class NoteViewHolder(val mBinding: ListNoteBinding): RecyclerView.ViewHolder(mBinding.root)
}