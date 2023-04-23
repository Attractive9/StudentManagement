package com.management.student.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.management.student.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private val mBinding: ActivityInfoBinding by lazy { ActivityInfoBinding.inflate(layoutInflater)}

    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        // TODO: 관리자 권한 확인 후 버튼 활성화
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)


        mBinding.btnAdmin.setOnClickListener {
            // TODO : 관리자 페이지 로 이동
            if(!makeUser(isAdmin = true)) return@setOnClickListener
            val intent = Intent(applicationContext, AdminActivity :: class.java)
            startActivity(intent)
        }

        mBinding.btnSave.setOnClickListener {
            // TODO : 학생 페이지 로 이동
            if(!makeUser(isAdmin = false)) return@setOnClickListener
            val intent = Intent(applicationContext, NoteActivity :: class.java)
            startActivity(intent)
        }
    }

    private fun makeUser(isAdmin: Boolean): Boolean {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")

        if(!checkUserID()) return false

        val editor = sharedPref.edit()
        editor.putString("id", mBinding.editStudentId.text.toString())
        editor.putString("name", mBinding.editStudentName.text.toString())
        editor.putBoolean("isAdmin", isAdmin)
        editor.apply()

        val user = HashMap<String, Any>()
        val uid = sharedPref.getString("UID", "")!!
        user["UID"] = uid
        user["id"] = mBinding.editStudentId.text.toString()
        user["name"] = mBinding.editStudentName.text.toString()
        user["symptom"] = ""
        user["isAdmin"] = isAdmin

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
        mBinding.editStudentId.setText(sharedPref.getString("id", ""))
        mBinding.editStudentName.setText(sharedPref.getString("name", ""))
    }
}