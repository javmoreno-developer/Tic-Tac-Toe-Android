package com.jma.a3raya

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jma.a3raya.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // PLAYER1 DATA
    private var columnasJ1 = arrayListOf<Number>()
    private var filasJ1 = arrayListOf<Number>()
    private var diagonalesJ1 = arrayListOf<Number>()

    // PLAYER2 DATA
    private var columnasJ2 = arrayListOf<Number>()
    private var filasJ2 = arrayListOf<Number>()
    private var diagonalesJ2 = arrayListOf<Number>()

    // Variable que apunta al jugador activo
    private var active_player = 0

    // Imagen a colocar en el tablero
    private var player_image = R.drawable.close

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflado del layout
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Forzar modo claro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Boton reset
        binding.btnReset.setOnClickListener {
            reset()
        }

        fun setListeners() {
            // Lista de casillas
            val clickeableViews = listOf<View>(
                binding.img11,
                binding.img12,
                binding.img13,
                binding.img21,
                binding.img22,
                binding.img23,
                binding.img31,
                binding.img32,
                binding.img33
            )

            // Recorremos lista de casillas y asignamos un listener a cada una
            for (item in 0 until clickeableViews.size) {
                clickeableViews[item].setOnClickListener {
                    val buSelected: ImageView = clickeableViews[item] as ImageView

                    // Cambiamos imagen al pulsar
                    clickeableViews[item].setBackgroundResource(player_image)

                    // Asignamos filas y columnas
                    var fila = 0
                    var columna = 0
                    var diagonal = 0
                    when (buSelected.id) {
                        R.id.img_1_1 -> {
                            fila = 1
                            columna = 1
                            diagonal = 1
                        }
                        R.id.img_1_2 -> {
                            fila = 1
                            columna = 2
                            diagonal = 2
                        }
                        R.id.img_1_3 -> {
                            fila = 1
                            columna = 3
                            diagonal = 3
                        }
                        R.id.img_2_1 -> {
                            fila = 2
                            columna = 1
                            diagonal = 4
                        }
                        R.id.img_2_2 -> {
                            fila = 2
                            columna = 2
                            diagonal = 5
                        }
                        R.id.img_2_3 -> {
                            fila = 2
                            columna = 3
                            diagonal = 6
                        }
                        R.id.img_3_1 -> {
                            fila = 3
                            columna = 1
                            diagonal = 7
                        }
                        R.id.img_3_2 -> {
                            fila = 3
                            columna = 2
                            diagonal = 8
                        }
                        R.id.img_3_3 -> {
                            fila = 3
                            columna = 3
                            diagonal = 9
                        }
                    }

                    changePlayer(active_player, fila, columna, diagonal)

                }

            }

        }
        setListeners()
    }

    private fun changePlayer(activePlayer: Number, fila: Number, columna: Number, diagonal: Number) {
        // Dependiendo del jugador activo apuntara los resultados en uno u otro arrayList
        if (activePlayer == 0) {
            active_player = 1
            player_image = R.drawable.o
            filasJ1.add(fila)
            columnasJ1.add(columna)
            diagonalesJ1.add(diagonal)
            comprobacion(filasJ1, columnasJ1, diagonalesJ1, activePlayer)
            binding.textTurn.text = "Turno: J2"
        } else {
            active_player = 0
            player_image = R.drawable.close
            filasJ2.add(fila)
            columnasJ2.add(columna)
            diagonalesJ2.add(diagonal)
            comprobacion(filasJ2, columnasJ2, diagonalesJ2, activePlayer)
            binding.textTurn.text = "Turno: J1"
        }

    }

    private fun comprobacion(
        filas: ArrayList<Number>,
        columnas: ArrayList<Number>,
        diagonal: ArrayList<Number>,
        activePlayer: Number
    ) {
        // Comprobacion de filas
        var filaContador = 0
        var end = false

        for (i in 0 until filas.size) {
            filaContador = 0
            for (j in 0 until i) {
                if (filas[j] === filas[i]) {
                    filaContador++
                }
            }
        }


        // Comprobacion de columnas
        var columnaContador = 0

        for (i in 0 until columnas.size) {
            columnaContador = 0
            for (j in 0 until i) {
                if (columnas[j] === columnas[i]) {
                    columnaContador++
                }
            }
        }

        if (columnaContador == 2 || filaContador == 2) {
            end = true
        }

        // Comprobacion de diagonales
        if (!end) {
            if (diagonal.contains(1) && diagonal.contains(5) && diagonal.contains(9)) {
                end = true
            }
            if (diagonal.contains(3) && diagonal.contains(5) && diagonal.contains(7)) {
                end = true
            }
        }

        // Vemos si ha ganado el usuario
        if (end) {

            if (activePlayer == 0) endGame("J1") else endGame("J2")
        }




    }

    private fun endGame(player: String) {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle("has ganado $player")
            .setPositiveButton("Volver a jugar") { dialog, which ->
                reset()
            }
            .show()
    }

    private fun reset() {
        filasJ1 = arrayListOf()
        columnasJ1 = arrayListOf()
        diagonalesJ1 = arrayListOf()

        filasJ2 = arrayListOf()
        columnasJ2 = arrayListOf()
        diagonalesJ2 = arrayListOf()

        active_player = 0
        player_image = R.drawable.close

        val clickeableViews = listOf<View>(
            binding.img11,
            binding.img12,
            binding.img13,
            binding.img21,
            binding.img22,
            binding.img23,
            binding.img31,
            binding.img32,
            binding.img33
        )

        for (index in 0 until clickeableViews.size) {
            clickeableViews[index].setBackgroundResource(0)
        }
        binding.textTurn.text = "Turno: J1"
    }
}