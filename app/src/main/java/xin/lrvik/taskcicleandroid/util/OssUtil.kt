package xin.lrvik.taskcicleandroid.util

import android.util.Log

import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSS
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.OSSLog
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseApplication
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant
import xin.lrvik.taskcicleandroid.baselibrary.common.BaseConstant.Companion.OSS_BASE_URL
import xin.lrvik.taskcicleandroid.baselibrary.data.net.RetrofitFactory
import xin.lrvik.taskcicleandroid.baselibrary.ext.md5Encode
import xin.lrvik.taskcicleandroid.baselibrary.utils.DateUtils
import xin.lrvik.taskcicleandroid.data.api.HomeApi

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/30.
 */
class OssUtil {

    companion object {
        val instance: OssUtil by lazy {
            OssUtil()
        }
    }

    var oss: OSS

    constructor() {
        var credentialProvider: OSSCredentialProvider = object : OSSFederationCredentialProvider() {
            override fun getFederationToken(): OSSFederationToken {
                var ossToken = RetrofitFactory.instance.create(HomeApi::class.java).osstoken().execute().body()
                return OSSFederationToken(ossToken?.accessKeyId,
                        ossToken?.accessKeySecret,
                        ossToken?.securityToken,
                        ossToken?.expiration)
            }
        }
        OSSLog.enableLog()
        oss = OSSClient(BaseApplication.context, OSS_BASE_URL, credentialProvider)
    }

    fun putFile(path: String, success: (url: String) -> Unit, faile: () -> Unit) {
        //计算图片资源路径
        var url = md5Encode(path + DateUtils.curTime)
        // 构造上传请求。
        val put = PutObjectRequest("taskcircle", url, path)
        // 异步上传时可以设置进度回调。
        put.progressCallback = OSSProgressCallback { request, currentSize, totalSize ->
            Log.d("PutObject", "currentSize: $currentSize totalSize: $totalSize")
        }
        val task = oss.asyncPutObject(put,
                object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
                    override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                        Log.d("PutObject", "UploadSuccess")
                        Log.d("ETag", result.eTag)
                        Log.d("RequestId", result.requestId)
                        Log.d("serverCallback", "serverCallback" + result.serverCallbackReturnBody)
                        success("${OSS_BASE_URL}/${url}")
                    }

                    override fun onFailure(request: PutObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                        // 请求异常。
                        clientExcepion?.printStackTrace()
                        if (serviceException != null) {
                            // 服务异常。
                            Log.e("ErrorCode", serviceException.errorCode)
                            Log.e("RequestId", serviceException.requestId)
                            Log.e("HostId", serviceException.hostId)
                            Log.e("RawMessage", serviceException.rawMessage)
                            faile()
                        }
                    }
                })
        Log.d("serverCallback", "serverCallback" + task.result.serverCallbackReturnBody)
    }
}
