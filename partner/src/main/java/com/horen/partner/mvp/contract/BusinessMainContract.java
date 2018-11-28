package com.horen.partner.mvp.contract;

import com.horen.base.mvp.BaseModel;
import com.horen.base.mvp.BasePresenter;
import com.horen.base.mvp.BaseView;

/**
 * Author:Steven
 * Time:2018/8/3 9:26
 * Description:This isBusinessMainContract
 */
public interface BusinessMainContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

    }
}
