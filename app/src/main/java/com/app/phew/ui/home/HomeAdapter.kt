package com.app.phew.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.PopupMenu
import com.app.phew.R
import com.app.phew.base.ParentRecyclerAdapter
import com.app.phew.base.ParentRecyclerViewHolder
import com.app.phew.models.auth.UserModel
import com.app.phew.models.home.ActivityModel
import com.app.phew.models.home.HomeModel
import com.app.phew.models.images.ImageModel
import com.app.phew.models.places.MapsSearchData
import com.bumptech.glide.Glide
import com.github.pgreze.reactions.PopupGravity
import com.github.pgreze.reactions.ReactionPopup
import com.github.pgreze.reactions.dsl.reactionConfig
import com.github.pgreze.reactions.dsl.reactions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.recycler_item_home_activities.view.*
import kotlinx.android.synthetic.main.recycler_item_home_activities_share.view.*
import kotlinx.android.synthetic.main.recycler_item_home_posts.view.*
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.*
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.ibEchoPostsItemRate
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.ibEchoPostsItemShare
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.ivEchoItemUserImage
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.ivEchoPostsItemUserImage
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.rvEchoItemImages
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoItemText
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoItemTime
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoItemUserName
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoPostsItemComments
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoPostsItemMenu
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoPostsItemReactions
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoPostsItemScreenShots
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoPostsItemTime
import kotlinx.android.synthetic.main.recycler_item_home_posts_share.view.tvEchoPostsItemUserName
import kotlinx.android.synthetic.main.recycler_item_home_posts_share_without_comment.view.*
import kotlinx.android.synthetic.main.recycler_item_home_secret_message.view.*
import kotlinx.android.synthetic.main.recycler_item_home_sponsors.view.*

/**
 * Created by Mohamed Balsha on 2/25/2021.
 */

