package com.example.googlebooksapi

import android.os.Bundle
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentDisplayBooks: Fragment() {

    companion object {
        const val KEY_DATA_SET = "KEY_DATA_SET"
        fun newInstance(dataSet: BooksResponse): FragmentDisplayBooks {
            return FragmentDisplayBooks().also {
                var bundle = Bundle()
                bundle.putParcelable(KEY_DATA_SET, dataSet)
                it.arguments = bundle
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_display,
                container,
                false)
        arguments?.getParcelable<BooksResponse>(KEY_DATA_SET)?.let {data->
            view.findViewById<RecyclerView>(R.id.recycler_books).also {
                it.layoutManager =GridLayoutManager(activity,3)
                val adapter = BooksAdapter()
                it.adapter = adapter
                adapter.setDataSet(data)
            }

        }
        return view

    }
}

