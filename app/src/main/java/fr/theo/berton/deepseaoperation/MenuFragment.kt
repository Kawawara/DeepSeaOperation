package fr.theo.berton.deepseaoperation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.graphics.drawable.AnimationDrawable
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import fr.theo.berton.deepseaoperation.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private lateinit var homeAnimation: AnimationDrawable

    private var _binding: FragmentMenuBinding? = null
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding = _binding!!

        return binding.root

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showGif()
        showAnimation()

        val userName = binding.playerName
        println("preference username fragment :" + getPreferenceName());
        userName.setText(getPreferenceName())

        val btnCreate = binding.createButton
        val btnJoin = binding.joinButton
        val btnHistory = binding.historyButton
        val btnQuit = binding.quitButton
        val btnValidateName = binding.validateName

        btnValidateName.setOnClickListener {
            savePreferencesName(view)
        }

        btnCreate.setOnClickListener {
            val creator = true
            val name = ""
            val action = MenuFragmentDirections
                .actionMenuFragmentToWaitingRoomFragment()
                .setOrigin(WaitingRoomOriginTypes.mainMenu.toString())
            findNavController().navigate(action)
        }

        btnHistory.setOnClickListener {
            val action = MenuFragmentDirections.actionMenuFragmentToHistoryFragment()
            findNavController().navigate(action)
        }

        btnJoin.setOnClickListener {
            // TODO Make popup to fill up form to join party
        }

        btnQuit.setOnClickListener {
            requireActivity().finish()
            System.exit(0)
        }
    }

    override fun onStart() {
        super.onStart()
        homeAnimation.setExitFadeDuration(2000)
        homeAnimation.start()
    }

    private fun showAnimation() {
        binding.menuLayout.apply {
            setBackgroundResource(R.drawable.background_home_list)
            homeAnimation = background as AnimationDrawable
        }
    }

    private fun showGif() {
        val imageView: ImageView = binding.submarine
        Glide.with(this).load(R.drawable.yellow_submarine_gif_2).into(imageView)
    }


    private fun savePreferencesName(view: View) {
        val sharedPrefs = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)

        with (sharedPrefs.edit()) {
            putString("USERNAME", binding.playerName.text.toString())
            apply()
        }
    }
    private fun getPreferenceName(): String {
        val sharedPrefs = requireActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE)
        return sharedPrefs.getString("USERNAME", "default").toString()
    }
}