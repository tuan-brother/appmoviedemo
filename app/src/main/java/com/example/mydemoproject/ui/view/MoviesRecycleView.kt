package com.example.mydemoproject.ui.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mydemoproject.R
import com.example.mydemoproject.base.GridSpacingItemDecoration
import com.example.mydemoproject.data.constants.Constants
import com.example.mydemoproject.databinding.LayoutItemMovieBinding
import com.example.mydemoproject.data.model.Movies
import com.example.mydemoproject.utils.SpannableStringBuilder
import com.example.mydemoproject.utils.setMarginStartAndEnd

class MoviesRecycleView : RecyclerView {
    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        ctx,
        attrs,
        defStyleAttr
    )

    val customAdapter by lazy { Adapter(context) }

    init {
        val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)
        layoutManager = gridLayoutManager
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx == 0 && dy == 0) {
                    return
                }
                if (!customAdapter.hasNext)
                    return

                val totalCountItem = gridLayoutManager.itemCount
                val visibleItemCount = gridLayoutManager.childCount
                val firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
                if (!customAdapter.isLoading && totalCountItem - visibleItemCount <= firstVisibleItem) {
                    customAdapter.isLoading = true
                    customAdapter.callBack?.onNextClick()
                }
            }
        })
        adapter = customAdapter
    }

    class Adapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {
        var listItem = mutableListOf<Movies>()
        var callBack: moviesAdapterCallback? = null

        var hasNext = false
        var isLoading = false

        fun refresh(items: List<Movies>) {
            listItem.apply {
                clear()
                listItem.addAll(items)
            }
            notifyDataSetChanged()
        }

        fun add(items: List<Movies>) {
            val startPosition = listItem.size
            val insertCount = items.size
            listItem.addAll(items)
            notifyItemRangeInserted(startPosition, insertCount)
            isLoading = false
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ItemViewHolder(
                LayoutItemMovieBinding.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            when (holder) {
                is ItemViewHolder -> onBindItemViewHolder(holder, position)
            }
        }

        override fun getItemCount(): Int {
            return listItem.size
        }

        private fun onBindItemViewHolder(viewHolder: ItemViewHolder, position: Int) {
            val data = listItem[position]
            viewHolder.binding.apply {
                titleTextView.text = data.title
                yearTextView.text = data.vote_count.toString()
                rankTextView.text =
                    SpannableStringBuilder(data.vote_average.toString(), '.', 0.7f)
                imgUrl = Constants.START_URL_IMAGE + data.backdrop_path
                if (position % 2 == 0) {
                    root.setMarginStartAndEnd(R.dimen.dp_12, R.dimen.dp_6)
                } else {
                    root.setMarginStartAndEnd(R.dimen.dp_6, R.dimen.dp_12)
                }
            }
        }

        class ItemViewHolder(val binding: LayoutItemMovieBinding) :
            RecyclerView.ViewHolder(binding.root)

        interface moviesAdapterCallback {
            fun onNextClick()
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}