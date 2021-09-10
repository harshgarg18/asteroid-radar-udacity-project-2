package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModel.Factory(application)
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter = AsteroidListAdapter(AsteroidListAdapter.OnClickListener {
            viewModel.displayAsteroidDetails(it)
        })

        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, {
            if (it != null) {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayAsteroidDetailsCompleted()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu ->
                viewModel.onMenuItemFilterChanged(MenuOptionsFilter.WEEK)

            R.id.show_today_menu ->
                viewModel.onMenuItemFilterChanged(MenuOptionsFilter.TODAY)

            R.id.show_saved_menu ->
                viewModel.onMenuItemFilterChanged(MenuOptionsFilter.SAVED)
        }
        return true
    }
}
