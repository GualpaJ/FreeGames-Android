package com.javier.freegames.activities

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.javier.freegames.R
import com.javier.freegames.adapters.GameAdapter
import com.javier.freegames.data.Game
import com.javier.freegames.data.GameService
import com.javier.freegames.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: GameAdapter
    var gameList: List<Game> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = GameAdapter(gameList) {position ->
            val game = gameList[position]
            val intent= Intent(this, DetailActivity::class.java)
            intent.putExtra("GAME_ID",game.id)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        CoroutineScope(Dispatchers.IO).launch {
            gameList = GameService.getInstance().getGamesList()

            CoroutineScope(Dispatchers.Main).launch {
                adapter.updateData(gameList)
            }
        }
    }
}