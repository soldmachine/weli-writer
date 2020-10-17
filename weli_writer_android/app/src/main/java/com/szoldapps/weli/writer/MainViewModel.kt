package com.szoldapps.weli.writer

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.szoldapps.weli.writer.calculation.*
import com.szoldapps.weli.writer.data.dao.UserDao
import com.szoldapps.weli.writer.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel @ViewModelInject constructor(
    private val calculationRepository: CalculationRepository,
    private val userDao: UserDao,
) : ViewModel() {

    val result: MutableLiveData<String> = MutableLiveData("")

    fun doSomethingInDb() {
        viewModelScope.launch {
            addUser()
        }

    }

    private suspend fun addUser() = withContext(Dispatchers.Default) {
        userDao.insertAll(
            User(firstName = "firstName", lastName = "lastName")
        )
    }

    fun calculateResult(input: String) {
        try {
            val output = StringBuilder()
            val rows = input.split("\n")
            val players = rows[0].split(",").map { Player(it) }
            val rounds = mutableListOf<Round>()
            output.appendLine(players.toString())
            rows.forEachIndexed { index, row ->
                if (index != 0) {
                    val values = row.split(",")
                    output.appendLine(values.toString())
                    rounds.add(generateRound(index, values.map { it.toInt() }, players))
                }
            }
            val game = Game(rounds = rounds)
            val calculationResult = calculationRepository.calculateResult(game)
            result.postValue(calculationResult.payments.joinToString("\n"))
        } catch (e: Exception) {
            result.postValue("ERROR!")
        }
    }

    private fun generateRound(number: Int, points: List<Int>, players: List<Player>): Round {
        return Round(
            number = number,
            values = listOf(
                RoundValue(
                    player = players[0],
                    points = points[0]
                ),
                RoundValue(
                    player = players[1],
                    points = points[1]
                ),
                RoundValue(
                    player = players[2],
                    points = points[2]
                ),
                RoundValue(
                    player = players[3],
                    points = points[3]
                )
            )
        )
    }
}
