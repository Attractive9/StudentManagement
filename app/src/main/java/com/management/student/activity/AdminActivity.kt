package com.management.student.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.management.student.ViewAdapter
import com.management.student.databinding.ActivityAdminBinding
import com.management.student.fragment.ConsultFragment
import com.management.student.fragment.ManagementFragment

class AdminActivity : AppCompatActivity() {

    private val mBinding: ActivityAdminBinding by lazy { ActivityAdminBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        setTab()
    }

    private fun setTab() {
        val adapter = ViewAdapter(supportFragmentManager)
        adapter.apply {
            addList(ConsultFragment())
            addList(ManagementFragment())
        }

        val texts = arrayOf("상담", "학생 관리")

        mBinding.apply {
            pagerAdmin.adapter = adapter
            tabAdmin.setupWithViewPager(mBinding.pagerAdmin)

            for (i in texts.indices) {
                val tab = tabAdmin.getTabAt(i)!!
                tab.text = texts[i]
            }
        }
    }
}