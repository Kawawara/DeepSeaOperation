package fr.theo.berton.deepseaoperation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.activity.viewModels

import kotlin.random.Random

import fr.theo.berton.deepseaoperation.databinding.ActivityMainBinding
import fr.theo.berton.deepseaoperation.network.SocketViewModel
import fr.theo.berton.deepseaoperation.viewmodel.GameViewModel


class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "preferences"
    private val PREF_UNAME = "USERNAME"

    private val DefaultUnameValue = "beattle_" + Random.nextInt(0, 999)
    private var UnameValue: String? = "personne"

    private lateinit var binding: ActivityMainBinding

    private val socketViewModel: SocketViewModel by viewModels()
    private val gameViewModel : GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.saveNameForFirstTime()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        socketViewModel.ipAddress.observe(this) {}

//        setContentView(R.layout.activity_main)

        val isHost: Boolean = intent.getBooleanExtra("isHost", false)
        val hostIpAddress = intent.getStringExtra("ip").toString()
        val hostPort = intent.getStringExtra("port").toString()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_frame)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onPause() {
        super.onPause()
        savePreferences()
    }
    override fun onResume() {
        super.onResume()
        loadPreferences()
    }

    private fun savePreferences() {
        val settings = getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val editor = settings.edit()

        // Edit and commit
        System.out.println("preference username saved :" + UnameValue);
        editor.putString(PREF_UNAME, UnameValue)
        editor.apply()
        System.out.println("preference username saved and retrieved :" + getSharedPreferences(PREFS_NAME, MODE_PRIVATE).getString(
            "USERNAME", "default"));
    }

    private fun loadPreferences() {
        val settings = getSharedPreferences(
            PREFS_NAME,
            MODE_PRIVATE
        )

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue)
        System.out.println("preference username loaded :" + UnameValue);
    }

    private fun saveNameForFirstTime() {
        this.loadPreferences()
        this.savePreferences()
    }
}