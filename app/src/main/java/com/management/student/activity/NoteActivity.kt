package com.management.student.activity

import android.content.Context
import android.content.SharedPreferences
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
import com.management.student.databinding.ActivityNoteBinding
import com.management.student.recyclerview.NoteRecyclerAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NoteActivity : AppCompatActivity() {

    private val mList = ArrayList<Note>()
    private val mAdapter = NoteRecyclerAdapter(mList)

    private lateinit var sharedPref: SharedPreferences
    private var name = ""

    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")
    private val chatRef = database.getReference("chat")

    private val dateFormat = SimpleDateFormat("MM월 dd일 HH:mm", Locale.getDefault())

    private val mBinding: ActivityNoteBinding by lazy { ActivityNoteBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        name = sharedPref.getString("name", "") ?: ""

        mBinding.apply {
            viewNote.adapter = mAdapter
            viewNote.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

            btnSave.setOnClickListener {

                val editor = sharedPref.edit()
                editor.putString("symptom", mBinding.editSymptom.text.toString())
                editor.apply()

                val user = HashMap<String, Any>()
                val uid = sharedPref.getString("UID", "") ?: ""
                user["UID"] = uid
                user["id"] = sharedPref.getString("id", "") ?: ""
                user["name"] = sharedPref.getString("name", "") ?: ""
                user["symptom"] = mBinding.editSymptom.text.toString()
                user["date"] = dateFormat.format(Date())

                userRef.child(uid).setValue(user)
            }

            btnSubmit.setOnClickListener {
                val chat = HashMap<String, Any>()
                chat["message"] = mBinding.editSubmit.text.toString()
                chat["name"] = name
                chat["date"] = dateFormat.format(Date())
                chatRef.child(name).push().setValue(chat)
                mBinding.editSymptom.text.clear()
            }
        }

        chatRef.addValueEventListener(object : ValueEventListener {
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

    override fun onStart() {
        super.onStart()
        mBinding.editSymptom.setText(sharedPref.getString("symptom", "")!!)
    }
}