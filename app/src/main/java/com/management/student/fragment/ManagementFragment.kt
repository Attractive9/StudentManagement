package com.management.student.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.management.student.data.StudentInfo
import com.management.student.databinding.FragmentManagementBinding
import com.management.student.recyclerview.StudentInfoRecyclerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManagementFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val mList = ArrayList<StudentInfo>()
    private val mAdapter = StudentInfoRecyclerAdapter(mList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mList.add(StudentInfo("학번", "이름", "증상", "날짜"))
        val mBinding = FragmentManagementBinding.inflate(inflater, container, false).apply {
            viewStudentInfo.adapter = mAdapter
            viewStudentInfo.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }

        val database = Firebase.database
        val myRef = database.getReference("users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터 변경 시 호출되는 콜백
                mList.clear()
                mList.add(StudentInfo("학번", "이름", "증상", "날짜"))
                for (snapshot in dataSnapshot.children) {
                    if(snapshot.child("isAdmin").getValue(Boolean ::class.java) != false) continue
                    mList.add(StudentInfo(snapshot.child("id").getValue(String ::class.java) ?: "",
                        snapshot.child("name").getValue(String ::class.java) ?: "",
                        snapshot.child("symptom").getValue(String ::class.java) ?: "",
                        "1"))
                }
                mAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터 조회가 취소되었을 때 호출되는 콜백
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

        return mBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManagementFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManagementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}