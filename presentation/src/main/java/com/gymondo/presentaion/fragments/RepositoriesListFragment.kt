package com.gymondo.presentaion.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.gymondo.presentaion.EndlessRecyclerViewScrollListener
import com.gymondo.presentaion.GitHubViewModel
import com.gymondo.presentaion.R
import com.gymondo.presentaion.adapters.RepositoriesListAdapter
import com.gymondo.presentaion.databinding.FragmentRepositoriesListBinding
import com.gymondo.presentaion.model.RepositoryView
import com.gymondo.presentaion.state.RepositoriesListState
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list),
    RepositoriesListAdapter.OnRepositoryClickListener {

    private var binding: FragmentRepositoriesListBinding? = null
    private var repositoriesAdapter: RepositoriesListAdapter? = null
    private val viewModel: GitHubViewModel by sharedViewModel()

    private lateinit var viewManager: LinearLayoutManager
    private var onItemClicked: RepositoriesListAdapter.OnRepositoryClickListener? = null
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onItemClicked = activity as? RepositoriesListAdapter.OnRepositoryClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesListBinding.bind(view)
        Log.d(javaClass.name, "onViewCreated")

        retainInstance = true
        viewManager = LinearLayoutManager(context)

        repositoriesAdapter = RepositoriesListAdapter(onRepositoryClicked = this)

        binding?.repositoriesRecyclerView?.apply {
            layoutManager = viewManager
            adapter = repositoriesAdapter
        }
        binding?.swipeRefreshLayout?.isEnabled = false

        lifecycleScope.launchWhenStarted {
            viewModel.repositoriesStateFlow.collect {
                when (it) {
                    is RepositoriesListState.Failure -> onFailure(it.failureMessage)
                    RepositoriesListState.Idle -> onIdle()
                    RepositoriesListState.Loading -> onLoading()
                    is RepositoriesListState.Success -> onSuccess(it.data)
                }
            }
        }

        binding?.repositoriesRecyclerView?.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(viewManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.loadMore(page.plus(1))
            }
        })
    }

    private fun onSuccess(reposList: List<RepositoryView>) {
        repositoriesAdapter?.updateData(reposList)
        binding?.swipeRefreshLayout?.isRefreshing = false
        binding?.repositoriesRecyclerView?.visibility = View.VISIBLE


    }

    private fun onFailure(failureMsg: String) {
        if (repositoriesAdapter?.itemCount ?: 0 == 0) {

            binding?.msgTextView?.let {
                it.text = failureMsg
                it.visibility = View.VISIBLE
            }

        } else {
            view?.let {
                Snackbar.make(it, failureMsg, Snackbar.LENGTH_LONG).show()
            }
        }
        binding?.swipeRefreshLayout?.isRefreshing = false

    }

    private fun onIdle() {
        repositoriesAdapter?.updateData(emptyList())
        binding?.swipeRefreshLayout?.isRefreshing = false
        binding?.msgTextView?.let {
            it.text = getString(R.string.start_typing_to_search)
            it.visibility = View.VISIBLE
        }
        binding?.repositoriesRecyclerView?.visibility = View.GONE
    }


    private fun onLoading() {
        binding?.swipeRefreshLayout?.isRefreshing = true
        binding?.msgTextView?.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.repositories_search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem.actionView as? SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return true
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            searchView?.isFocusable = false
        }
        searchView?.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onItemClicked(repository: RepositoryView) {
        val action =
            RepositoriesListFragmentDirections.actionRepositoriesListFragmentToRepositoryDetailsFragment(
                repository.owner.name,
                repository.name
            )
        view?.findNavController()?.navigate(action)
    }
}