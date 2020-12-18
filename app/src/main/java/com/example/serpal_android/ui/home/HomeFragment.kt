package com.example.serpal_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.serpal_android.MainActivity
import com.example.serpal_android.R
import com.example.serpal_android.ScrollingFragment
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    var client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val list : LinearLayout = root.findViewById(R.id.books)
        run(root, list)


        return root
    }

    fun run(root: View, list : LinearLayout) {
        val request = Request.Builder()
            .url("http://10.0.2.2:8000/api/books/")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }

                    val obj = JSONArray(response.body!!.string())

                    for(i in 0 .. obj.length()-1){
                        val button = Button(root.context)
                        button.text = JSONObject(obj.getString(i)).getString("name")
                        button.tag = JSONObject(obj.getString(i))
                        button.setOnClickListener {
                            (activity as MainActivity).button = button
                           activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.homefragment, ScrollingFragment(), "findThisFragment")
                                ?.addToBackStack(null)
                                ?.commit();
                            list.visibility = ListView.GONE
                        }
                        activity?.runOnUiThread(Runnable {
                            list.addView(button, LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ))
                        })


                     }


                }
            }
        })
    }
}