package com.management.student.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.management.student.R
import com.management.student.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private val mBinding: ActivityInfoBinding by lazy { ActivityInfoBinding.inflate(layoutInflater)}

    private lateinit var sharedPref: SharedPreferences
    private var isAdmin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        checkUserID()
        checkIsAdmin()

        mBinding.btnApply.setOnClickListener {
            if(!makeUser()) return@setOnClickListener
            val activity = if(isAdmin){ AdminActivity :: class.java } else { NoteActivity :: class.java }
            val intent = Intent(applicationContext, activity)
            startActivity(intent)
        }
    }

    private fun checkIsAdmin() {
        val database = Firebase.database
        val myRef = database.getReference("admin")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터 변경 시 호출되는 콜백
                val uid = sharedPref.getString("UID", null)
                for (snapshot in dataSnapshot.children) {
                    if(snapshot.child("UID").getValue(String ::class.java) == uid) {
                        val editor = sharedPref.edit()
                        editor.putBoolean("isAdmin", true)
                        editor.apply()
                        isAdmin = true
                        mBinding.btnApply.text = if(isAdmin) {getString(R.string.txt_admin) } else { getString(R.string.txt_save) }
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터 조회가 취소되었을 때 호출되는 콜백
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun makeUser(): Boolean {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(if(isAdmin) {"admin"} else {"users"})

        if(!checkUserID()) return false

        val editor = sharedPref.edit()
        editor.putString("id", mBinding.editStudentId.text.toString())
        editor.putString("name", mBinding.editStudentName.text.toString())
        editor.apply()

        //회원 등록
        val user = HashMap<String, Any>()
        val uid = sharedPref.getString("UID", "")!!
        user["UID"] = uid
        user["id"] = mBinding.editStudentId.text.toString()
        user["name"] = mBinding.editStudentName.text.toString()
        user["symptom"] = ""

        myRef.child(uid).setValue(user)

        return true
    }

    private fun checkUserID(): Boolean {
        if(sharedPref.getString("UID", null) == null) {
            startActivity(Intent(applicationContext, LogInActivity::class.java))
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        checkUserID()
        checkIsAdmin()
        mBinding.editStudentId.setText(sharedPref.getString("id", ""))
        mBinding.editStudentName.setText(sharedPref.getString("name", ""))
        mBinding.btnApply.text = if(isAdmin) {getString(R.string.txt_admin) } else { getString(R.string.txt_save) }
    }
}