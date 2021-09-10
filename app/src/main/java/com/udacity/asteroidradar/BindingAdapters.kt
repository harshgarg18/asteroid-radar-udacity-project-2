package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.main.AsteroidApiStatus
import com.udacity.asteroidradar.main.AsteroidListAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    data?.let {
        val adapter = recyclerView.adapter as AsteroidListAdapter
        adapter.submitList(it)
    }
}

@BindingAdapter("asteroidListStatus")
fun bindListStatus(progressBar: ProgressBar, status: AsteroidApiStatus?) {
    status?.let {
        when (it) {
            AsteroidApiStatus.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            AsteroidApiStatus.DONE -> {
                progressBar.visibility = View.GONE
            }
        }
    }
}

@BindingAdapter("pictureOfDayUrl")
fun bindPictureOfTheDayUrl(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        if (it.mediaType == "image") {
            val imgUri = it.url.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageView)
        } else {
            Picasso.get()
                .load(R.drawable.ic_broken_image)
                .into(imageView)
        }
    }
}

@BindingAdapter("pictureOfDayDesc")
fun bindPictureOfTheDayContentDescription(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        imageView.contentDescription = String.format(
            imageView.context.getString(R.string.nasa_picture_of_day_content_description_format),
            pictureOfDay.title
        )
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("asteroidStatusDesc")
fun bindDetailsStatusImageDescription(imageView: ImageView, isHazardous: Boolean) {
    imageView.contentDescription = imageView.context.getString(
        if (isHazardous) {
            R.string.potentially_hazardous_asteroid_image
        } else {
            R.string.not_hazardous_asteroid_image
        }
    )
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
