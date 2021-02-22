package com.gymondo.presentaion.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.gymondo.presentaion.GitHubViewModel
import com.gymondo.presentaion.R
import com.gymondo.presentaion.databinding.FragmentRepositoryDetailsBinding
import com.gymondo.presentaion.model.RepositoryView
import com.gymondo.presentaion.state.RepositoryDetailsState
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel


class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {
    private var binding: FragmentRepositoryDetailsBinding? = null
    private lateinit var repoName: String
    private lateinit var userName: String
    private val viewModel: GitHubViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repoName = RepositoryDetailsFragmentArgs.fromBundle(it).repoName
            userName = RepositoryDetailsFragmentArgs.fromBundle(it).username
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailsBinding.bind(view)

        viewModel.getRepositoryDetails(userName, repoName)
        lifecycleScope.launchWhenCreated {
            viewModel.repositoryDetailsStateFlow.collect {
                when (it) {
                    is RepositoryDetailsState.Failure -> onFailure(it.failureMessage)
                    RepositoryDetailsState.Loading -> onLoading()
                    is RepositoryDetailsState.Success -> onSuccess(it.data)
                    RepositoryDetailsState.Idle -> {
                        // just I need this one for default state-flow
                    }
                }
            }
        }
    }

    private fun onLoading() {
        binding?.repoDetailsContentGroup?.visibility = View.GONE
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.errorTextView?.visibility = View.GONE
    }

    private fun onFailure(msg: String) {
        binding?.repoDetailsContentGroup?.visibility = View.GONE
        binding?.progressBar?.visibility = View.GONE
        binding?.errorTextView?.let {
            it.visibility = View.VISIBLE
            it.text = msg
        }
    }

    private fun onSuccess(repositoryView: RepositoryView) {

        binding?.ownerNameTextView?.text = repositoryView.owner.name

        binding?.projectNameTextView?.text = repositoryView.name
        binding?.projectDescriptionTextView?.text = repositoryView.description ?: ""

        binding?.starTextView?.text = repositoryView.stargazersCount.toString()

        binding?.repositoryImageView?.load(repositoryView.owner.avatarUrl) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding?.repositoryUrlWebView?.loadUrl(repositoryView.url)
        binding?.repoDetailsContentGroup?.visibility = View.VISIBLE
        binding?.progressBar?.visibility = View.GONE
        binding?.errorTextView?.visibility = View.GONE

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}