package me.blackout.mathsapp.ui.easymode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import me.blackout.mathsapp.R
import me.blackout.mathsapp.databinding.FragmentEasyModeBinding
import me.blackout.mathsapp.databinding.FragmentHomeBinding
import me.blackout.mathsapp.ui.home.HomeViewModel
import java.util.Random

class EasyMode : Fragment() {

    companion object {
        fun newInstance() = EasyMode()
    }


    private var _binding: FragmentEasyModeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: EasyModeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel =
            ViewModelProvider(this).get(EasyModeViewModel::class.java)

        _binding = FragmentEasyModeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.question = binding!!.QuestionBox
        viewModel.answer = binding!!.AnswerBox
        viewModel.score = 0
        viewModel.questioncount = 0

        var (q, a) = createQuestion()

        viewModel.question.text = q.toEditable()
        viewModel.currentAnswer = a;

        binding.button.setOnClickListener {

            if(viewModel.questioncount == 20) {
                Snackbar.make(requireView(), "Game Over, You got ${viewModel.score}/20!!!", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (viewModel.answer.text.isNullOrEmpty()) {
                Snackbar.make(requireView(), "Missing Answer...", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (viewModel.answer.text.toString().trim() == viewModel.currentAnswer.toString().trim()) {
                Snackbar.make(requireView(), "Correct Answer...", Snackbar.LENGTH_LONG).show()
                viewModel.score = viewModel.score as Int + 1
            }

            if (viewModel.answer.text.toString().trim() != viewModel.currentAnswer.toString().trim()) {
                Snackbar.make(requireView(), "Incorrect Answer...", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var (q, a) = createQuestion()

            viewModel.question.text = q.toEditable()
            viewModel.currentAnswer = a;
            viewModel.answer.text = "".toEditable()
            viewModel.questioncount = viewModel.questioncount as Int + 1

            _binding!!.QLeft.text = (20 - viewModel.questioncount.toInt()).toString().toEditable()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EasyModeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun createQuestion(): Pair<String, Number> {
        var method = (1..4).random();
        when (method) {
            1 -> {
                var varone = (1..6).random();
                var vartwo = (1..6).random();

                return "$varone + $vartwo" to varone + vartwo
            }
            2 -> {
                var varone = (1..12).random();
                var vartwo = (1..11).random();

                if (varone < vartwo) {
                    return "$vartwo - $varone" to vartwo - varone
                }

                return "$varone - $vartwo" to varone - vartwo
            }
            3 -> {
                var varone = (1..6).random();
                var vartwo = (1..4).random();

                if (varone >= 5) {
                    return "$varone * 2" to varone * 2
                }

                return "$varone * $vartwo" to varone * vartwo
            }
            4 -> {
                var varone = (1..12).random();
                var vartwo = (1..12).random();

                return "$varone / $vartwo" to varone / vartwo
            }
        }
        return "" to -1;
    }

    private fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}