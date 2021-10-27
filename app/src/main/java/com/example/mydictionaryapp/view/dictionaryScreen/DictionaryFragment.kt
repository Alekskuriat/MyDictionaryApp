package com.example.mydictionaryapp.view.dictionaryScreen

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.databinding.FragmentDictionaryScreenBinding
import com.example.mydictionaryapp.domain.abs.AbsFragment
import com.example.mydictionaryapp.domain.dictionary.repo.DictionaryRepository
import com.example.mydictionaryapp.presenter.DictionaryPresenter.DictionaryPresenter
import com.example.mydictionaryapp.presenter.DictionaryPresenter.DictionaryPresenterImpl
import com.example.mydictionaryapp.view.dictionaryScreen.RV.DictionaryAdapter
import javax.inject.Inject

class DictionaryFragment
    : AbsFragment(R.layout.fragment_dictionary_screen),
    DictionaryView {

    companion object {
        fun newInstance() = DictionaryFragment()
    }

    private val viewBinding: FragmentDictionaryScreenBinding by viewBinding()

    @Inject
    lateinit var repo: DictionaryRepository

    private lateinit var presenter: DictionaryPresenter<DictionaryView>

    private var adapter: DictionaryAdapter? = null

    private val onListItemClickListener: DictionaryAdapter.OnListItemClickListener =
        object : DictionaryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {

                val translate = "${getString(R.string.translate_from_toast)}: ${data.text}"
                val note =
                    "${getString(R.string.note_from_toast)}: ${data.meanings?.first()?.translation?.note}"
                val difficulty =
                    "${getString(R.string.difficulty_from_toast)}: ${data.meanings?.first()?.difficultyLevel.toString()}"

                Toast.makeText(
                    context,
                    translate +
                            "\n" +
                            note +
                            "\n" +
                            difficulty,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListenerFromButtons()
    }

    private fun setOnClickListenerFromButtons() {
        viewBinding.apply {
            searchButtonTextview.setOnClickListener {
                val text =
                    viewBinding
                        .searchEditText.text.toString()

                if (text.isNotEmpty()) {
                    presenter
                        .getData(
                            word = text,
                            isOnline = true
                        )
                } else {
                    Toast
                        .makeText(
                            context,
                            getString(R.string.enter_a_word),
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
            clearTextImageview.setOnClickListener {
                searchEditText.setText("")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = createPresenter()
        presenter.attachView(this)
    }

    private fun createPresenter(): DictionaryPresenter<DictionaryView> =
        DictionaryPresenterImpl(
            repo,
            router,
            schedulers,
            disposable
        )

    override fun onStop() {
        presenter.detachView(this)
        super.onStop()
    }


    override fun showWords(data: List<DataModel>?) {

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

    override fun showError(error: Throwable) {
        showErrorScreen(error.toString())
    }

    override fun showLoading() {
        showViewLoading()
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        viewBinding.apply {
            errorTextview.text = error ?: getString(R.string.undefined_error)
            reloadButton.setOnClickListener {
                presenter
                    .getData("hi", true)
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

}

