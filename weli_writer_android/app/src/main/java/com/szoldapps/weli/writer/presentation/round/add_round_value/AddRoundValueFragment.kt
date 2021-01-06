package com.szoldapps.weli.writer.presentation.round.add_round_value

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.szoldapps.weli.writer.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddRoundValueFragment : Fragment(R.layout.fragment_add_round_value) {

    private val viewModel: AddRoundValueViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player1TricksLl = view.findViewById<LinearLayout>(R.id.player1TricksLl)
        val player2TricksLl = view.findViewById<LinearLayout>(R.id.player2TricksLl)
        val player3TricksLl = view.findViewById<LinearLayout>(R.id.player3TricksLl)
        val player4TricksLl = view.findViewById<LinearLayout>(R.id.player4TricksLl)
        val addRoundValueBt = view.findViewById<Button>(R.id.addRoundValueBt)

        setButtonClickListeners(0, player1TricksLl)
        setButtonClickListeners(1, player2TricksLl)
        setButtonClickListeners(2, player3TricksLl)
        setButtonClickListeners(3, player4TricksLl)
        addRoundValueBt.setOnClickListener {
            viewModel.addRoundValue()
        }
    }

    private fun setButtonClickListeners(playerNumber: Int, linearLayout: LinearLayout) {
        val valueTv = linearLayout.findViewById<TextView>(R.id.valueTv)
        val trick1Bt = linearLayout.findViewById<Button>(R.id.trick1Bt)
        val trick2Bt = linearLayout.findViewById<Button>(R.id.trick2Bt)
        val trick3Bt = linearLayout.findViewById<Button>(R.id.trick3Bt)
        val trick4Bt = linearLayout.findViewById<Button>(R.id.trick4Bt)
        val trick5Bt = linearLayout.findViewById<Button>(R.id.trick5Bt)
        val homeBt = linearLayout.findViewById<Button>(R.id.homeBt)

        trick1Bt.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
        trick2Bt.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
        trick3Bt.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
        trick4Bt.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
        trick5Bt.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
        homeBt.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
    }

    private fun buttonOnClickListener(playerNumber: Int, valueTv: TextView) = View.OnClickListener { view ->
        val tagAsInt = (view.tag as String).toInt()
        Timber.i("playerNumber = $playerNumber, buttonTag=$tagAsInt")
        valueTv.text = tagAsInt.toString()
        viewModel.updateValue(playerNumber, tagAsInt)
    }
}
