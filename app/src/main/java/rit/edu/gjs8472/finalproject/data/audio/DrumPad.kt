package rit.edu.gjs8472.finalproject.data.audio

import rit.edu.gjs8472.finalproject.R

data class DrumPad(val label: String, val soundResourceId: Int)

val drumPads = listOf(
    DrumPad("Snare1", R.raw.snare_drum_2b),
    DrumPad("Snare2", R.raw.snare_drum_3a),
    DrumPad("Tom1", R.raw.tom_tom_drum_1),
    DrumPad("Tom2", R.raw.medium_tom_drum_7a),
    DrumPad("Hi-Hat", R.raw.hi_hat_b3),
    DrumPad("Crash", R.raw.crash_cymbal_b),
    DrumPad("Bass", R.raw.bass_drum_5a),
    DrumPad("Floor", R.raw.floor_tum_drum_5a)
)
