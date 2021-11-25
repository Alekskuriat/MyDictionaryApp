package com.example.mydictionaryapp.view.detailsScreen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil.api.load
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.databinding.FragmentDetailsBinding
import com.example.mydictionaryapp.delegates.viewBinding
import com.example.mydictionaryapp.domain.database.HistoryEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.ticker


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewBinding: FragmentDetailsBinding by viewBinding(FragmentDetailsBinding::bind)
    private lateinit var data: DataModel
    private lateinit var dataHistory: HistoryEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            if (it.getParcelable<DataModel>(KEY) != null) {
                data = it.getParcelable(KEY)!!
                showDetails(data)
            }
            if (it.getParcelable<HistoryEntity>(HISTORY) != null) {
                dataHistory = it.getParcelable(HISTORY)!!
                showDetails(dataHistory)
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showDetails(data: DataModel) {
        assignmentValues(
            header = data.text,
            translationWord = data.meanings?.first()?.translation?.translation,
            imageUrl = data.meanings?.first()?.imageUrl
        )
    }

    private fun showDetails(data: HistoryEntity) {
        assignmentValues(
            header = data.word,
            translationWord = data.description,
            imageUrl = data.imageUrl
        )
    }

    private fun assignmentValues(header: String?, translationWord: String?, imageUrl: String?) {
        viewBinding.apply {
            word.text = header
            translation.text = translationWord
            descriptionImageview.load("https:$imageUrl") {
                crossfade(CROSSFADE)
                transformations(CircleCropTransformation())
                size(SIZE_IMAGE)
                target(
                    onStart = {
                        descriptionImageview.setImageResource(R.drawable.ic_launcher_foreground)
                    },
                    onSuccess = {
                        descriptionImageview.setImageDrawable(it)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            CoroutineScope(IO).launch {
                                for (i in BLUR_STEP downTo ZERO) {
                                    if (i == ZERO) {
                                        descriptionImageview.setRenderEffect(null)
                                        break
                                    }
                                    descriptionImageview.setRenderEffect(blurEffect(i.toFloat()))
                                    delay(BLUR_DURATION)
                                }
                            }
                        }
                    },
                    onError = {
                        descriptionImageview.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
                    }
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurEffect(a: Float): RenderEffect {
        return RenderEffect.createBlurEffect(a, a, Shader.TileMode.CLAMP)
    }

    companion object {
        const val KEY = "data"
        const val HISTORY = "history"
        private const val CROSSFADE = 1000
        private const val SIZE_IMAGE = 1000
        private const val BLUR_DURATION = 100L
        private const val BLUR_STEP = 15
        private const val ZERO = 0

        fun newInstance(data: DataModel): Fragment =
            DetailsFragment().apply {
                arguments = Bundle().apply { putParcelable(KEY, data) }
            }

        fun newInstance(data: HistoryEntity): Fragment =
            DetailsFragment().apply {
                arguments = Bundle().apply { putParcelable(HISTORY, data) }
            }
    }

}