package me.blackout.mathsapp.ui.easymode

import android.widget.EditText
import androidx.lifecycle.ViewModel

class EasyModeViewModel : ViewModel() {
    lateinit var score: Number;
    lateinit var question: EditText;
    lateinit var answer: EditText;
    lateinit var currentAnswer: Number;
    lateinit var questioncount: Number;
}