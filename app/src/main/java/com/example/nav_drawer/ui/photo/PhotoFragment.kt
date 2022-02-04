package com.example.nav_drawer.ui.photo

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.nav_drawer.databinding.FragmentPhotoBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class PhotoFragment : Fragment() {

    private lateinit var viewModel: PhotoViewModel
    private var _binding: FragmentPhotoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[PhotoViewModel::class.java]

        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        //loadPicture()
        binding.input.error = null

        binding.input.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener loadPicture()
            }

            return@setOnEditorActionListener false
        }

        binding.button.setOnClickListener {
            loadPicture()
        }

        return root
    }

    private fun loadPicture(): Boolean {
        val search = binding.input.text.toString()

        if (search.isBlank()) {
            binding.input.error = "Строка поиска пустая"
            return true
        }

        val encodedSearch = URLEncoder.encode(search, StandardCharsets.UTF_8.name())

        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        Glide
            .with(this)
            .load("https://source.unsplash.com/random/800x600?$encodedSearch")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            /*.centerCrop()*/
            /*.placeholder(R.drawable.ic_menu_camera)*/
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(binding.image)

        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}