package com.gymondo.presentaion

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gymondo.app.domain.usecases.GetRepositoryDetailsUseCase
import com.gymondo.app.domain.usecases.GitHubSearchUseCase
import com.gymondo.presentaion.mapper.RepositoryMapper
import com.gymondo.presentaion.mapper.SearchResponseMapper
import com.gymondo.presentaion.model.RepositoryView
import com.gymondo.presentaion.state.Resource
import com.gymondo.presentaion.state.ResourceState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GitHubViewModel(
    private val gitHubSearchUseCase: GitHubSearchUseCase,
    private val getRepositoryDetailsUseCase: GetRepositoryDetailsUseCase,
    private val searchResponseMapper: SearchResponseMapper,
    private val repositoryMapper: RepositoryMapper

) : ViewModel() {

    private val repositoriesMutableLiveData: MutableLiveData<Resource<List<RepositoryView>>> by lazy {
        MutableLiveData()
    }

    fun search(q: String) {
        viewModelScope.launch {
            gitHubSearchUseCase.execute(GitHubSearchUseCase.Params.create(q, 1, 1))
                .onStart {
                    Log.d(javaClass.name, "onstart")
                }
                .catch {
                    Log.d(javaClass.name, "catch")
                }
                .collect {
                    repositoriesMutableLiveData.value =
                        Resource(
                            status = ResourceState.Success,
                            data = searchResponseMapper.mapToView(it).repositories
                        )
                    Log.d(javaClass.name, "catch")
                }
        }
    }

}