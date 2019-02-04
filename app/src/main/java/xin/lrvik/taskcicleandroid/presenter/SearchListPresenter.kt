package xin.lrvik.taskcicleandroid.presenter

import xin.lrvik.taskcicleandroid.baselibrary.ext.execute
import xin.lrvik.taskcicleandroid.baselibrary.presenter.BasePresenter
import xin.lrvik.taskcicleandroid.presenter.view.SearchListView
import xin.lrvik.taskcicleandroid.service.TaskService
import javax.inject.Inject

class SearchListPresenter @Inject constructor() : BasePresenter<SearchListView>() {
    @Inject
    lateinit var taskService: TaskService

    fun search(key: String,
                       page: Int,
                       size: Int) {
        if (!checkNetWork()) {
            return
        }

        taskService.search(key, page, size).execute(lifecycleProvider, mView, false) {
            mView.onTaskListResult(it)
        }
    }
}