class HomeAdapter(
        context: Context, dataList: ArrayList<HomeModel>, private val mListener: HomeItemClickListeners
) : ParentRecyclerAdapter<HomeModel>(context, dataList) {

    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    when (viewType) {
                        HomeRows.LOADING.type_num -> R.layout.recycler_item_loading
                        HomeRows.POST_FIRST_NORMAL.type_num -> R.layout.recycler_item_home_posts
                        HomeRows.POST_FIRST_ACTIVITY.type_num -> R.layout.recycler_item_home_activities
                        HomeRows.ECHO_WITHOUT_COMMENT.type_num -> R.layout.recycler_item_home_posts_share_without_comment
                        HomeRows.ECHO_WITH_COMMENT.type_num -> R.layout.recycler_item_home_posts_share
                        HomeRows.POST_ECHO_NORMAL.type_num -> R.layout.recycler_item_home_posts_share
                        HomeRows.POST_ECHO_ACTIVITY.type_num -> R.layout.recycler_item_home_activities_share
                        HomeRows.POST_SECRET_MESSAGE.type_num -> R.layout.recycler_item_home_secret_message
                        else -> R.layout.recycler_item_home_sponsors
                    }, parent, false
            )
    )

    override fun onBindViewHolder(holder: ParentRecyclerViewHolder, position: Int) {
        val itemData = data[position]
        val itemView = holder.itemView
        val viewType = getItemViewType(position)
        when (viewType) {
            HomeRows.LOADING.type_num -> {
            }
            HomeRows.POST_FIRST_NORMAL.type_num -> {
                itemView.tvFirstPostsItemScreenShots.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id && !itemData.data?.screen_shots.isNullOrEmpty()) View.VISIBLE else View.GONE
                itemView.tvFirstPostsItemScreenShots.text =
                        itemData.data?.screen_shots?.size.toString()
                itemView.tvFirstPostsItemMenu.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id) View.VISIBLE else View.GONE
                itemView.tvFirstPostsItemMenu.setOnClickListener {
                    val popup = PopupMenu(itemView.context, itemView.tvFirstPostsItemMenu)
                    popup.menuInflater.inflate(R.menu.menu_home_item_options, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuDelete -> {
                                mListener.onDeleteClick(position, itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuFindlay -> {
                                mListener.onFindlayClick(itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuUpdatePrivacy -> {
                                mListener.onUpdatePrivacyClick(itemData.data?.id ?: 0)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                Glide.with(itemView.context).load(itemData.data?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_anonymous).into(itemView.ivFirstPostsItemUserImage)
                itemView.ivFirstPostsItemUserImage.setOnClickListener {
                    mListener.onUserClick(itemData.data?.user?.id ?: 0)
                }
                itemView.tvFirstPostsItemUserName.text = itemData.data?.user?.fullname.toString()
                itemView.tvFirstPostsItemText.text = itemData.data?.text.toString()
                itemView.tvFirstPostsItemTime.text = itemData.data?.created_ago.toString()
                itemView.ibFirstPostsItemRate.setImageResource(
                        if (itemData.data?.is_fav == true) R.drawable.ic_star_post_on else R.drawable.ic_star_post_off
                )
                itemView.ibFirstPostsItemRate.setOnClickListener {
                    itemView.ibFirstPostsItemRate.setImageResource(
                            if (itemData.data?.is_fav == true) R.drawable.ic_star_post_off else R.drawable.ic_star_post_on
                    )
                    mListener.onFavoriteClick(itemData.data?.id ?: 0)
                }

                itemView.ibFirstPostsItemShare.setOnClickListener {
                    mListener.onEchoClick(itemData.data?.id ?: 0, itemData.data?.post_type.toString())
                }

                if (itemData.data?.likes_count != null && itemData.data.likes_count > 0) {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvFirstPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                when (itemData.data.like_type.toString()) {
                                    "laugh" -> R.drawable.ic_react_laugh_on
                                    "love" -> R.drawable.ic_react_love_on
                                    "dislike" -> R.drawable.ic_react_dislike_on
                                    else -> {
                                        itemData.data.like_type = ""
                                        R.drawable.ic_react_love_off
                                    }
                                }, 0, 0, 0
                        )
                    else itemView.tvFirstPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, when (itemData.data.like_type.toString()) {
                        "laugh" -> R.drawable.ic_react_laugh_on
                        "love" -> R.drawable.ic_react_love_on
                        "dislike" -> R.drawable.ic_react_dislike_on
                        else -> {
                            itemData.data.like_type = ""
                            R.drawable.ic_react_love_off
                        }
                    }, 0
                    )

                    itemView.tvFirstPostsItemReactions.text = when {
                        itemData.data.likes_count < 1000 -> itemData.data.likes_count.toString()
                        itemData.data.likes_count in 1000..999999 ->
                            String.format("%.2fK", (itemData.data.likes_count / 1000).toFloat())
                        else -> String.format(
                                "%.2fM", (itemData.data.likes_count / 1000000).toFloat()
                        )
                    }
                } else {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvFirstPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_react_love_off, 0, 0, 0
                        )
                    else itemView.tvFirstPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_react_love_off, 0
                    )
                    itemView.tvFirstPostsItemReactions.text = "0"
                }
                itemView.tvFirstPostsItemReactions.setOnTouchListener(
                        showReaction(itemView.tvFirstPostsItemReactions.context, itemData.data?.id ?: 0)
                )

                if (itemData.data?.comments_count != null)
                    itemView.tvFirstPostsItemComments.text = when {
                        itemData.data.comments_count < 1000 -> itemData.data.comments_count.toString()
                        itemData.data.comments_count in 1000..999999 ->
                            String.format("%.2fK", (itemData.data.comments_count / 1000).toFloat())
                        else -> String.format(
                                "%.2fM", (itemData.data.comments_count / 1000000).toFloat()
                        )
                    }
                else itemView.tvFirstPostsItemComments.text = "0"

                val mImages = ArrayList<ImageModel>()
                if (!itemData.data?.images.isNullOrEmpty())
                    for (image in itemData.data?.images!!) {
                        image.type = "image"
                        mImages.add(image)
                    }
                if (!itemData.data?.videos.isNullOrEmpty())
                    for (video in itemData.data?.videos!!) {
                        video.type = "video"
                        mImages.add(video)
                    }

                if (!mImages.isNullOrEmpty()) {
                    itemView.rvFirstPostsItemImages.visibility = View.VISIBLE
                    itemView.rvFirstPostsItemImages.adapter =
                            PostAttachmentsAdapter(mcontext, mImages, object : PostAttachmentsAdapter.OnAttachmentListener {
                                override fun onAttachmentListener() {
                                    mListener.onPhotosClick(mImages)
                                }
                            })
                } else itemView.rvFirstPostsItemImages.visibility = View.GONE
                itemView.setOnClickListener { mListener.onPostClicked(position) }
            }
            HomeRows.POST_FIRST_ACTIVITY.type_num -> {
                itemView.tvFirstActivitiesItemScreenShots.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id && !itemData.data?.screen_shots.isNullOrEmpty()) View.VISIBLE else View.GONE
                itemView.tvFirstActivitiesItemScreenShots.text =
                        itemData.data?.screen_shots?.size.toString()
                itemView.tvFirstActivitiesItemMenu.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id) View.VISIBLE else View.GONE
                itemView.tvFirstActivitiesItemMenu.setOnClickListener {
                    val popup = PopupMenu(itemView.context, itemView.tvFirstActivitiesItemMenu)
                    popup.menuInflater.inflate(R.menu.menu_home_item_options, popup.menu)
                    popup.menu.removeItem(R.id.menuFindlay)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuDelete -> {
                                mListener.onDeleteClick(position, itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuFindlay -> {
                                mListener.onFindlayClick(itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuUpdatePrivacy -> {
                                mListener.onUpdatePrivacyClick(itemData.data?.id ?: 0)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                itemView.ibFirstActivitiesItemRate.setImageResource(
                        if (itemData.data?.is_fav == true) R.drawable.ic_star_post_on else R.drawable.ic_star_post_off
                )
                itemView.ibFirstActivitiesItemRate.setOnClickListener {
                    itemView.ibFirstActivitiesItemRate.setImageResource(
                            if (itemData.data?.is_fav == true) R.drawable.ic_star_post_off else R.drawable.ic_star_post_on
                    )
                    mListener.onFavoriteClick(itemData.data?.id ?: 0)
                }
                itemView.ibFirstActivitiesItemShare.setOnClickListener {
                    mListener.onEchoClick(itemData.data?.id ?: 0, itemData.data?.post_type.toString())
                }

                Glide.with(itemView.context).load(itemData.data?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_anonymous).into(itemView.ivFirstActivitiesItemUserImage)
                itemView.ivFirstActivitiesItemUserImage.setOnClickListener {
                    mListener.onUserClick(itemData.data?.user?.id ?: 0)
                }
                itemView.tvFirstActivitiesItemUserName.text =
                        itemData.data?.user?.fullname.toString()
                if (itemData.data?.activity_type == HomeRows.LOCATION.type_name) {
                    itemView.tvFirstActivitiesItemUserActivity.text =
                            itemView.context.getString(R.string.`in`)
                    val location = Gson().fromJson(
                            itemData.data.location.data.toString(),
                        MapsSearchData::class.java
                    )
                    if (location != null) {
                        itemView.tvFirstActivitiesItemUserActivation.text =
                            location.name ?: location.name ?: ""
                        itemView.tvFirstActivitiesItemUserActivation.setOnClickListener {
                            val gmmIntentUri = Uri.parse(
                                "geo:${location.geometry?.location?.lat.toString()},${location.geometry?.location?.lng.toString()}?q=" +
                                        Uri.encode(location.name)
                            )
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")
                            mapIntent.resolveActivity(this.mcontext.packageManager)?.let {
                                this.mcontext.startActivity(mapIntent)
                            }
                        }
                    }

                    itemView.ibFirstActivitiesItemType.setImageResource(R.drawable.ic_location_h)
                } else {
                    itemView.tvFirstActivitiesItemUserActivity.text =
                            itemView.context.getString(R.string.watching)
                    val watching = Gson().fromJson(
                            itemData.data?.watching?.data.toString(),
                            ActivityModel.WatchingModel::class.java
                    )
                    if (watching != null) {
                        itemView.tvFirstActivitiesItemUserActivation.text =
                            watching.title.toString()
                        itemView.tvFirstActivitiesItemUserActivation.setOnClickListener {
                            mListener.onMovieClick(watching.id!!, watching.title.toString())
                        }
                    }
                    itemView.ibFirstActivitiesItemType.setImageResource(R.drawable.ic_watching_h)
                }
                itemView.tvFirstActivitiesItemTime.text = itemData.data?.created_ago.toString()
                if (!itemData.data?.mentions.isNullOrEmpty()) {
                    itemView.tvFirstActivitiesItemMentionWith.visibility = View.VISIBLE
                    itemView.tvFirstActivitiesItemMentionName.visibility = View.VISIBLE
                    itemView.rvFirstActivitiesItemMentionsImages.visibility = View.VISIBLE
                    itemView.tvFirstActivitiesItemMentionName.text =
                            itemData.data?.mentions?.first()?.fullname.toString()
                    if (itemData.data?.mentions?.size!! > 1) {
                        itemView.tvFirstActivitiesItemMentionAnd.visibility = View.VISIBLE
                        itemView.tvFirstActivitiesItemMentionOthers.visibility = View.VISIBLE
                        itemView.tvFirstActivitiesItemMentionOthers.text =
                                String.format(
                                        "+%d %s", itemData.data.mentions.lastIndex,
                                        itemView.context.getString(R.string.others)
                                )
                    } else {
                        itemView.tvFirstActivitiesItemMentionAnd.visibility = View.GONE
                        itemView.tvFirstActivitiesItemMentionOthers.visibility = View.GONE
                    }
                    if (itemData.data.mentions.size > 5) {
                        val mentionsExtraCountUserModel = UserModel(username = "mentionsExtraCount", friends_count = itemData.data.mentions.size - 5)
                        itemView.rvFirstActivitiesItemMentionsImages.adapter =
                                HomeActivityMentionsAdapter(
                                    itemView.context,
                                    arrayListOf(
                                        itemData.data.mentions[0],
                                        itemData.data.mentions[1],
                                        itemData.data.mentions[2],
                                        itemData.data.mentions[3],
                                        itemData.data.mentions[4],
                                        mentionsExtraCountUserModel
                                    ),
                                    object: HomeActivityMentionsAdapter.onMentionClickListener {
                                        override fun onMentionsClick() {
                                            mListener.onMentionsClick(itemData.data.id)
                                        }
                                    }
                                )
                        itemView.ivFirstActivitiesItemMentionsOthers.visibility = View.VISIBLE
                        itemView.ivFirstActivitiesItemMentionsOthersFilter.visibility = View.VISIBLE
//                        itemView.tvFirstActivitiesItemMentionsOthers.visibility = View.VISIBLE
                        Glide.with(itemView.context)
                                .load(itemData.data.mentions[3].profile_image.toString())
                                .placeholder(R.color.white)
                                .into(itemView.ivFirstActivitiesItemMentionsOthers)
//                        itemView.tvFirstActivitiesItemMentionsOthers.text =
//                                String.format("+%d", itemData.data.mentions.lastIndex)
                    } else {
                        itemView.rvFirstActivitiesItemMentionsImages.adapter =
                                HomeActivityMentionsAdapter(itemView.context, itemData.data.mentions, object: HomeActivityMentionsAdapter.onMentionClickListener {
                                    override fun onMentionsClick() {
                                        mListener.onMentionsClick(itemData.data.id)
                                    }
                                })
                        itemView.ivFirstActivitiesItemMentionsOthers.visibility = View.GONE
                        itemView.ivFirstActivitiesItemMentionsOthersFilter.visibility = View.GONE
                        itemView.tvFirstActivitiesItemMentionsOthers.visibility = View.GONE
                    }
                } else {
                    itemView.tvFirstActivitiesItemMentionWith.visibility = View.GONE
                    itemView.tvFirstActivitiesItemMentionName.visibility = View.GONE
                    itemView.tvFirstActivitiesItemMentionAnd.visibility = View.GONE
                    itemView.tvFirstActivitiesItemMentionOthers.visibility = View.GONE
                    itemView.rvFirstActivitiesItemMentionsImages.visibility = View.GONE
                }
//                itemView.setOnClickListener { mListener.onPostClicked(position) }
            }
            HomeRows.POST_ECHO_NORMAL.type_num, HomeRows.ECHO_WITH_COMMENT.type_num -> {
                itemView.tvEchoPostsItemScreenShots.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id && !itemData.data?.screen_shots.isNullOrEmpty()) View.VISIBLE else View.GONE
                itemView.tvEchoPostsItemScreenShots.text =
                        itemData.data?.screen_shots?.size.toString()
                itemView.tvEchoPostsItemMenu.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id) View.VISIBLE else View.GONE
                itemView.tvEchoPostsItemMenu.setOnClickListener {
                    val popup = PopupMenu(itemView.context, itemView.tvEchoPostsItemMenu)
                    popup.menuInflater.inflate(R.menu.menu_home_item_options, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuDelete -> {
                                mListener.onDeleteClick(position, itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuFindlay -> {
                                mListener.onFindlayClick(itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuUpdatePrivacy -> {
                                mListener.onUpdatePrivacyClick(itemData.data?.id ?: 0)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                Glide.with(itemView.context).load(itemData.data?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_anonymous).into(itemView.ivEchoPostsItemUserImage)
                itemView.ivEchoPostsItemUserImage.setOnClickListener {
                    mListener.onUserClick(itemData.data?.user?.id ?: 0)
                }
                itemView.tvEchoPostsItemUserName.text = itemData.data?.user?.fullname.toString()
                itemView.tvEchoPostsItemTime.text = itemData.data?.created_ago.toString()
                itemView.tvEchoCommentText.text = itemData.data?.text.toString()
                itemView.ibEchoPostsItemRate.setImageResource(
                        if (itemData.data?.is_fav == true) R.drawable.ic_star_post_on else R.drawable.ic_star_post_off
                )
                itemView.ibEchoPostsItemRate.setOnClickListener {
                    itemView.ibEchoPostsItemRate.setImageResource(
                            if (itemData.data?.is_fav == true) R.drawable.ic_star_post_off else R.drawable.ic_star_post_on
                    )
                    mListener.onFavoriteClick(itemData.data?.id ?: 0)
                }
                itemView.ibEchoPostsItemShare.setOnClickListener {
                    mListener.onEchoClick(itemData.data?.id ?: 0, itemData.data?.post_type.toString())
                }
                Glide.with(itemView.context)
                        .load(itemData.data?.postable?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_emoji).into(itemView.ivEchoItemUserImage)
                itemView.tvEchoItemUserName.text =
                        itemData.data?.postable?.user?.fullname.toString()
                itemView.tvEchoItemText.text = itemData.data?.postable?.text.toString()
                itemView.tvEchoItemTime.text = itemData.data?.postable?.created_ago.toString()

                if (itemData.data?.likes_count != null && itemData.data.likes_count > 0) {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                when (itemData.data.like_type.toString()) {
                                    "laugh" -> R.drawable.ic_react_laugh_on
                                    "love" -> R.drawable.ic_react_love_on
                                    "dislike" -> R.drawable.ic_react_dislike_on
                                    else -> {
                                        itemData.data.like_type = ""
                                        R.drawable.ic_react_love_off
                                    }
                                }, 0, 0, 0
                        )
                    else itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, when (itemData.data.like_type.toString()) {
                        "laugh" -> R.drawable.ic_react_laugh_on
                        "love" -> R.drawable.ic_react_love_on
                        "dislike" -> R.drawable.ic_react_dislike_on
                        else -> {
                            itemData.data.like_type = ""
                            R.drawable.ic_react_love_off
                        }
                    }, 0
                    )

                    itemView.tvEchoPostsItemReactions.text = when {
                        itemData.data.likes_count < 1000 -> itemData.data.likes_count.toString()
                        itemData.data.likes_count in 1000..999999 ->
                            String.format("%.2fK", (itemData.data.likes_count / 1000).toFloat())
                        else -> String.format(
                                "%.2fM", (itemData.data.likes_count / 1000000).toFloat()
                        )
                    }
                } else {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_react_love_off, 0, 0, 0
                        )
                    else itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_react_love_off, 0
                    )
                    itemView.tvEchoPostsItemReactions.text = "0"
                }
                itemView.tvEchoPostsItemReactions.setOnTouchListener(
                        showReaction(itemView.context, itemData.data?.id ?: 0)
                )

                if (itemData.data?.comments_count != null)
                    itemView.tvEchoPostsItemComments.text = when {
                        itemData.data.comments_count < 1000 -> itemData.data.comments_count.toString()
                        itemData.data.comments_count in 1000..999999 ->
                            String.format("%.2fK", (itemData.data.comments_count / 1000).toFloat())
                        else -> String.format(
                                "%.2fM", (itemData.data.comments_count / 1000000).toFloat()
                        )
                    }
                else itemView.tvEchoPostsItemComments.text = "0"

                val mImages = ArrayList<ImageModel>()
                if (!itemData.data?.postable?.images.isNullOrEmpty())
                    for (image in itemData.data?.postable?.images!!) {
                        image.type = "image"
                        mImages.add(image)
                    }
                if (!itemData.data?.postable?.videos.isNullOrEmpty())
                    for (video in itemData.data?.postable?.videos!!) {
                        video.type = "video"
                        mImages.add(video)
                    }

                if (!mImages.isNullOrEmpty()) {
                    itemView.rvEchoItemImages.visibility = View.VISIBLE
                    itemView.rvEchoItemImages.adapter =
                            PostAttachmentsAdapter(mcontext, mImages, object : PostAttachmentsAdapter.OnAttachmentListener {
                                override fun onAttachmentListener() {
                                    mListener.onPhotosClick(mImages)
                                }
                            })
                } else itemView.rvEchoItemImages.visibility = View.GONE
                itemView.setOnClickListener { mListener.onPostClicked(position) }
            }
            HomeRows.POST_ECHO_ACTIVITY.type_num -> {
                itemView.tvActivitiesShareItemScreenShots.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id && !itemData.data?.screen_shots.isNullOrEmpty()) View.VISIBLE else View.GONE
                itemView.tvActivitiesShareItemScreenShots.text =
                        itemData.data?.screen_shots?.size.toString()
                itemView.tvActivitiesShareItemMenu.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id) View.VISIBLE else View.GONE
                itemView.tvActivitiesShareItemMenu.setOnClickListener {
                    val popup = PopupMenu(itemView.context, itemView.tvActivitiesShareItemMenu)
                    popup.menuInflater.inflate(R.menu.menu_home_item_options, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuDelete -> {
                                mListener.onDeleteClick(position, itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuFindlay -> {
                                mListener.onFindlayClick(itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuUpdatePrivacy -> {
                                mListener.onUpdatePrivacyClick(itemData.data?.id ?: 0)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                Glide.with(itemView.context).load(itemData.data?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_anonymous).into(itemView.ivActivitiesShareItemUserImage)
                itemView.ivActivitiesShareItemUserImage.setOnClickListener {
                    mListener.onUserClick(itemData.data?.user?.id ?: 0)
                }
                itemView.tvActivitiesShareItemUserName.text =
                        itemData.data?.user?.fullname.toString()
                itemView.tvActivitiesShareItemTime.text = itemData.data?.created_ago.toString()
                itemView.ibActivitiesShareItemRate.setImageResource(
                        if (itemData.data?.is_fav == true) R.drawable.ic_star_post_on else R.drawable.ic_star_post_off
                )
                itemView.ibActivitiesShareItemRate.setOnClickListener {
                    itemView.ibActivitiesShareItemRate.setImageResource(
                            if (itemData.data?.is_fav == true) R.drawable.ic_star_post_off else R.drawable.ic_star_post_on
                    )
                    mListener.onFavoriteClick(itemData.data?.id ?: 0)
                }

                itemView.ibActivitiesShareItemShare.setOnClickListener {
                    mListener.onEchoClick(itemData.data?.id ?: 0, itemData.data?.post_type.toString())
                }

                Glide.with(itemView.context)
                        .load(itemData.data?.postable?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_emoji).into(itemView.ivActivitiesItemUserImage)
                itemView.tvActivitiesItemUserName.text =
                        itemData.data?.postable?.user?.fullname.toString()
                if (itemData.data?.postable?.activity_type == HomeRows.LOCATION.type_name) {
                    itemView.tvActivitiesItemUserActivity.text =
                            itemView.context.getString(R.string.`in`)
                    val location = Gson().fromJson(
                            itemData.data.postable.location.data.toString(),
                            ActivityModel.LocationModel::class.java
                    )
                    if (location != null)
                        itemView.tvActivitiesItemUserActivation.text = location.address.toString()
                    itemView.ibActivitiesItemType.setImageResource(R.drawable.ic_location_h)
                } else {
                    itemView.tvActivitiesItemUserActivity.text =
                            itemView.context.getString(R.string.watching)
                    val watching = Gson().fromJson(
                            itemData.data?.postable?.watching?.data.toString(),
                            ActivityModel.WatchingModel::class.java
                    )
                    if (watching != null)
                        itemView.tvActivitiesItemUserActivation.text = watching.title.toString()
                    itemView.ibActivitiesItemType.setImageResource(R.drawable.ic_watching_h)
                }
                itemView.tvActivitiesItemTime.text = itemData.data?.postable?.created_ago.toString()
                if (!itemData.data?.postable?.mentions.isNullOrEmpty()) {
                    itemView.tvActivitiesItemMentionWith.visibility = View.VISIBLE
                    itemView.tvActivitiesItemMentionName.visibility = View.VISIBLE
                    itemView.tvActivitiesItemMentionName.text =
                            itemData.data?.postable?.mentions?.first()?.fullname.toString()
                    if (itemData.data?.postable?.mentions?.size!! > 1) {
                        itemView.tvActivitiesItemMentionAnd.visibility = View.VISIBLE
                        itemView.tvActivitiesItemMentionOthers.visibility = View.VISIBLE
                        itemView.tvActivitiesItemMentionOthers.text =
                                String.format(
                                        "+%d %s", itemData.data.postable.mentions.lastIndex,
                                        itemView.context.getString(R.string.others)
                                )
                    } else {
                        itemView.tvActivitiesItemMentionAnd.visibility = View.GONE
                        itemView.tvActivitiesItemMentionOthers.visibility = View.GONE
                    }
                } else {
                    itemView.tvActivitiesItemMentionWith.visibility = View.GONE
                    itemView.tvActivitiesItemMentionName.visibility = View.GONE
                    itemView.tvActivitiesItemMentionAnd.visibility = View.GONE
                    itemView.tvActivitiesItemMentionOthers.visibility = View.GONE
                }

                if (itemData.data?.likes_count != null && itemData.data.likes_count > 0) {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvActivitiesShareItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                when (itemData.data.like_type.toString()) {
                                    "laugh" -> R.drawable.ic_react_laugh_on
                                    "love" -> R.drawable.ic_react_love_on
                                    "dislike" -> R.drawable.ic_react_dislike_on
                                    else -> {
                                        itemData.data.like_type = ""
                                        R.drawable.ic_react_love_off
                                    }
                                }, 0, 0, 0
                        )
                    else itemView.tvActivitiesShareItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, when (itemData.data.like_type.toString()) {
                        "laugh" -> R.drawable.ic_react_laugh_on
                        "love" -> R.drawable.ic_react_love_on
                        "dislike" -> R.drawable.ic_react_dislike_on
                        else -> {
                            itemData.data.like_type = ""
                            R.drawable.ic_react_love_off
                        }
                    }, 0
                    )

                    itemView.tvActivitiesShareItemReactions.text = when {
                        itemData.data.likes_count < 1000 -> itemData.data.likes_count.toString()
                        itemData.data.likes_count in 1000..999999 ->
                            String.format("%.2fK", (itemData.data.likes_count / 1000).toFloat())
                        else ->
                            String.format("%.2fM", (itemData.data.likes_count / 1000000).toFloat())
                    }
                } else {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvActivitiesShareItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_react_love_off, 0, 0, 0
                        )
                    else itemView.tvActivitiesShareItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_react_love_off, 0
                    )
                    itemView.tvActivitiesShareItemReactions.text = "0"
                }
                itemView.tvActivitiesShareItemReactions.setOnTouchListener(
                        showReaction(itemView.context, itemData.data?.id ?: 0)
                )

                if (itemData.data?.comments_count != null)
                    itemView.tvActivitiesShareItemComments.text = when {
                        itemData.data.comments_count < 1000 -> itemData.data.comments_count.toString()
                        itemData.data.comments_count in 1000..999999 ->
                            String.format("%.2fK", (itemData.data.comments_count / 1000).toFloat())
                        else ->
                            String.format("%.2fM", (itemData.data.comments_count / 1000000).toFloat())
                    }
                else itemView.tvActivitiesShareItemComments.text = "0"
                itemView.setOnClickListener { mListener.onPostClicked(position) }
            }
            HomeRows.POST_SECRET_MESSAGE.type_num -> {
                itemView.tvSecretMessagesItemScreenShots.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id && !itemData.data?.screen_shots.isNullOrEmpty()) View.VISIBLE else View.GONE
                itemView.tvSecretMessagesItemScreenShots.text =
                        itemData.data?.screen_shots?.size.toString()
                itemView.tvSecretMessagesItemMenu.visibility =
                        if (itemData.data?.user?.id == mSharedPrefManager.userData.id) View.VISIBLE else View.GONE
                itemView.tvSecretMessagesItemMenu.setOnClickListener {
                    val popup = PopupMenu(itemView.context, itemView.tvSecretMessagesItemMenu)
                    popup.menuInflater.inflate(R.menu.menu_home_item_options, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuDelete -> {
                                mListener.onDeleteClick(position, itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuFindlay -> {
                                mListener.onFindlayClick(itemData.data?.id ?: 0)
                                true
                            }
                            R.id.menuUpdatePrivacy -> {
                                mListener.onUpdatePrivacyClick(itemData.data?.id ?: 0)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                Glide.with(itemView.context).load(itemData.data?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_anonymous).into(itemView.ivSecretMessagesItemUserImage)
                itemView.ivSecretMessagesItemUserImage.setOnClickListener {
                    mListener.onUserClick(itemData.data?.user?.id ?: 0)
                }
                itemView.tvSecretMessagesItemUserName.text = itemData.data?.user?.fullname.toString()
                itemView.tvSecretMessagesItemTime.text = itemData.data?.created_ago.toString()

                val mImages = ArrayList<ImageModel>()
                if (!itemData.data?.images.isNullOrEmpty())
                    for (image in itemData.data?.images!!) {
                        image.type = "image"
                        mImages.add(image)
                    }
                if (!itemData.data?.videos.isNullOrEmpty())
                    for (video in itemData.data?.videos!!) {
                        video.type = "video"
                        mImages.add(video)
                    }

                if (!mImages.isNullOrEmpty()) {
                    itemView.rvSecretMessagesEchoItemImages.visibility = View.VISIBLE
                    itemView.rvSecretMessagesEchoItemImages.adapter =
                            PostAttachmentsAdapter(mcontext, mImages, object : PostAttachmentsAdapter.OnAttachmentListener {
                                override fun onAttachmentListener() {
                                    mListener.onPhotosClick(mImages)
                                }
                            })
                } else itemView.rvSecretMessagesEchoItemImages.visibility = View.GONE

                itemView.tvSecretMessagesEchoItemTime.text = itemData.data?.postable?.ago_time.toString()
                itemView.tvSecretMessagesEchoItemText.text = itemData.data?.postable?.message.toString()
                itemView.setOnClickListener { mListener.onPostClicked(position) }
            }
            HomeRows.ECHO_WITHOUT_COMMENT.type_num -> {
                itemView.tvEchoedUserName.text = itemData.data?.user?.fullname + " said"
                itemView.tvEchoDate.text = itemData.data?.created_ago
                val echoPost = itemData.data?.postable
                itemView.tvEchoPostsItemScreenShots.visibility =
                        if (echoPost?.user?.id == mSharedPrefManager.userData.id && !echoPost?.screen_shots.isNullOrEmpty()) View.VISIBLE else View.GONE
                itemView.tvEchoPostsItemScreenShots.text =
                        echoPost?.screen_shots?.size.toString()
                itemView.tvEchoPostsItemMenu.visibility =
                        if (echoPost?.user?.id == mSharedPrefManager.userData.id) View.VISIBLE else View.GONE
                itemView.tvEchoPostsItemMenu.setOnClickListener {
                    val popup = PopupMenu(itemView.context, itemView.tvEchoPostsItemMenu)
                    popup.menuInflater.inflate(R.menu.menu_home_item_options, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menuDelete -> {
                                mListener.onDeleteClick(position, echoPost?.id ?: 0)
                                true
                            }
                            R.id.menuFindlay -> {
                                mListener.onFindlayClick(echoPost?.id ?: 0)
                                true
                            }
                            R.id.menuUpdatePrivacy -> {
                                mListener.onUpdatePrivacyClick(echoPost?.id ?: 0)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                Glide.with(itemView.context).load(echoPost?.user?.profile_image.toString())
                        .placeholder(R.drawable.ic_anonymous).into(itemView.ivEchoPostsItemUserImage)
                itemView.ivEchoPostsItemUserImage.setOnClickListener {
                    mListener.onUserClick(echoPost?.user?.id ?: 0)
                }
                itemView.tvEchoPostsItemUserName.text = echoPost?.user?.fullname.toString()
                itemView.tvEchoPostsItemCommentText.text = echoPost?.text.toString()
                itemView.tvEchoPostsItemTime.text = echoPost?.created_ago.toString()
                itemView.ibEchoPostsItemRate.setImageResource(
                        if (echoPost?.is_fav == true) R.drawable.ic_star_post_on else R.drawable.ic_star_post_off
                )
                itemView.ibEchoPostsItemRate.setOnClickListener {
                    itemView.ibEchoPostsItemRate.setImageResource(
                            if (echoPost?.is_fav == true) R.drawable.ic_star_post_off else R.drawable.ic_star_post_on
                    )
                    mListener.onFavoriteClick(echoPost?.id ?: 0)
                }

                itemView.ibEchoPostsItemShare.setOnClickListener {
                    mListener.onEchoClick(echoPost?.id ?: 0, echoPost?.post_type.toString())
                }

                if (echoPost?.likes_count != null && echoPost.likes_count > 0) {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                when (echoPost.like_type.toString()) {
                                    "laugh" -> R.drawable.ic_react_laugh_on
                                    "love" -> R.drawable.ic_react_love_on
                                    "dislike" -> R.drawable.ic_react_dislike_on
                                    else -> {
                                        echoPost.like_type = ""
                                        R.drawable.ic_react_love_off
                                    }
                                }, 0, 0, 0
                        )
                    else itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, when (echoPost.like_type.toString()) {
                        "laugh" -> R.drawable.ic_react_laugh_on
                        "love" -> R.drawable.ic_react_love_on
                        "dislike" -> R.drawable.ic_react_dislike_on
                        else -> {
                            echoPost.like_type = ""
                            R.drawable.ic_react_love_off
                        }
                    }, 0
                    )

                    itemView.tvEchoPostsItemReactions.text = when {
                        echoPost.likes_count < 1000 -> echoPost.likes_count.toString()
                        echoPost.likes_count in 1000..999999 ->
                            String.format("%.2fK", (echoPost.likes_count / 1000).toFloat())
                        else -> String.format(
                                "%.2fM", (echoPost.likes_count / 1000000).toFloat()
                        )
                    }
                } else {
                    if (mSharedPrefManager.appLanguage == "en")
                        itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_react_love_off, 0, 0, 0
                        )
                    else itemView.tvEchoPostsItemReactions.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_react_love_off, 0
                    )
                    itemView.tvEchoPostsItemReactions.text = "0"
                }
                itemView.tvEchoPostsItemReactions.setOnTouchListener(
                        showReaction(itemView.tvEchoPostsItemReactions.context, echoPost?.id ?: 0)
                )

                if (echoPost?.comments_count != null)
                    itemView.tvEchoPostsItemComments.text = when {
                        echoPost.comments_count < 1000 -> echoPost.comments_count.toString()
                        echoPost.comments_count in 1000..999999 ->
                            String.format("%.2fK", (echoPost.comments_count / 1000).toFloat())
                        else -> String.format(
                                "%.2fM", (echoPost.comments_count / 1000000).toFloat()
                        )
                    }
                else itemView.tvEchoPostsItemComments.text = "0"

                val mImages = ArrayList<ImageModel>()
                if (!echoPost?.images.isNullOrEmpty())
                    for (image in echoPost?.images!!) {
                        image.type = "image"
                        mImages.add(image)
                    }
                if (!echoPost?.videos.isNullOrEmpty())
                    for (video in echoPost?.videos!!) {
                        video.type = "video"
                        mImages.add(video)
                    }

                if (!mImages.isNullOrEmpty()) {
                    itemView.rvEchoPostsItemImages.visibility = View.VISIBLE
                    itemView.rvEchoPostsItemImages.adapter =
                            PostAttachmentsAdapter(mcontext, mImages, object : PostAttachmentsAdapter.OnAttachmentListener {
                                override fun onAttachmentListener() {
                                    mListener.onPhotosClick(mImages)
                                }
                            })
                } else itemView.rvEchoPostsItemImages.visibility = View.GONE
                itemView.setOnClickListener { mListener.onPostClicked(position) }
            }
            else -> {
                //Log.e("FFFFF")
                Glide.with(itemView.context).load(itemData.data?.sponsor?.logo.toString())
                        .placeholder(R.drawable.ic_emoji).into(itemView.ivSponsorsItemUserImage)
                itemView.tvSponsorsItemUserName.text = itemData.data?.sponsor?.name.toString()
                itemView.tvSponsorsItemDescription.text = itemData.data?.desc.toString()
                Glide.with(itemView.context).load(itemData.data?.file.toString())
                        .placeholder(R.color.black).into(itemView.ivSponsorsItemImage)
                itemView.tvSponsorsItemVisitAdv.setOnClickListener {
                    val uri = Uri.parse(itemData.data?.url.toString())
                    itemView.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }
        }
        Log.e("LIKE_TYPE", "LikeType: ${itemData.data?.like_type}")



    }

    override fun getItemViewType(position: Int) = run {
        if (isLoaderVisible && position == data.lastIndex) HomeRows.LOADING.type_num
        else {
            val type = data[position].type.toString()
            val postType = data[position].data?.post_type.toString()
            var activityType = data[position].data?.activity_type.toString()

            when (type) {
                HomeRows.POST.type_name -> {
                    when (postType) {
                        HomeRows.FIRST.type_name -> when (activityType) {
                            HomeRows.NORMAL.type_name -> HomeRows.POST_FIRST_NORMAL.type_num
                            else -> HomeRows.POST_FIRST_ACTIVITY.type_num
                        }
                        HomeRows.ECHO_WITHOUT_COMMENT.type_name -> HomeRows.ECHO_WITHOUT_COMMENT.type_num
                        HomeRows.ECHO_WITH_COMMENT.type_name -> HomeRows.ECHO_WITH_COMMENT.type_num
                        else -> {
                            if (data[position].data?.postable?.activity_type != null) {
                                activityType = data[position].data?.postable?.activity_type.toString()
                                when (activityType) {
                                    HomeRows.NORMAL.type_name -> HomeRows.POST_ECHO_NORMAL.type_num
                                    else -> HomeRows.POST_ECHO_ACTIVITY.type_num
                                }
                            } else HomeRows.POST_SECRET_MESSAGE.type_num
                        }
                    }
                }
                else -> HomeRows.SPONSOR.type_num
            }
        }
    }

    override fun getItemCount() = data.size

    fun getItems() = data

    fun getItem(position: Int) = data[position]

    fun addItems(dataList: ArrayList<HomeModel>) {
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    fun removeItem(item: HomeModel) {
        val position = data.indexOf(item)
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addLoading() {
        isLoaderVisible = true
        data.add(HomeModel())
        notifyItemInserted(data.lastIndex)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = data.lastIndex
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    interface HomeItemClickListeners {
        fun onPostClicked(position: Int)
        fun onDeleteClick(position: Int, postId: Int)
        fun onFindlayClick(postId: Int)
        fun onUpdatePrivacyClick(postId: Int)
        fun onFavoriteClick(postId: Int)
        fun onReactClick(postId: Int, reactionPosition: Int)
        fun onUserClick(userId: Int)
        fun onPhotosClick(list: ArrayList<ImageModel>)
        fun onEchoClick(postId: Int, postType: String)
        fun onMovieClick(movieId: Int, movieTitle: String)
        fun onMentionsClick(postId: Int)
    }

    private fun showReaction(context: Context, postId: Int): ReactionPopup {
        val config = reactionConfig(context) {
            reactions {
//                reaction { R.drawable.ic_react_love_off scale ImageView.ScaleType.FIT_XY }
                reaction { R.drawable.ic_react_love_on scale ImageView.ScaleType.FIT_XY }
                reaction { R.drawable.ic_react_laugh_on scale ImageView.ScaleType.FIT_XY }
                reaction { R.drawable.ic_react_dislike_on scale ImageView.ScaleType.FIT_XY }
            }
            popupGravity = PopupGravity.PARENT_RIGHT
            popupMargin = context.resources.getDimensionPixelSize(R.dimen.popup_horizontal_margin)
            verticalMargin = context.resources.getDimensionPixelSize(R.dimen.popup_vertical_margin)
        }

        return ReactionPopup(context, config) { position ->
            true.also {
                mListener.onReactClick(postId, position)
            }
        }
    }

    class HomeViewHolder(itemView: View) : ParentRecyclerViewHolder(itemView)
}