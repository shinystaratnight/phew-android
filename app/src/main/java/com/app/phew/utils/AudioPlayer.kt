package com.app.phew.utils

import android.content.Context
import android.media.MediaPlayer



class AudioPlayer {

    private var mMediaPlayer: MediaPlayer? = null

    fun stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun play(c: Context, rid: Int) {
        stop()

        mMediaPlayer = MediaPlayer.create(c, rid)
        mMediaPlayer!!.setOnCompletionListener { stop() }

        mMediaPlayer!!.start()
    }

}