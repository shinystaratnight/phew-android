package com.app.phew.app


class Constant {


    object UserActiveMode {

        var DEACTIVE = "deactive"

        var ACTIVE = "active"
    }

    object RequestCode {

        val GPS_ENABLING = 300

        val GET_LOCATION = 500

        val CHOOSE_CATEGORIES_RESUTL = 3

    }

    object RequestPermission {

        val REQUEST_GPS_LOCATION = 800

        val REQUEST_IMAGES = 400

        val REQUEST_CALL = 300

        val STORAGE = 1

        var GETLOCATION = 500
        val PHOTO_CHOOSE = 3
        val GPSEnabling = 300
        val Call = 100
        val Take_PICTURE = 9


    }

    object LocationConstant {

        var LAT = "lat"

        var LNG = "lng"

        var LOCATION = "location"
    }

    object InfinitScroll {

        val ITEM = 0

        val LOADING = 1

        val SELF_TEXT = 100

        val OTHER_TEXT = 101

        val SELF_IMAGE = 103

        val OTHER_IMAGE = 104
    }


    object ResultData {

        val CHOOSE_COUNTRY = "choose_country"

        val CHOOSE_CITY = "choose_city"

        val CHOOSE_CATEGORIES = "choose_categories"

        val CHOOSE_CATEGORIES_ID = "choose_categories_id"
    }

    object NotificationType {

        val ChatSound = 1

        val NotificationSound = 0
    }

    object BundleData {

        val FORGET_PASS_MODEL = "forget_pass"

        val FOOD_MODEL = "food_model"
        val FOOD_ID = "food_id"

        val CATEGORY_ID = "category_id"

        val CATEGORY = "category"

        val FAMILY_MODEL = "family_model"

        val FAMILY_ID = "familey_id"

        val REGISTER_MODEL = "register_model"

        var ORDER = "order_details"
    }

    object
    APIsKey {
        const val OS_TYPE = "android"
    }

    object SharedPrefKey {
        const val SHARED_PREF_NAME = "phew_shared_pref"
        const val MY_LAT = "phew_user_lat"

        const val MY_LNG = "phew_user_lng"

        val IS_nOTIFICATION = "phew_NOTIFICATION"
        const val App_LANGUAGE = "phew_language"

        const val LOGIN_STATUS = "phew_login_status"

        const val GUEST_STATUS = "phew_guest_status"

        const val FIRST_TIME = "phew_guest_first_time"

        const val DEVICE_TOKEN = "phew_device_token_data"

        const val APP_LANGUAGE = "phew_app_language"
        const val MOBILE_TYPE = "phew_mobile_type"

        const val TOKEN = "phew_token_data"

        const val USER = "phew_user_data"

        const val NOTIFICATIONS_COUNT = "phew_notifications_count"

        const val LOCATION = "phew_location"

        const val VIBRATION = "phew_vibration"

        const val RING_TONE = "phew_ring_tone"
    }


    object OrderStatus {

        val NO_ORDERS = "noOrder"

        val CREATED_ORDER = "orderCreated"

        val ACCEPTED_TRIP = "accepted"

        val TRIP_STARTED = "tripStarted"

        val ORDER_COST = "orderCost"

        var RATE_DRIVER = "rateDriver"
    }
}
