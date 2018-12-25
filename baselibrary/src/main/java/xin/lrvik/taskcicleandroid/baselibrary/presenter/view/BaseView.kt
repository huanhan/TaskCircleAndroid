package xin.lrvik.taskcicleandroid.baselibrary.presenter.view


interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
}