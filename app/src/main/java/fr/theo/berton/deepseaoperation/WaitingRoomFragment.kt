package fr.theo.berton.deepseaoperation

import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

import fr.theo.berton.deepseaoperation.databinding.FragmentWaitingRoomBinding
import fr.theo.berton.deepseaoperation.model.Player
import fr.theo.berton.deepseaoperation.network.SocketViewModel
import fr.theo.berton.deepseaoperation.viewmodel.GameViewModel
import fr.theo.berton.deepseaoperation.model.RoleType
import fr.theo.berton.deepseaoperation.network.payload.GamePlayerPayload
import fr.theo.berton.deepseaoperation.network.payload.PayloadType
import fr.theo.berton.deepseaoperation.network.payload.PlayerUpdateStatusPayload


class WaitingRoomFragment : Fragment() {

    private lateinit var homeAnimation: AnimationDrawable

    private var _binding: FragmentWaitingRoomBinding? = null
    private lateinit var binding: FragmentWaitingRoomBinding

    private val args: WaitingRoomFragmentArgs by navArgs()

    private val socketViewModel: SocketViewModel by activityViewModels()
    private val gameViewModel: GameViewModel by activityViewModels()
    private val playerViewModel: PlayerViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWaitingRoomBinding.inflate(inflater, container, false)
        binding = _binding!!

        //        init game
        socketViewModel.ipAddress.observe(viewLifecycleOwner) { ipAdress ->
            if (ipAdress !== null) {
                if (args.origin == WaitingRoomOriginTypes.mainMenu.toString()) {
                    initGame(ipAdress)
                }
            }
        }


        //        update Player in Recycler view
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.SPACE_AROUND

        val playersRecyclerView = binding.playersWaitingList
        playersRecyclerView.layoutManager = layoutManager

        playerViewModel.username.observe(viewLifecycleOwner) {
            val name = playerViewModel.username.value
            System.out.println("player name init : " + name.toString())
        }

        gameViewModel.currentGame.observe(viewLifecycleOwner) { currentGame ->
            if (currentGame != null) {
                System.out.println("player name s : " + currentGame.players.toString())

                if (currentGame.players.find { player -> !player.status } == null && currentGame.players.size > 1) {
                    gameViewModel.gameIsLauncheable.postValue(true)
                } else {
                    gameViewModel.gameIsLauncheable.postValue(false)
                }

                playersRecyclerView.adapter =
                    WaitingRoomAdapter(requireContext(), currentGame.players)

                updateButtons()

                if (currentGame.game.status == "started") {
                    launchGame()
                }
            }
        }

//        update Player in Game
        gameViewModel.addPlayer.observe(viewLifecycleOwner) {
            if (it == true) {
                updatePlayerInGame()
                gameViewModel.addPlayer.postValue(false)
            }
        }

        listenPayload()

        return binding.root
//        return inflater.inflate(R.layout.fragment_waiting_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupIPAdress()

        showAnimation()



        val backButton = binding.quitButtonWaitingRoom
        val readyButton = binding.readyButton

        readyButton.setOnClickListener {
            val currentGame = gameViewModel.currentGame.value

            val ipAdress = socketViewModel.ipAddress.value

            val currentPlayer = currentGame?.players?.find { player ->
                player.name == playerViewModel.username.value &&
                        player.ipAddress == ipAdress
            }

            System.out.println("waiting room player name : " + currentPlayer?.name)
            System.out.println("waiting room player : " + currentPlayer)

            if (currentPlayer != null) {
                currentPlayer.status = !currentPlayer.status

                gameViewModel.updatePlayerStatus(currentPlayer)

//                send new status to all player

                val payload = PlayerUpdateStatusPayload(
                    data = currentPlayer
                ).jsonEncodeToString()

                for (player in currentGame.players) {
                    socketViewModel.sendUDPData(
                        payload,
                        player.ipAddress,
                        8888
                    )
                }
            }


//            Navigation.findNavController(view).navigate(R.id.action_waitingRoomFragment_to_gameFragment)
        }

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
//        val view = requireView()
        binding.waitingRoomLayout.apply {
            setBackgroundResource(R.drawable.background_home_list)
            homeAnimation = background as AnimationDrawable
        }
    }

    private fun updateButtons() {
        val currentGame = gameViewModel.currentGame.value
        val currentPlayer = currentGame?.players?.find { player ->
            player.name == playerViewModel.username.value &&
                    player.ipAddress == socketViewModel.ipAddress.value
        }
        if (currentPlayer != null) {
            if (currentPlayer.status) {
                binding.readyButton.setText(R.string.ready_button)
            } else {
                binding.readyButton.setText(R.string.not_ready_button)
            }
        }

        if (currentGame != null) {
            if ((currentGame.game.role) == RoleType.client.toString()) {
                // TODO add lunch button
//            binding.launchGameButton.visibility = View.INVISIBLE
            }
        }
    }

        private fun setupIPAdress() {
        binding.adressIp.text = "loading..."
        var adressIp = ""
        socketViewModel.ipAddress.observe(viewLifecycleOwner) { ipAdress ->
            adressIp = ipAdress + ":8888"
            binding.adressIp.text = adressIp
        }
    }

    private fun updatePlayerInGame() {
        val payload = gameViewModel.currentGame.value?.players?.let {
            GamePlayerPayload(
                PayloadType.players.toString(),
                it
            ).jsonEncodeToString()
        }

        for (player in gameViewModel.currentGame.value?.players!!) {
            if (socketViewModel.ipAddress.value != player.ipAddress) {
                if (payload != null) {
                    socketViewModel.sendUDPData(payload, player.ipAddress, player.port)
                }
            }
        }
    }

    private fun launchGame() {
        val action = WaitingRoomFragmentDirections.actionWaitingRoomFragmentToGameFragment()
        findNavController().navigate(action)
    }


    private fun listenPayload() {
        socketViewModel.payload.observe(viewLifecycleOwner) { payload ->
            if (payload != null) {
                gameViewModel.handlePayload(payload)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initGame(ipAdress: String) {
        gameViewModel.initGame(
            RoleType.server.toString(),
            ipAdress,
            socketViewModel.ipAddress.value!!,
            playerViewModel.username.value.toString()
        )
    }

}