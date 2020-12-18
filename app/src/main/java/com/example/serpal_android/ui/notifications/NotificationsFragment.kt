package com.example.serpal_android.ui.notifications

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

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val button: Button = root.findViewById(R.id.button2)
        button.setOnClickListener(View.OnClickListener {
            val text : TextInputEditText = root.findViewById(R.id.input)
            val resultText : EditText = root.findViewById(R.id.resulttext2)
            if(text.text.toString().equals("Example")){
                resultText.setText("A thing characteristic of\nits kind or illustrating \na general rule.", TextView.BufferType.EDITABLE)
            }else{
                resultText.setText("Defined: " + text.text.toString(), TextView.BufferType.EDITABLE)
            }

        })
        return root
    }
}