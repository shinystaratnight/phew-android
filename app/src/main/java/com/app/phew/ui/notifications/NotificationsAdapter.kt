package com.app.phew.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.listners.OnNotificationsListener
import com.app.phew.models.notifications.NotificationsModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recycler_item_notification.view.*

class NotificationsAdapter(
    private val context: Context, data: ArrayList<NotificationsModel>,
    layoutId: Int, private val mListener: OnNotificationsListener
) : ParentRecyclerAdapter<NotificationsModel>(context, data, layoutId) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val model = data[position]
        val myView = viewHolder.itemView
        when(model.key){
            "new_friend_request"->{
                myView.acceptLayout.visibility = View.VISIBLE
                myView.imvNotificationIc.visibility = View.GONE
                myView.tvNotificationTitle.text = model.body
                myView.tvNotificationDate.text = model.created_time
                Glide.with(context).load(model.sender_data?.profile_image).into(myView.imvClientImage)
                myView.tvAccept.setOnClickListener {
                    mListener.onAcceptClick(position,model.sender_data?.id?:1,model.id)
                }
                myView.tvCancel.setOnClickListener {
                    mListener.onRejectClick(position,model.sender_data?.id?:1,model.id)
                }

            }
            "management_message"->{
                myView.acceptLayout.visibility = View.GONE
                myView.imvNotificationIc.visibility = View.GONE
                myView.tvNotificationTitle.text = model.body
                myView.tvNotificationDate.text = model.created_time
                Glide.with(context).load(ContextCompat.getDrawable(context, R.drawable.ic_msg_admin)).into(myView.imvClientImage)
            }
            "retweeted_post"->{
                myView.acceptLayout.visibility = View.GONE
                myView.imvNotificationIc.visibility = View.VISIBLE
                myView.tvNotificationTitle.text = model.body
                myView.tvNotificationDate.text = model.created_time
                Glide.with(context).load(model.sender_data?.profile_image).into(myView.imvClientImage)
                myView.imvNotificationIc.background = ContextCompat.getDrawable(context, R.drawable.ic_echo_notify)
            }
            "follow"->{
                myView.acceptLayout.visibility = View.GONE
                myView.imvNotificationIc.visibility = View.VISIBLE
                myView.tvNotificationTitle.text = model.body
                myView.tvNotificationDate.text = model.created_time
                Glide.with(context).load(model.sender_data?.profile_image).into(myView.imvClientImage)
                myView.imvNotificationIc.background = ContextCompat.getDrawable(context, R.drawable.ic_follow)
            }
            "comment"->{
                myView.acceptLayout.visibility = View.GONE
                myView.imvNotificationIc.visibility = View.VISIBLE
                myView.tvNotificationTitle.text = model.body
                myView.tvNotificationDate.text = model.created_time
                Glide.with(context).load(model.sender_data?.profile_image).into(myView.imvClientImage)
                myView.imvNotificationIc.background = ContextCompat.getDrawable(context, R.drawable.ic_comment_notify)
            }
            "like"->{
                myView.acceptLayout.visibility = View.GONE
                myView.imvNotificationIc.visibility = View.VISIBLE
                myView.tvNotificationTitle.text = model.body
                myView.tvNotificationDate.text = model.created_time
                Glide.with(context).load(model.sender_data?.profile_image).into(myView.imvClientImage)
                myView.imvNotificationIc.background = ContextCompat.getDrawable(context, R.drawable.ic_emoji)
            }
        }


    }

    private inner class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }
}