package com.udacity.asteroidradar.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )

        val application = requireActivity().application

        val asteroid = DetailFragmentArgs.fromBundle(requireArguments()).selectedAsteroid

        val viewModelFactory = DetailViewModel.Factory(application, asteroid)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.asteroid = asteroid
        binding.viewModel = viewModel

        viewModel.displayAuExplanation.observe(viewLifecycleOwner, {
            if (it == true) {
                displayAstronomicalUnitExplanationDialog()
                viewModel.doneDisplayingHelp()
            }
        })

        viewModel.navigateToMainFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigateUp()
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
