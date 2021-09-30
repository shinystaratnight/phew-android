package com.app.phew.ui.home

/**
 * Created by Mohamed Balsha on 2/4/2021.
 */

enum class HomeRows(val type_name: String, val type_num: Int) {
    LOADING("loading", 0),
    POST("post", 1),
    FIRST("first", 2),
    ECHO_WITH_COMMENT("echo_with_comment", 3),
    ECHO_WITHOUT_COMMENT("echo_without_comment", 4),
    NORMAL("normal", 5),
    LOCATION("location", 6),
    WATCHING("watching", 7),
    SECRET_MESSAGE("secret_message", 8),

    SPONSOR("sponsor", 9),
    POST_FIRST_NORMAL("post_first_normal", 10),
    POST_FIRST_ACTIVITY("post_first_activity", 11),
    POST_ECHO_NORMAL("post_echo_normal", 12),
    POST_ECHO_ACTIVITY("post_echo_activity", 13),
    POST_SECRET_MESSAGE("post_secret_message", 14)
}