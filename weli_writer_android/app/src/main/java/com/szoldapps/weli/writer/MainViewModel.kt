package com.szoldapps.weli.writer

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.szoldapps.weli.writer.calculation.CalculationRepository

class MainViewModel @ViewModelInject constructor(
    private val calculationRepository: CalculationRepository
) : ViewModel() {

    fun someFunc() {
        Log.v("TAG", "working!")
    }
}
