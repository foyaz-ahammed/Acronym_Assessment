package com.exam.acronym.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.acronym.entities.DataResult
import com.exam.acronym.entities.LoadResult
import com.exam.acronym.network.Response
import com.exam.acronym.repositories.AcronymRepository
import kotlinx.coroutines.launch

/**
 * [ViewModel] class used on Main Page
 */
class MainViewModel(private val repository: AcronymRepository): ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.simpleName
        private const val MAX_ITEM_COUNT = 6
    }

    private val _lfsItems = MutableLiveData<List<Response.LfsItem>>()
    private val _loading = MutableLiveData(LoadResult.NONE)
    private val keyWord = MutableLiveData<String>()

    val lfsItems: LiveData<List<Response.LfsItem>>
        get() = _lfsItems
    val loading: LiveData<LoadResult>
        get() = _loading

    fun keyWordChanged(text: String) {
        if (keyWord.value != text) {
            keyWord.value = text
            fetchData()
        }
    }

    fun fetchData() {
        _loading.value = LoadResult.LOADING
        viewModelScope.launch {
            when (val result = repository.getMeanings(keyWord.value?:"")) {
                is DataResult.Success -> {
                    Log.w(TAG, "Success getting items: " + result.data)
                    val sortedData = result.data.sortedWith(compareBy( {it.freq}, {it.since} ))
                    _lfsItems.value = sortedData.subList(0, MAX_ITEM_COUNT.coerceAtMost(sortedData.size))
                    _loading.value = LoadResult.SUCCESS
                }
                is DataResult.Failure -> {
                    Log.w(TAG, "Failed loading data: " + result.exception)
                    _loading.value = LoadResult.FAILURE
                }
            }
        }
    }

}