package xin.lrvik.taskcicleandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_my.*
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.fragment.BaseFragment
import xin.lrvik.taskcicleandroid.common.UserInfo
import xin.lrvik.taskcicleandroid.ui.activity.LoginActivity
import xin.lrvik.taskcicleandroid.ui.activity.WalletActivity

class MyFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvMoney.text = "${UserInfo.money}元"
        mTvStart.text = "${UserInfo.start}"
        mTvEvaluate.text = "${UserInfo.comment}条"

        mTvLoginOrRegist.onClick {
            startActivity<LoginActivity>()
        }

        mRlWallet.onClick {
            startActivity<WalletActivity>()
        }
        mBtExitLogin.onClick {
            UserInfo.userId = if (UserInfo.userId == 6L) 13 else 6
        }
    }
}
