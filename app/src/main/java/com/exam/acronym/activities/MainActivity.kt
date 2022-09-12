package com.exam.acronym.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.exam.acronym.adapters.LfsItemListAdapter
import com.exam.acronym.databinding.ActivityMainBinding
import com.exam.acronym.entities.LoadResult
import com.exam.acronym.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Activity for the Main Page
 */
class MainActivity : AppCompatActivity() {

    // View binding variable
    lateinit var binding: ActivityMainBinding

    // View model
    private val viewModel by viewModel<MainViewModel>()

    // Recyclerview Adapter
    private val adapter = LfsItemListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        binding.btnSearch.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(this, "The keyword should not be empty.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.keyWordChanged(text)
            }
        }
        binding.btnRetry.setOnClickListener {
            viewModel.fetchData()
        }

        // Observe live data of the view model
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it == LoadResult.LOADING
            binding.recyclerView.isVisible = it == LoadResult.SUCCESS
            binding.errorViews.isVisible = it == LoadResult.FAILURE
        }
        viewModel.lfsItems.observe(this) {
            adapter.submitList(it)
        }
    }

}