package me.blackout.mathsapp.ui.hardmode

import android.widget.EditText
import androidx.lifecycle.ViewModel

class HardModeViewModel : ViewModel() {
    lateinit var score: Number;
    lateinit var question: EditText;
    lateinit var answer: EditText;
    lateinit var currentAnswer: Number;
    lateinit var questioncount: Number;
}