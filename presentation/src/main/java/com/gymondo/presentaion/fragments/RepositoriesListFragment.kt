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
    private var projectsAdapter: RepositoriesListAdapter? = null
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

        projectsAdapter = RepositoriesListAdapter(onRepositoryClicked = this)

        binding?.repositoriesRecyclerView?.apply {
            layoutManager = viewManager
            adapter = projectsAdapter
        }
        lifecycleScope.launchWhenStarted {
            viewModel.repositoriesStateFlow.collect {

                when (it) {

                    is RepositoriesListState.Failure -> {
                    }

                    RepositoriesListState.Idle -> {

                    }
                    RepositoriesListState.Loading -> {
                    }
                    is RepositoriesListState.Success -> projectsAdapter?.updateData(it.data)
                }

            }
        }


//       projectsSearchSwipeRefresh.isEnabled = false

        binding?.repositoriesRecyclerView
            ?.addOnScrollListener(object : EndlessRecyclerViewScrollListener(viewManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    viewModel.loadMore(page.plus(1))
                }
            })
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
                Log.i("onQueryTextChange", newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i("onQueryTextSubmit", query)
                viewModel.search(query)
                return true
            }
        }
        searchView?.setOnQueryTextListener(queryTextListener)

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            return false
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