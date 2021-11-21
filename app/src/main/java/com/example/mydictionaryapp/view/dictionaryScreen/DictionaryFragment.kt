package com.example.mydictionaryapp.view.dictionaryScreen

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionaryapp.model.entities.DataModel
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.databinding.FragmentDictionaryScreenBinding
import com.example.mydictionaryapp.view.detailsScreen.DetailsScreen
import com.example.mydictionaryapp.view.dictionaryScreen.RV.DictionaryAdapter
import com.example.mydictionaryapp.viewModel.DictionaryViewModel.DictionaryViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class DictionaryFragment
    : Fragment(R.layout.fragment_dictionary_screen) {


    private val scopeFragment = getKoin().createScope("Dictionary_fragment_id", named("Dictionary_fragment") )
    private val router : Router by inject()
    private val viewModel: DictionaryViewModel by scopeFragment.inject()
    lateinit var model: DictionaryViewModel
    private var adapter: DictionaryAdapter? = null
    private val viewBinding: FragmentDictionaryScreenBinding by viewBinding()
    private val observerData = Observer<List<DataModel>> { showWords(it) }
    private val observerErrors = Observer<Throwable> { showError(it) }
    private val observerLoading = Observer<Boolean> { showLoading(it) }
    private var text = ""
    private val job: Job = Job()
    private val queryStateFlow = MutableStateFlow("")
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val onListItemClickListener: DictionaryAdapter.OnListItemClickListener =
        object : DictionaryAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
               router.navigateTo(DetailsScreen().show(data))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = viewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(WORD_KEY, text)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            text = savedInstanceState.getString(WORD_KEY).toString()
            model.getData(text, true).observe(viewLifecycleOwner, observerData)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.getLoading().observe(viewLifecycleOwner, observerLoading)
        model.getError().observe(viewLifecycleOwner, observerErrors)
        setUpSearchStateFlow()
    }


    private fun setUpSearchStateFlow() {
        scope.launch {
            queryStateFlow
                .debounce(DEBOUNCE)
                .distinctUntilChanged()
                .collect { result ->
                    model.getData(result, true).observe(viewLifecycleOwner, observerData)
                }
        }

        viewBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { queryStateFlow.value = it }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                queryStateFlow.value = newText
                return true
            }
        })
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

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = DictionaryFragment()
        private const val WORD_KEY = "word_key"
        private const val DEBOUNCE : Long = 500
    }
}






