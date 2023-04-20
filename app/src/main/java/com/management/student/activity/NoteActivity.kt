package com.management.student.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.management.student.data.Note
import com.management.student.databinding.ActivityNoteBinding
import com.management.student.recyclerview.NoteRecyclerAdapter

class NoteActivity : AppCompatActivity() {

    private val mList = ArrayList<Note>()
    private val mAdapter = NoteRecyclerAdapter(mList)

    private val mBinding: ActivityNoteBinding by lazy { ActivityNoteBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.btnSave.setOnClickListener {
            // TODO: 증상 저장 시 데이터 베이스 수정
        }

        // 데이터 받아와야 함
        test()
        
        mBinding.apply {
            viewNote.adapter = mAdapter
            viewNote.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        }
    }
    
    private fun test() {
        for(i in 0 .. 10) {
            mList.add(Note("준구", "안녕하세요", 0))
        }
        mAdapter.notifyDataSetChanged()
    }
}