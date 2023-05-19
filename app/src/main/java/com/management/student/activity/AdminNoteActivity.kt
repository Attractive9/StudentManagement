package com.management.student.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.management.student.data.Note
import com.management.student.data.StudentInfo
import com.management.student.databinding.ActivityAdminNoteBinding
import com.management.student.recyclerview.NoteRecyclerAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AdminNoteActivity : AppCompatActivity() {

    private val mList = ArrayList<Note>()
    private val mAdapter = NoteRecyclerAdapter(mList)

    private lateinit var sharedPref: SharedPreferences
    private var name = ""

    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("chat")

    private val dateFormat = SimpleDateFormat("MM월 dd일 HH:mm", Locale.getDefault())

    private val mBinding: ActivityAdminNoteBinding by lazy { ActivityAdminNoteBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        name = sharedPref.getString("name", "") ?: ""

        val studentInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("studentInfo",StudentInfo::class.java)
        } else {
            intent.getSerializableExtra("studentInfo") as StudentInfo
        }

        mBinding.apply {
            viewNote.adapter = mAdapter
            viewNote.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            btnSubmit.setOnClickListener {
                // 텍스트 전송

                val chat = HashMap<String, Any>()
                chat["message"] = mBinding.editSubmit.text.toString()
                chat["name"] = name
                chat["date"] = dateFormat.format(Date())
                myRef.child(studentInfo!!.studentName).push().setValue(chat)
                mBinding.editSubmit.text.clear()
            }
        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터 변경 시 호출되는 콜백
                mList.clear()
                for (roomSnapshot in dataSnapshot.children) {
                    for (messageSnapshot in roomSnapshot.children) {
                        mList.add(Note(messageSnapshot.child("name").getValue(String ::class.java) ?: "",
                            messageSnapshot.child("message").getValue(String ::class.java) ?: "",
                            messageSnapshot.child("date").getValue(String ::class.java) ?: ""))
                    }
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터 조회가 취소되었을 때 호출되는 콜백
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}