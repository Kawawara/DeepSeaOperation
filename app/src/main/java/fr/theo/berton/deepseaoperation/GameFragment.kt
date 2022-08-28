package fr.theo.berton.deepseaoperation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import fr.theo.berton.deepseaoperation.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var homeAnimation: AnimationDrawable

    private var _binding : FragmentGameBinding? = null
    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        binding = _binding!!

        return binding.root
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showGif()
        showAnimation()
    }
    override fun onStart() {
        super.onStart()
        homeAnimation.setExitFadeDuration(2000)
        homeAnimation.start()
    }

    private fun showGif() {
        val imageView: ImageView = binding.submarineInGame
        Glide.with(this).load(R.drawable.yellow_submarine_gif_2).into(imageView)
    }

    private fun showAnimation() {
        binding.gameLayout.apply {
            setBackgroundResource(R.drawable.background_home_list)
            homeAnimation = background as AnimationDrawable
        }
    }

}