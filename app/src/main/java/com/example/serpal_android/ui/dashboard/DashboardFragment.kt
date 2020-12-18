package com.example.serpal_android.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.serpal_android.R
import com.google.android.material.textfield.TextInputEditText


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val button: Button = root.findViewById(R.id.button)
        button.setOnClickListener(View.OnClickListener {
            val text : TextInputEditText = root.findViewById(R.id.input)
            val resultText : EditText = root.findViewById(R.id.resulttext)
            if(text.text.toString().equals("Example")){
                resultText.setText("Példa", TextView.BufferType.EDITABLE)
            }else{
                resultText.setText("Translated: " + text.text.toString(), TextView.BufferType.EDITABLE)
            }

        })

        return root
    }
}