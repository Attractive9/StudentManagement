package com.management.student.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.management.student.data.Note
import com.management.student.databinding.ActivityNoteBinding
import com.management.student.recyclerview.NoteRecyclerAdapter

class NoteActivity : AppCompatActivity() {

    private val mList = ArrayList<Note>()
    private val mAdapter = NoteRecyclerAdapter(mList)

    private lateinit var sharedPref: SharedPreferences

    private val mBinding: ActivityNoteBinding by lazy { ActivityNoteBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        mBinding.btnSave.setOnClickListener {
            // TODO: 증상 저장 시 데이터 베이스 수정

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("users")
            val editor = sharedPref.edit()
            editor.putString("symptom", mBinding.editSymptom.text.toString())
            editor.apply()

            val user = HashMap<String, Any>()
            val uid = sharedPref.getString("UID", "")!!
            user["UID"] = uid
            user["id"] = sharedPref.getString("id", "")!!
            user["name"] = sharedPref.getString("name", "")!!
            user["symptom"] = mBinding.editSymptom.text.toString()
            user["isAdmin"] = sharedPref.getBoolean("isAdmin", false)

            myRef.child(uid).setValue(user)
        }

        // 데이터 받아와야 함
        test()
        
        mBinding.apply {
            viewNote.adapter = mAdapter
            viewNote.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        }
    }

    override fun onStart() {
        super.onStart()
        mBinding.editSymptom.setText(sharedPref.getString("symptom", "")!!)
    }
    
    private fun test() {
        for(i in 0 .. 10) {
            mList.add(Note("준구", "안녕하세요", 0))
        }
        mAdapter.notifyDataSetChanged()
    }
}