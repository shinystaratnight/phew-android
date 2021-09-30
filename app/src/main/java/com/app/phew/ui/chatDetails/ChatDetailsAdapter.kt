package com.app.phew.ui.chatDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.phew.R
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.chatDetails.ChatDetailsModel
import com.app.phew.utils.AudioPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_chat_text_me.view.*
import kotlinx.android.synthetic.main.item_chat_text_other.view.*
import kotlinx.android.synthetic.main.item_chat_video_me.view.*
import kotlinx.android.synthetic.main.item_chat_video_other.view.*
import kotlinx.android.synthetic.main.item_chat_voice_me.view.*
import kotlinx.android.synthetic.main.item_chat_voice_other.view.*


class ChatDetailsAdapter(var context: Context,
                         var data: ArrayList<ChatDetailsModel>,var mListener:OnMessageClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentRecyclerViewHolder {
        var itemView: View? = null
        when (viewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_text_me, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_MESSAGE_RECEIVED -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_text_other, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_IMAGE_SENT -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_video_me, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_IMAGE_RECEIVED -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_video_other, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_VIDEO_SENT -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_video_me, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_VIDEO_RECEIVED -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_video_other, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_VOICE_SENT -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_voice_me, parent, false)
                ViewHolder(itemView!!)
            }

            VIEW_TYPE_VOICE_RECEIVED -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_voice_other, parent, false)
                ViewHolder(itemView!!)
            }
        }

        return ViewHolder(itemView!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position].message_position == "current") {
            return when (data[position].message_type) {

                "text" -> {
                    VIEW_TYPE_MESSAGE_RECEIVED
                }
                "image" -> {
                    VIEW_TYPE_IMAGE_RECEIVED
                }
                "video" -> {
                    VIEW_TYPE_VIDEO_RECEIVED
                }
                else -> {
                    VIEW_TYPE_VOICE_RECEIVED
                }
            }
        } else {
            return when (data[position].message_type) {
                "text" -> {
                    VIEW_TYPE_MESSAGE_SENT
                }
                "image" -> {
                    VIEW_TYPE_IMAGE_SENT
                }
                "video" -> {
                    VIEW_TYPE_VIDEO_SENT
                }
                else -> {
                    VIEW_TYPE_VOICE_SENT
                }

            }
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
        private const val VIEW_TYPE_IMAGE_SENT = 3
        private const val VIEW_TYPE_IMAGE_RECEIVED = 4
        private const val VIEW_TYPE_VIDEO_SENT = 5
        private const val VIEW_TYPE_VIDEO_RECEIVED = 6
        private const val VIEW_TYPE_VOICE_SENT = 7
        private const val VIEW_TYPE_VOICE_RECEIVED = 8
    }

    private class ViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView) {
        init {
            setClickableRootView(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val message = data[position]
        if (message.message_position == "current") {
            when (message.message_type) {
                "text" -> {
                    viewHolder.itemView.tvAgoTO.text = message.ago_time
                    viewHolder.itemView.tvMessageTO.text = message.message
                }
                "image" -> {
                    viewHolder.itemView.tvAgoVO.text = message.ago_time
                    Glide.with(context).load(message.message).into(viewHolder.itemView.imvMessageVO)
                    viewHolder.itemView. imvVideo.visibility = View.GONE
                    viewHolder.itemView.setOnClickListener { mListener.onMessage(message) }
                }
                "video" -> {
                    viewHolder.itemView. imvVideo.visibility = View.VISIBLE
                    viewHolder.itemView.tvAgoVO.text = message.ago_time
                    val requestOptions = RequestOptions()
                    requestOptions.placeholder(R.color.colorPrimary)
                    requestOptions.error(R.color.colorPrimary)
                    Glide.with(context)
                        .load(message.message)
                        .apply(requestOptions)
                        .thumbnail(Glide.with(context).load(message.message))
                        .into(viewHolder.itemView.imvMessageVO)
                    viewHolder.itemView.setOnClickListener { mListener.onMessage(message) }
                }
                "voice_message" -> {
                    viewHolder.itemView.tvAgoO.text = message.ago_time
                    viewHolder.itemView.voicePlayerViewO.setAudioTarget(message.message)
                }
            }

        } else {
            when (message.message_type) {
                "text" -> {
                    if (position>0&&data[position-1].message_position!="current"){
                        viewHolder.itemView.imvUserImageTMe.visibility = View.GONE
                        viewHolder.itemView.tvAgoTMe.visibility = View.GONE
                    }
                    Glide.with(context).load(message.sender_data.profile_image)
                        .into(viewHolder.itemView.imvUserImageTMe)
                    viewHolder.itemView.tvAgoTMe.text = message.ago_time
                    viewHolder.itemView.tvMessageTMe.text = message.message
                }
                "image" -> {
                    if (position>0&&data[position-1].message_position!="current"){
                        viewHolder.itemView.imvUserImageVM.visibility = View.GONE
                        viewHolder.itemView.tvAgoVM.visibility = View.GONE
                    }
                    Glide.with(context).load(message.sender_data.profile_image)
                        .into(viewHolder.itemView.imvUserImageVM)
                    viewHolder.itemView.tvAgoVM.text = message.ago_time
                    Glide.with(context).load(message.message).into(viewHolder.itemView.imvMessageVM)

                    viewHolder.itemView.setOnClickListener { showImageDialog(message.message) }
                }
                "video" -> {
                    if (position>0&&data[position-1].message_position!="current"){
                        viewHolder.itemView.imvUserImageVM.visibility = View.GONE
                        viewHolder.itemView.tvAgoVM.visibility = View.GONE
                    }
                    viewHolder.itemView. imvVideoM.visibility = View.VISIBLE
                    Glide.with(context).load(message.sender_data.profile_image)
                        .into(viewHolder.itemView.imvUserImageVM)
                    viewHolder.itemView.tvAgoVM.text = message.ago_time
                    val requestOptions = RequestOptions()
                    requestOptions.placeholder(R.color.colorPrimary)
                    requestOptions.error(R.color.colorPrimary)

                    Glide.with(context)
                        .load(message.message)
                        .apply(requestOptions)
                        .thumbnail(Glide.with(context).load(message.message))
                        .into(viewHolder.itemView.imvMessageVM)
                }
                "voice_message" -> {
                    if (position>0&&data[position-1].message_position!="current"){
                        viewHolder.itemView.imvUserImage.visibility = View.GONE
                        viewHolder.itemView.tvAgo.visibility = View.GONE
                    }
                    Glide.with(context).load(message.sender_data.profile_image)
                        .into(viewHolder.itemView.imvUserImage)
                    viewHolder.itemView.tvAgo.text = message.ago_time
                    viewHolder.itemView.voicePlayerView.setAudioTarget(message.message)

                }
            }
        }

    }

    private fun showImageDialog(image: String) {
        /*  val dialog = Dialog(context)
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
          dialog.setContentView(R.layout.dialog_chat_image)
          dialog.window!!.setLayout(
              ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
          )
          dialog.ibChatImageClose.setOnClickListener { dialog.dismiss() }
          Glide.with(context).load(image).into(dialog.ivChatImage)
          dialog.window!!.setGravity(Gravity.CENTER)
          dialog.setCancelable(false)
          dialog.show()*/
    }
    interface OnMessageClick{
        fun onMessage(model:ChatDetailsModel)
    }
}
