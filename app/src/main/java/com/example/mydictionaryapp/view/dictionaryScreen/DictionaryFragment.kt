package com.example.mydictionaryapp.view.dictionaryScreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.databinding.FragmentDictionaryScreenBinding
import com.example.mydictionaryapp.domain.abs.AbsFragment
import com.example.mydictionaryapp.view.dictionaryScreen.RV.DictionaryAdapter
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import javax.inject.Inject

class DictionaryFragment
    : AbsFragment(R.layout.fragment_dictionary_screen){

    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var model: DictionaryViewModel
    private var adapter: DictionaryAdapter? = null
    private val viewBinding: FragmentDictionaryScreenBinding by viewBinding()
    private val observerData = Observer<List<DataModel>> { showWords(it) }
    private val observerErrors = Observer<Throwable> { showError(it) }
    private val observerLoading = Observer<Boolean> { showLoading(it) }


    private val onListItemClickListener: DictionaryAdapter.OnListItemClickListener =
        object : DictionaryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {

                val translate = "${getString(R.string.translate_from_toast)}: ${data.text}"
                val note =
                    "${getString(R.string.note_from_toast)}: ${data.meanings?.first()?.translation?.note}"
                val difficulty =
                    "${getString(R.string.difficulty_from_toast)}: ${data.meanings?.first()?.difficultyLevel.toString()}"

                Toast.makeText(
                    context, translate + "\n" + note + "\n" + difficulty, Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = viewModelFactory.create(DictionaryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getLoading().observe(viewLifecycleOwner, observerLoading)
        setOnClickListenerFromButtons()

    }

    private fun setOnClickListenerFromButtons() {
        viewBinding.apply {
            searchButtonTextview.setOnClickListener {

                model.getError().observe(viewLifecycleOwner, observerErrors)

                val text = viewBinding.searchEditText.text.toString()

                if (text.isNotEmpty()) {
                    model.getData(word = text, isOnline = true)
                        .observe(viewLifecycleOwner, observerData)
                } else {
                    Toast.makeText(context, getString(R.string.enter_a_word), Toast.LENGTH_SHORT).show()
                }
            }
            clearTextImageview.setOnClickListener {
                searchEditText.setText("")
            }
        }
    }


    private fun showWords(data: List<DataModel>?) {
        if (data == null || data.isEmpty()) {
            showErrorScreen(getString(R.string.empty_server_response_on_success))
        } else {
            showViewSuccess()
            if (adapter == null) {
                viewBinding.apply {
                    mainActivityRecyclerview.layoutManager =
                        LinearLayoutManager(context)
                    mainActivityRecyclerview.adapter =
                        DictionaryAdapter(onListItemClickListener, data)
                }
            } else {
                adapter!!.setData(data)
            }
        }
    }

    private fun showError(error: Throwable) {
        showErrorScreen(error.toString())
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) showViewLoading()
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        viewBinding.apply {
            errorTextview.text = error ?: getString(R.string.undefined_error)
            reloadButton.setOnClickListener {
                model.getData("hi", true)
                    .observe(viewLifecycleOwner, observerData)
            }
        }
    }

    private fun showViewSuccess() {
        viewBinding.apply {
            mainActivityRecyclerview.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            errorLinearLayout.visibility = View.GONE
        }
    }

    private fun showViewLoading() {
        viewBinding.apply {
            mainActivityRecyclerview.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            errorLinearLayout.visibility = View.GONE
        }
    }

    private fun showViewError() {
        viewBinding.apply {
            mainActivityRecyclerview.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorLinearLayout.visibility = View.VISIBLE
        }

    }

    companion object {
        fun newInstance() = DictionaryFragment()
    }

}

