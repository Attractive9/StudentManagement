package com.management.student.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.management.student.data.Note
import com.management.student.data.Student
import com.management.student.databinding.FragmentConsultBinding
import com.management.student.recyclerview.NoteRecyclerAdapter
import com.management.student.recyclerview.StudentRecyclerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConsultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val studentList = ArrayList<Student>()
    private val studentAdapter = StudentRecyclerAdapter(studentList)

    private val noteList = ArrayList<Note>()
    private val noteAdapter = NoteRecyclerAdapter(noteList)

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
        val mBinding = FragmentConsultBinding.inflate(inflater, container, false).apply {
            viewNote.adapter = noteAdapter
            viewStudent.adapter = studentAdapter

            viewNote.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            viewStudent.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

            studentList.add(Student("준구"))
            studentList.add(Student("준구"))
            studentList.add(Student("준구"))
            studentAdapter.notifyDataSetChanged()

            btnSubmit.setOnClickListener {
                noteList.add(Note("준구", editSubmit.text.toString(), 1))
                noteAdapter.notifyItemInserted(noteList.size)
                viewNote.scrollToPosition(noteList.size - 1)
            }
        }

        return mBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConsultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}