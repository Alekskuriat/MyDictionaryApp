package com.example.mydictionaryapp.view.historyScreen

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mydictionaryapp.R
import com.example.mydictionaryapp.databinding.FragmentHistoryBinding
import com.example.mydictionaryapp.domain.database.HistoryEntity
import com.example.mydictionaryapp.view.detailsScreen.DetailsScreen
import com.example.mydictionaryapp.view.historyScreen.RV.HistoryAdapter
import com.example.mydictionaryapp.viewModel.HistoryViewModel.HistoryViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val router : Router by inject()
    private val viewModel: HistoryViewModel by viewModel()
    private var adapter: HistoryAdapter? = null
    private val viewBinding: FragmentHistoryBinding by viewBinding()
    private val observerData = Observer<List<HistoryEntity>> { showWords(it) }
    private val observerHistory = Observer<List<HistoryEntity>> { showWords(it) }
    private val observerErrors = Observer<Throwable> { showError(it) }
    private val observerLoading = Observer<Boolean> { showLoading(it) }
    private val job: Job = Job()
    private val queryStateFlow = MutableStateFlow("")
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)

    private val onListHistoryItemClickListener : HistoryAdapter.OnListHistoryItemClickListener =
        object : HistoryAdapter.OnListHistoryItemClickListener {
            override fun onItemClick(data: HistoryEntity) {
                router.navigateTo(DetailsScreen().show(data))
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLoading().observe(viewLifecycleOwner, observerLoading)
        viewModel.getError().observe(viewLifecycleOwner, observerErrors)
        viewModel.getData().observe(viewLifecycleOwner, observerData)
        setUpSearchStateFlow()
    }

    private fun setUpSearchStateFlow() {
        scope.launch {
            queryStateFlow
                .debounce(DEBOUNCE)
                .distinctUntilChanged()
                .collect { result ->
                    if (result.isNotEmpty())
                    viewModel.getHistorySearch(result).observe(viewLifecycleOwner, observerHistory)
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

    private fun showWords(data: List<HistoryEntity>?) {
        if (data == null || data.isEmpty()) {
            showErrorScreen(getString(R.string.db_empty))
        } else {
            showViewSuccess()
            if (adapter == null) {
                viewBinding.apply {
                    historyActivityRecyclerview.layoutManager =
                        LinearLayoutManager(context)
                    historyActivityRecyclerview.adapter =
                        HistoryAdapter(onListHistoryItemClickListener, data)
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
                viewModel.getData()
                    .observe(viewLifecycleOwner, observerData)
            }
        }
    }

    private fun showViewSuccess() {
        viewBinding.apply {
            historyActivityRecyclerview.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            errorLinearLayout.visibility = View.GONE
        }
    }

    private fun showViewLoading() {
        viewBinding.apply {
            historyActivityRecyclerview.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            errorLinearLayout.visibility = View.GONE
        }
    }

    private fun showViewError() {
        viewBinding.apply {
            historyActivityRecyclerview.visibility = View.GONE
            progressBar.visibility = View.GONE
            errorLinearLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    companion object {
        private const val DEBOUNCE = 500L
        fun newInstance() = HistoryFragment()
    }
}

