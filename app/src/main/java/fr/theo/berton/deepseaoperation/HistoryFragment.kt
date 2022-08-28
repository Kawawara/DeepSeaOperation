package fr.theo.berton.deepseaoperation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import fr.theo.berton.deepseaoperation.databinding.FragmentHistoryBinding
import fr.theo.berton.deepseaoperation.databinding.FragmentWaitingRoomBinding

class HistoryFragment : Fragment() {

    private lateinit var homeAnimation: AnimationDrawable

    private var _binding: FragmentHistoryBinding? = null
    private lateinit var binding: FragmentHistoryBinding

            override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding = _binding!!

        return binding.root
                // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAnimation()

        val backButton = binding.quitButtonHistory

        backButton.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        homeAnimation.setExitFadeDuration(2000)
        homeAnimation.start()
    }

    private fun showAnimation() {
        binding.historyLayout.apply {
            setBackgroundResource(R.drawable.background_home_list)
            homeAnimation = background as AnimationDrawable
        }
    }

}