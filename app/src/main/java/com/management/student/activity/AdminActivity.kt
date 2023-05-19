package com.management.student.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.management.student.data.StudentInfo
import com.management.student.databinding.ActivityAdminBinding
import com.management.student.recyclerview.StudentInfoRecyclerAdapter

class AdminActivity : AppCompatActivity() {

    private val mBinding: ActivityAdminBinding by lazy { ActivityAdminBinding.inflate(layoutInflater)}


    private val mList = ArrayList<StudentInfo>()
    private val mAdapter = StudentInfoRecyclerAdapter(mList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)


        // Inflate the layout for this fragment
        mList.add(StudentInfo("학번", "이름", "증상", "날짜"))
        mBinding.apply {
            viewStudentInfo.adapter = mAdapter
            viewStudentInfo.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        }

        val database = Firebase.database
        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터 변경 시 호출되는 콜백
                mList.clear()
                mList.add(StudentInfo("학번", "이름", "증상", "날짜"))
                for (snapshot in dataSnapshot.children) {
                    mList.add(StudentInfo(snapshot.child("id").getValue(String ::class.java) ?: "",
                        snapshot.child("name").getValue(String ::class.java) ?: "",
                        snapshot.child("symptom").getValue(String ::class.java) ?: "",
                        snapshot.child("date").getValue(String ::class.java) ?: "",))
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