package com.app.phew.ui.signing.register

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.phew.R
import com.app.phew.base.BaseFragment
import com.app.phew.listners.OnDeleteImageClickListener
import com.app.phew.listners.OnItemClickListener
import com.app.phew.models.BaseResponse
import com.app.phew.models.ListModel
import com.app.phew.models.cities.CitiesResponse
import com.app.phew.models.countries.CountriesResponse
import com.app.phew.ui.signing.forgetPassword.verificationCode.VerificationCodeActivity
import com.app.phew.utils.CommonUtil
import com.app.phew.utils.SBManager
import com.app.phew.utils.views.ListDialog
import com.esafirm.imagepicker.features.ImagePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.et_email
import kotlinx.android.synthetic.main.fragment_register.et_password
import kotlinx.android.synthetic.main.fragment_register.rel


class RegisterFragment : BaseFragment(), OnDeleteImageClickListener, RegisterContract.View,
    OnItemClickListener {
    override val layoutResource: Int
        get() = R.layout.fragment_register
    override val isRecycle: Boolean
        get() = true


    lateinit var imagesAdapter: RegisterImageAdapter
    lateinit var mPresenter: RegisterPresenter
    lateinit var models: ArrayList<ListModel>
    lateinit var mDialog: ListDialog
    private var countryId = -1
    private var cityId = -1
    private var flag = ""
    var images: ArrayList<String> = ArrayList()
    override fun initializeComponents(view: View) {
        imagesAdapter = RegisterImageAdapter(mContext, images, R.layout.item_image, this)
        rvImages.adapter = imagesAdapter
        imvUserImages.setOnClickListener {
            if (images.size==3)
               SBManager.displayError(mContext,getString(R.string.you_are_not_allowed_to_add_more_than_picture))
            else pickImages()
        }
        tvProfileImages.setOnClickListener {
            if (images.size==3)
                SBManager.displayError(mContext,getString(R.string.you_are_not_allowed_to_add_more_than_picture))
            else pickImages()
        }
        mPresenter = RegisterPresenter(this)
        models = ArrayList()
        etCountry.setOnClickListener {
            flag = "country"
            mPresenter.getCountries()
        }
        etCity.setOnClickListener {
            flag = "city"
            if (countryId == 0) {
                SBManager.displayError(
                    mContext,
                    getString(R.string.please_select_your_country_first)
                )
            } else {
                mPresenter.getCities(countryId)
            }
        }
        btn_register.setOnClickListener {
            mPresenter.register(
                et_name.text.toString(),
                et_phone.text.toString(),
                et_email.text.toString(),
                et_password.text.toString(),
                et_confirm_password.text.toString(),
                images,
                countryId,cityId,mSharedPrefManager.mobileType,CommonUtil.getDeviceToken(mContext)
            )
        }

    }

    private fun pickImages() {
        ImagePicker.create(this)
            .toolbarFolderTitle("Folder") // folder selection title
            .toolbarImageTitle("Tap to select") // image selection title
            .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
            .includeVideo(false) // Show video on image picker
            .single()// multi mode (default mode)
            .limit(1) // max images can be selected (99 by default)
            .showCamera(true) // show camera or not (true by default)
            .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
            .enableLog(true) // disabling log
            .start(); // start image picker activity with request code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data).path
            images.add(image)
            imagesAdapter.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDeleteImageClick( position: Int) {
        images.removeAt(position)
        imagesAdapter.notifyDataSetChanged()
    }

    override fun showFieldError(field: String) {
        when (field) {
            "fullname" -> {
                SBManager.displayError(mContext, getString(R.string.please_enter_name))
            }
            "email" -> {
                SBManager.displayError(mContext, getString(R.string.please_enter_valid_email))
            }
            "notEmail" -> {
                SBManager.displayError(mContext, getString(R.string.please_enter_valid_email))
            }
            "password" -> {

                SBManager.displayError(mContext, getString(R.string.please_enter_valid_password))
            }
            "notMatch" -> {
                SBManager.displayError(mContext, getString(R.string.passwords_not_match))
            }
            "mobile" -> {
                SBManager.displayError(
                    mContext,
                    getString(R.string.please_enter_valid_mobile_numbe)
                )
            }
            "cityId" -> {
                SBManager.displayError(
                    mContext,
                    getString(R.string.please_select_city)
                )
            }
            "country" -> {
                SBManager.displayError(
                    mContext,
                    getString(R.string.please_select_your_country)
                )
            }
        }
    }

    override fun onGetCountriesSuccess(response: CountriesResponse) {
        val data: ArrayList<ListModel> = ArrayList()
        for (i in response.data.indices) {
            data.add(
                ListModel(
                    response.data[i].id,
                    response.data[i].name
                )
            )
        }
        models.clear()
        models.addAll(data)
        mDialog = ListDialog(mContext, this, models, getString(R.string.please_select_your_country))
        mDialog.show()
    }

    override fun onGetCitiesSuccess(response: CitiesResponse) {
        val data: ArrayList<ListModel> = ArrayList()
        for (i in response.data.indices) {
            data.add(
                ListModel(
                    response.data[i].id,
                    response.data[i].name
                )
            )
        }
        models.clear()
        models.addAll(data)
        mDialog = ListDialog(mContext, this, models, getString(R.string.please_select_city))
        mDialog.show()
    }

    override fun onResponseSuccess(data: BaseResponse) {
        CommonUtil.makeToast(mContext,data.message.toString()?:"")
        VerificationCodeActivity.startActivity(mContext as AppCompatActivity,et_phone.text.toString(),"active")
    }

    override fun onResponseError(errorBody: String) {
        if (activity != null && isAdded) {
            SBManager.displayError(
                mContext, Gson().fromJson(errorBody, BaseResponse::class.java).message!!,
            )
        }
    }

    override fun onResponseFailure(t: Throwable) {
        if (activity != null && isAdded) {
            CommonUtil.handleException(mContext, t)
        }
    }

    override fun showProgress() {
        if (activity != null && isAdded) {
            rel.visibility = View.VISIBLE
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    override fun hideProgress() {
        if (activity != null && isAdded) {
            rel.visibility = View.GONE
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onItemClick(view: View, position: Int) {
        if (flag == "country") {
            mDialog.dismiss()
            countryId = models[position].id!!
            etCountry.setText(models[position].name)
        } else {
            mDialog.dismiss()
            cityId = models[position].id!!
            etCity.setText(models[position].name)
        }
    }


}