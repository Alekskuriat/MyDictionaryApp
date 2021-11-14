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


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewBinding: FragmentDetailsBinding by viewBinding()
    private lateinit var data: DataModel


    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            data = it.getParcelable(KEY)!!
        }
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetails(data)
    }

    private fun showDetails(data: DataModel) {
        viewBinding.apply {
            word.text = data.text
            translation.text = data.meanings?.first()?.translation?.translation

            descriptionImageview.load("https:${data.meanings?.first()?.imageUrl}") {
                crossfade(CROSSFADE)
                placeholder(R.drawable.ic_launcher_foreground)
                transformations(CircleCropTransformation())
                size(SIZE_IMAGE)
            }
        }
    }

    companion object {
        const val KEY = "data"
        private const val CROSSFADE = 1000
        private const val SIZE_IMAGE = 1000
        fun newInstance(data: DataModel): Fragment =
            DetailsFragment().apply {
                arguments = Bundle().apply { putParcelable(KEY, data) }
            }
    }

}