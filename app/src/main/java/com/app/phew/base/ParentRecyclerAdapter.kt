package com.app.phew.base

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.listners.OnItemClickListener
import com.app.phew.listners.PaginationAdapterCallback
import com.app.phew.preferences.LanguagePrefManager
import com.app.phew.preferences.SharedPrefManager

@Suppress("UNREACHABLE_CODE")
abstract class ParentRecyclerAdapter<Item> : RecyclerView.Adapter<ParentRecyclerViewHolder> {

    var mcontext: Context

    protected @JvmField
    var data: ArrayList<Item>

    protected var layoutId: Int = 0

    protected var isLoadingAdded = false

    protected var retryPageLoad = false

    protected lateinit var itemClickListener: OnItemClickListener

    protected var mPaginationAdapterCallback: PaginationAdapterCallback? = null

    protected var mSharedPrefManager: SharedPrefManager


    protected var mLanguagePrefManager: LanguagePrefManager


    constructor(context: Context, data: ArrayList<Item>) {
        this.mcontext = context
        this.data = data
        mSharedPrefManager = SharedPrefManager(context)
        mLanguagePrefManager =
            LanguagePrefManager(mcontext)
    }

    constructor(context: Context, data: ArrayList<Item>, layoutId: Int) {
        this.mcontext = context
        this.data = data
        this.layoutId = layoutId
        mSharedPrefManager = SharedPrefManager(context)
        mLanguagePrefManager =
            LanguagePrefManager(mcontext)
    }

    fun setOnPaginationClickListener(onPaginationClickListener: PaginationAdapterCallback) {
        this.mPaginationAdapterCallback = onPaginationClickListener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun InsertAll(items: List<Item>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun Insert(position: Int, item: Item) {
        data.add(position, item)
        Log.e("Test_Test", position.toString() + "")
        notifyDataSetChanged()
    }

    fun Insert(item: Item) {
        data.add(item)
        notifyDataSetChanged()
    }

    fun Delete(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    fun update(position: Int, item: Item) {
        data.removeAt(position)
        data.add(position, item)
        notifyDataSetChanged()
    }

    fun updateAll(items: List<Item>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun getData(): List<Item> {
        return data
    }

    fun addFooterProgress() {
        this.data.add(null!!)
        notifyItemInserted(data.size - 1)
    }

    fun removeFooterProgress() {
        data.removeAt(data.size - 1)
        notifyItemRemoved(data.size)
        Log.e("footer", "gone")
    }

    fun addLoadingFooter(item: Item) {
        isLoadingAdded = true
        data.add(item)
        notifyItemInserted(data.size - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = data.size - 1
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}
