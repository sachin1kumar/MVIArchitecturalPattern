package com.manager.mviarchitecturalpattern.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.manager.mviarchitecturalpattern.R
import com.manager.mviarchitecturalpattern.databinding.ActivityMainBinding
import com.manager.mviarchitecturalpattern.mvicomponents.MviActor
import com.manager.mviarchitecturalpattern.mvicomponents.MviContentHandler
import com.manager.mviarchitecturalpattern.mvicomponents.MviState
import com.manager.mviarchitecturalpattern.viewmodel.MviViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MviViewModel::class.java)

        val actor = MviActor(viewModel::takeAction)

        binding.actor = actor

        observeData(binding, viewModel)
    }

    private fun observeData(binding: ActivityMainBinding, viewModel: MviViewModel) {
        val contentHandler = MviContentHandler(binding)
        val stateObserver = Observer<MviState?> {
            // null state indicates there is no action needed
            it ?: return@Observer

            // Hide the loading state
            if (it != MviState.Loading) {
                hideLoading()
            }

            when (it) {
                is MviState.Loading -> showLoading()
                is MviState.InvalidNumberError -> {
                    Toast.makeText(this, "An error occurred!", Toast.LENGTH_SHORT).show()
                }
                is MviState.Content -> contentHandler.handleContent(it)
            }
        }

        viewModel._state.observe(this, stateObserver)
    }

    /**
     * Show the loading view
     */
    private fun showLoading() = findViewById<LinearLayout>(R.id.loadingView)?.apply { visibility = View.VISIBLE }

    /**
     * Hide the loading view
     */
    private fun hideLoading() = findViewById<LinearLayout>(R.id.loadingView)?.apply { visibility = View.GONE }
}