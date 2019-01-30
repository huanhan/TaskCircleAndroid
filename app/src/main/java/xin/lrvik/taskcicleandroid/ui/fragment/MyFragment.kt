package xin.lrvik.taskcicleandroid.ui.fragment

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leo.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.internal.utils.Platform
import com.zhihu.matisse.sample.GifSizeFilter
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
