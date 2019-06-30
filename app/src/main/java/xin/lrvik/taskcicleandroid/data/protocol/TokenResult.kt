package xin.lrvik.taskcicleandroid.data.protocol

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/2/10.
 *
 */
data class TokenResult(var access_token: String,
                       var token_type: String,
                       var refresh_token: String,
                       var expires_in: Long,
                       var scope: String,
                       var userId: String,
                       var jti: String)