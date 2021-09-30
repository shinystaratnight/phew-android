package com.app.phew.ui.phewPremiumMemberShip

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.listners.OnNotificationsListener
import com.app.phew.models.packages.PackagesModel
import kotlinx.android.synthetic.main.recycler_item_package.view.*

class PackagesAdapter(
        private val context: Context, data: ArrayList<PackagesModel>,
        layoutId: Int, private val mListener: OnPackageClick
) : ParentRecyclerAdapter<PackagesModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView
        myView.tvPackageName.text = context.resources.getString(R.string.packagee) +" "+model.id
        myView.tvPackageDate.text = model.period.toString()+" "+model.period_type
        myView.tvPackagePrice.text = model.price.toString()+" "+context.resources.getString(R.string.sar)
       if (model.isSelected){
           myView.rootLayout.background = ContextCompat.getDrawable(context,R.drawable.ic_bg_selected_pack)
           myView.tvPackageName.setTextColor(ContextCompat.getColor(context,R.color.white))
           myView.tvPackageDate.setTextColor(ContextCompat.getColor(context,R.color.white))
           myView.tvPackagePrice.setTextColor(ContextCompat.getColor(context,R.color.white))
       }else{
           myView.rootLayout.background = ContextCompat.getDrawable(context,R.drawable.ic_bg_packw)
           myView.tvPackageName.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
           myView.tvPackageDate.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
           myView.tvPackagePrice.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
       }
        myView.setOnClickListener {
            mListener.onPackageClick(position,model)
        }

    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
    interface OnPackageClick{
        fun onPackageClick(position: Int,model:PackagesModel)
    }
}