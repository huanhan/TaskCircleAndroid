package xin.lrvik.taskcicleandroid.ui.adapter

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseViewHolder
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 *
 */
class RvAddTaskStepAdapter(data: List<TaskStep>) : BaseItemDraggableAdapter<TaskStep,
        BaseViewHolder>(R.layout.item_add_task_step, data) {

    override fun convert(helper: BaseViewHolder, item: TaskStep) {
        helper.setText(R.id.mTvtitle, item.title).setText(R.id.mTvContent, item.context)
    }
}