package com.szoldapps.weli.writer.presentation.round.add_round_value

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.szoldapps.weli.writer.R
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Content
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Error
import com.szoldapps.weli.writer.presentation.round.add_round_value.AddRoundValueViewState.Loading
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AddRoundValueFragmentOld : Fragment(R.layout.fragment_add_round_value) {

    private val viewModel: AddRoundValueViewModel by viewModels()

    private val args: AddRoundValueFragmentOldArgs by navArgs()

    private val player1TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player1TricksLl) }
    private val player2TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player2TricksLl) }
    private val player3TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player3TricksLl) }
    private val player4TricksLl by lazy { requireView().findViewById<LinearLayout>(R.id.player4TricksLl) }

    private val player1Tv by lazy { player1TricksLl.findViewById<TextView>(R.id.playerTv) }
    private val player2Tv by lazy { player2TricksLl.findViewById<TextView>(R.id.playerTv) }
    private val player3Tv by lazy { player3TricksLl.findViewById<TextView>(R.id.playerTv) }
    private val player4Tv by lazy { player4TricksLl.findViewById<TextView>(R.id.playerTv) }

    private val normalModeButtonsLl1 by lazy { player1TricksLl.findViewById<LinearLayout>(R.id.normalModeButtonsLl) }
    private val normalModeButtonsLl2 by lazy { player2TricksLl.findViewById<LinearLayout>(R.id.normalModeButtonsLl) }
    private val normalModeButtonsLl3 by lazy { player3TricksLl.findViewById<LinearLayout>(R.id.normalModeButtonsLl) }
    private val normalModeButtonsLl4 by lazy { player4TricksLl.findViewById<LinearLayout>(R.id.normalModeButtonsLl) }

    private val mulaModeButtonsLl1 by lazy { player1TricksLl.findViewById<LinearLayout>(R.id.mulaModeButtonsLl) }
    private val mulaModeButtonsLl2 by lazy { player2TricksLl.findViewById<LinearLayout>(R.id.mulaModeButtonsLl) }
    private val mulaModeButtonsLl3 by lazy { player3TricksLl.findViewById<LinearLayout>(R.id.mulaModeButtonsLl) }
    private val mulaModeButtonsLl4 by lazy { player4TricksLl.findViewById<LinearLayout>(R.id.mulaModeButtonsLl) }

    private val value1Tv by lazy { player1TricksLl.findViewById<TextView>(R.id.valueTv) }
    private val value2Tv by lazy { player2TricksLl.findViewById<TextView>(R.id.valueTv) }
    private val value3Tv by lazy { player3TricksLl.findViewById<TextView>(R.id.valueTv) }
    private val value4Tv by lazy { player4TricksLl.findViewById<TextView>(R.id.valueTv) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, ::handleViewState)
        viewModel.viewEvent.observe(viewLifecycleOwner, ::handleEventState)
        viewModel.loadPlayerInitials(args.roundId, args.roundNumber)

        setupUi(view)
    }

    private fun handleViewState(viewState: AddRoundValueViewState) {
        when (viewState) {
            Loading -> TODO()
            Error -> TODO()
            is Content -> handleContentState(viewState)
        }
    }

    private fun handleEventState(event: AddRoundValueViewEvent) =
        when (event) {
            AddRoundValueViewEvent.CloseFragment ->
                // this is a workaround as with popBackStack the roundFragment didn't refresh the data
                findNavController().navigate(
                    AddRoundValueFragmentOldDirections.actionAddRoundValueBottomSheetToRoundFragment(args.roundId)
                )
        }

    private fun setupUi(view: View) {
        val addRoundValueBt = view.findViewById<Button>(R.id.addRoundValueBt)
        val heartsToggleButton = view.findViewById<ToggleButton>(R.id.heartsToggleButton)
        val redealButton = view.findViewById<Button>(R.id.redealButton)

        setButtonClickListeners(0, player1TricksLl)
        setButtonClickListeners(1, player2TricksLl)
        setButtonClickListeners(2, player3TricksLl)
        setButtonClickListeners(3, player4TricksLl)
        addRoundValueBt.setOnClickListener {
            viewModel.addRoundValue(args.roundId, args.roundNumber)
        }
        heartsToggleButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateHeartsRound(isChecked)
        }
        redealButton.setOnClickListener { _ ->
            viewModel.updateRedealState()
        }

        val mulaBt = view.findViewById<ToggleButton>(R.id.mulaBt)
        mulaBt.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateMulaRound(isChecked)
        }
    }

    private fun handleContentState(content: Content) = with(content) {
        val multiplierTv = view?.findViewById<TextView>(R.id.multiplierTv)
        multiplierTv?.text =
            "${multiplier}x (heartMultiplier=${heartsMultiplier}, redealMultiplier=${redealMultiplier})"

        player1Tv.text = playerInitials[0]
        player2Tv.text = playerInitials[1]
        player3Tv.text = playerInitials[2]
        player4Tv.text = playerInitials[3]

        normalModeButtonsLl1.isVisible = !isMulaRound
        normalModeButtonsLl2.isVisible = !isMulaRound
        normalModeButtonsLl3.isVisible = !isMulaRound
        normalModeButtonsLl4.isVisible = !isMulaRound
        mulaModeButtonsLl1.isVisible = isMulaRound
        mulaModeButtonsLl2.isVisible = isMulaRound
        mulaModeButtonsLl3.isVisible = isMulaRound
        mulaModeButtonsLl4.isVisible = isMulaRound

        value1Tv.text = (tricks[0] * multiplier).toString()
        value2Tv.text = (tricks[1] * multiplier).toString()
        value3Tv.text = (tricks[2] * multiplier).toString()
        value4Tv.text = (tricks[3] * multiplier).toString()

        val addRoundValueBt = view?.findViewById<Button>(R.id.addRoundValueBt)
        addRoundValueBt?.isEnabled = isAddValuesButtonEnabled
    }

    private fun setButtonClickListeners(playerNumber: Int, linearLayout: LinearLayout) {
        val valueTv = linearLayout.findViewById<TextView>(R.id.valueTv)
        val trick1Bt = linearLayout.findViewById<Button>(R.id.trick1Bt)
        val trick2Bt = linearLayout.findViewById<Button>(R.id.trick2Bt)
        val trick3Bt = linearLayout.findViewById<Button>(R.id.trick3Bt)
        val trick4Bt = linearLayout.findViewById<Button>(R.id.trick4Bt)
        val trick5Bt = linearLayout.findViewById<Button>(R.id.trick5Bt)
        val homeBt = linearLayout.findViewById<Button>(R.id.homeBt)
        val fallenBt = linearLayout.findViewById<Button>(R.id.fallenBt)
        val selfFallenBt = linearLayout.findViewById<Button>(R.id.selfFallenBt)
        val mulaSucceededBt = linearLayout.findViewById<Button>(R.id.mulaSucceededBt)
        val mulaFallenBt = linearLayout.findViewById<Button>(R.id.mulaFallenBt)
        val mulaHeldBt = linearLayout.findViewById<Button>(R.id.mulaHeldBt)
        val mulaStayBt = linearLayout.findViewById<Button>(R.id.mulaStayBt)

        val buttonList =
            listOf(
                trick1Bt, trick2Bt, trick3Bt, trick4Bt, trick5Bt,
                homeBt, fallenBt, selfFallenBt,
                mulaSucceededBt, mulaFallenBt, mulaHeldBt, mulaStayBt
            )
        buttonList.forEach { button ->
            button.setOnClickListener(buttonOnClickListener(playerNumber, valueTv))
        }
    }

    private fun buttonOnClickListener(playerNumber: Int, valueTv: TextView) = View.OnClickListener { view ->
        val tagAsInt = (view.tag as String).toInt()
        Timber.i("playerNumber = $playerNumber, buttonTag=$tagAsInt")
        viewModel.updateTrick(playerNumber, tagAsInt)
    }
}
