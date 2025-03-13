package com.ice.apianoview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.ice.apianoview.databinding.ActivityMainBinding
import com.ice.apianoview.entity.AutoPlayEntity
import com.ice.apianoview.entity.Piano
import com.ice.apianoview.extention.PianoBar
import com.ice.apianoview.listener.OnLoadAudioListener
import com.ice.apianoview.listener.OnPianoAutoPlayListener
import com.ice.apianoview.listener.OnPianoListener

class MainActivity : AppCompatActivity(), OnPianoListener, OnLoadAudioListener,
    OnPianoAutoPlayListener, PianoBar.ProgressListener {
    private lateinit var binding: ActivityMainBinding

    private var scrollProgress = 0

    private var isPlay = false
    private var litterStarList: ArrayList<AutoPlayEntity> = arrayListOf()

    private val LITTER_STAR_BREAK_SHORT_TIME: Long = 500
    private val LITTER_STAR_BREAK_LONG_TIME: Long = 1000

    private fun initLitterStarList() {
        litterStarList = ArrayList()
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 0, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 0, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 5, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 5, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_LONG_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 1, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 1, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 0, LITTER_STAR_BREAK_LONG_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 1, LITTER_STAR_BREAK_LONG_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 1, LITTER_STAR_BREAK_LONG_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 0, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 0, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 5, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 5, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 4, LITTER_STAR_BREAK_LONG_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 3, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 2, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 1, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 1, LITTER_STAR_BREAK_SHORT_TIME)
        )
        litterStarList.add(
            AutoPlayEntity(Piano.PianoKeyType.WHITE, 4, 0, LITTER_STAR_BREAK_LONG_TIME)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLitterStarList()
        initData()
        handleEvent()
    }

    private fun initData() {
        binding.pianoView.setPianoListener(this)
        binding.pianoView.setAutoPlayListener(this)
        binding.pianoView.setLoadAudioListener(this)
    }

    private fun handleEvent() {
        binding.ivMusic.setOnClickListener {
            if (!isPlay) {
                binding.pianoView.autoPlay(litterStarList)
            } else {
                binding.pianoView.stopAutoPlay()
            }
        }

        binding.ivZoomOut.setOnClickListener {
            binding.pianoView.zoomOut()
        }
        binding.ivZoomIn.setOnClickListener {
            binding.pianoView.zoomIn()
        }
    }

    override fun onPianoInitFinish() {
        Log.d("56123132312", "onPianoInitFinish: ")
        binding.pianoBar.apply {
            attackPianoWidth = binding.pianoView.pianoWidth
            attackWidth = binding.pianoView.layoutWidth
            addListener(this@MainActivity)
        }
    }

    override fun onPianoClick(type: Piano.PianoKeyType?, voice: Piano.PianoVoice?, group: Int, positionOfGroup: Int) {

    }

    override fun loadPianoAudioStart() {

    }

    override fun loadPianoAudioFinish() {

    }

    override fun loadPianoAudioError(e: Exception?) {

    }

    override fun loadPianoAudioProgress(progress: Int) {

    }

    override fun onPianoAutoPlayStart() {
        isPlay = true
    }

    override fun onScroll(progress: Int) {
        binding.pianoBar.progress = progress
    }

    override fun onPianoAutoPlayEnd() {
        isPlay = false
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.pianoView.releaseAutoPlay()
    }

    override fun onUserChangedProgress(progress: Int) {
        binding.pianoView.scroll(progress)
    }
}