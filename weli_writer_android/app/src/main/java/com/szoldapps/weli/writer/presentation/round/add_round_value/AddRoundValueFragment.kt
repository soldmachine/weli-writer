package com.szoldapps.weli.writer.presentation.round.add_round_value

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Content
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Error
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Loading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_round_value.heartsToggleButton
import kotlinx.android.synthetic.main.fragment_add_round_value.multiplierTv
import kotlinx.android.synthetic.main.fragment_add_round_value.redealButton
import timber.log.Timber

@AndroidEntryPoint
class AddRoundValueFragmentOld : Fragment(R.layout.fragment_add_round_value) {

    private val viewModel: AddRoundValueViewModel by viewModels()

    private val args: AddRoundValueFragmentOldArgs by navArgs()

    private val player1TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player1TricksLl) }
    private val player2TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player2TricksLl) }
    private val player3TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player3TricksLl) }
    private val player4TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player4TricksLl) }

    private val value1Tv by lazy { player1TricksLl.findViewById<TextView>(R.id.valueTv) }
    private val value2Tv by lazy { player2TricksLl.findViewById<TextView>(R.id.valueTv) }
    private val value3Tv by lazy { player3TricksLl.findViewById<TextView>(R.id.valueTv) }
    private val value4Tv by lazy { player4TricksLl.findViewById<TextView>(R.id.valueTv) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            when (event) {
                AddRoundValueViewEvent.CloseFragment ->
                    // this is a workaround as with popBackStack the roundFragment didn't refresh the data
                    findNavController().navigate(
                        AddRoundValueFragmentOldDirections.actionAddRoundValueBottomSheetToRoundFragment(args.roundId)
                    )
            }
        }

        val addRoundValueBt = view.findViewById<Button>(R.id.addRoundValueBt)

        setButtonClickListeners(0, player1TricksLl)
        setButtonClickListeners(1, player2TricksLl)
        setButtonClickListeners(2, player3TricksLl)
        setButtonClickListeners(3, player4TricksLl)
        addRoundValueBt.setOnClickListener {
            viewModel.addRoundValue()
        }
        heartsToggleButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHeartsRound(isChecked)
        }
        redealButton.setOnClickListener { _ ->
            viewModel.updateRedealState()
        }
    }

    private fun handleViewState(viewState: AddRoundValueViewState) {
        when (viewState) {
            Loading -> TODO()
            Error -> TODO()
            is Content -> handleContentState(viewState)
        }
    }

    private fun handleContentState(content: Content) = with(content) {
        value1Tv.text = (values[0] * multiplier).toString()
        value2Tv.text = (values[1] * multiplier).toString()
        value3Tv.text = (values[2] * multiplier).toString()
        value4Tv.text = (values[3] * multiplier).toString()
        multiplierTv.text = "${multiplier}x (heartMultiplier=${heartsMultiplier}, redealMultiplier=${redealMultiplier})"
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
        viewModel.updateValue(playerNumber, tagAsInt)
    }
}
