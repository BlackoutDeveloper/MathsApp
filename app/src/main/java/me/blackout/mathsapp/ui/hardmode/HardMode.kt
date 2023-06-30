package me.blackout.mathsapp.ui.hardmode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import me.blackout.mathsapp.R
import me.blackout.mathsapp.databinding.FragmentHardModeBinding
import me.blackout.mathsapp.databinding.FragmentMediumModeBinding
import me.blackout.mathsapp.ui.mediummode.MediumModeViewModel
import java.util.Random

class HardMode : Fragment() {

    companion object {
        fun newInstance() = HardMode()
    }


    private var _binding: FragmentHardModeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: HardModeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel =
            ViewModelProvider(this).get(HardModeViewModel::class.java)

        _binding = FragmentHardModeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.question = binding!!.QuestionBox
        viewModel.answer = binding!!.AnswerBox
        viewModel.score = 0
        viewModel.questioncount = 1

        _binding!!.Score.text = "0".toEditable();
        _binding!!.QLeft.text = "19".toEditable()

        var (q, a) = createQuestion()

        viewModel.question.text = q.toEditable()
        viewModel.currentAnswer = a;

        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                var (q, a) = createQuestion()

                viewModel.question.text = q.toEditable();
                viewModel.currentAnswer = a;
                viewModel.answer.text = "".toEditable();
            }
        }

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
                viewModel.score =+ 1
            }

            if (viewModel.answer.text.toString().trim() != viewModel.currentAnswer.toString().trim()) {
                Snackbar.make(requireView(), "Incorrect Answer...", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            timer.cancel()

            var (q, a) = createQuestion()

            viewModel.question.text = q.toEditable()
            viewModel.currentAnswer = a;
            viewModel.answer.text = "".toEditable()
            viewModel.questioncount =+ 1
            _binding!!.QLeft.text = (20 - viewModel.questioncount.toInt()).toString().toEditable()



            timer.start()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HardModeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun createQuestion(): Pair<String, Number> {
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

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}