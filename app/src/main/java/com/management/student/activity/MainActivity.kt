package com.management.student.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.management.student.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.btnSignIn.setOnClickListener {
            // TODO : 구글 로그인 으로 변경 해야 함
            val intent = Intent(applicationContext, InfoActivity :: class.java)
            startActivity(intent)
        }
    }

}