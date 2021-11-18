package com.example.mydictionaryapp.view.detailsScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.databinding.FragmentDetailsBinding
import com.example.mydictionaryapp.domain.database.HistoryEntity


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewBinding: FragmentDetailsBinding by viewBinding()
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
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                size(SIZE_IMAGE)
            }
        }
    }

    companion object {
        const val KEY = "data"
        const val HISTORY = "history"
        private const val CROSSFADE = 1000
        private const val SIZE_IMAGE = 1000
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