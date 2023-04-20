package com.management.student.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.management.student.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private val mBinding: ActivityInfoBinding by lazy { ActivityInfoBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        // TODO: 관리자 권한 확인 후 버튼 활성화

        mBinding.btnAdmin.setOnClickListener {
            // TODO : 관리자 페이지 로 이동
            val intent = Intent(applicationContext, AdminActivity :: class.java)
            startActivity(intent)
        }

        mBinding.btnSave.setOnClickListener {
            // TODO : 학생 페이지 로 이동
            val intent = Intent(applicationContext, NoteActivity :: class.java)
            startActivity(intent)
        }
    }
}