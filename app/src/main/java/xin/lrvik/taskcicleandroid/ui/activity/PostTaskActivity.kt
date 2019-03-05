package xin.lrvik.taskcicleandroid.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.InputFilter
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.leo.matisse.Glide4Engine
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_post_task.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import xin.lrvik.taskcicleandroid.R
import xin.lrvik.taskcicleandroid.baselibrary.ext.onClick
import xin.lrvik.taskcicleandroid.baselibrary.ui.activity.BaseMvpActivity
import xin.lrvik.taskcicleandroid.data.protocol.TaskClass
import xin.lrvik.taskcicleandroid.data.protocol.TaskDetail
import xin.lrvik.taskcicleandroid.data.protocol.TaskStep
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState
import xin.lrvik.taskcicleandroid.injection.component.DaggerTaskCircleComponent
import xin.lrvik.taskcicleandroid.presenter.PostTaskPresenter
import xin.lrvik.taskcicleandroid.presenter.view.PostTaskView
import xin.lrvik.taskcicleandroid.ui.adapter.RvModifyTaskStepAdapter
import xin.lrvik.taskcicleandroid.ui.dialog.ClassificationDialog
import xin.lrvik.taskcicleandroid.ui.dialog.TaskStepDialog
import xin.lrvik.taskcicleandroid.util.OssUtil
import kotlin.collections.ArrayList

//startActivity<PostTaskActivity>(PostTaskActivity.MODE to PostTaskActivity.Mode.LOOK.name, PostTaskActivity.HUNTERTASKID to "20190124220137236856064")
class PostTaskActivity : BaseMvpActivity<PostTaskPresenter>(), PostTaskView {

    enum class Mode {
        CREATE(), MODIFY(), LOOK()
    }

    companion object {
        val MODE = "MODE"
        val TASKID = "HUNTERTASKID"
        val REQUEST_CODE_CHOOSE = 1
        val REQUEST_CODE_CROP = 2
    }

    var mode: Mode = Mode.LOOK

    //是否可修改
    var isModify = false

    lateinit var taskid: String

    var mDialog: ClassificationDialog? = null

    var classList = ArrayList<TaskClass>()
    lateinit var mRvTaskStepAdapter: RvModifyTaskStepAdapter
//    var geoCoder = GeoCoder.newInstance()

    //当前选择图片的缓存对象
    lateinit var taskStep: TaskStep

    override fun injectComponent() {
        DaggerTaskCircleComponent.builder().activityComponent(activityComponent).build().inject(this)
        mPresenter.mView = this
    }

    override fun onTaskClassResult(data: List<TaskClass>) {
        mDialog?.let {
            it.setData(data)
            it.setCurrData(0, -1)
        }
    }

    override fun onResult(result: String) {
        toast(result)
        finish()
    }

    override fun onTaskDetailResult(data: TaskDetail) {

        mEtTitle.setText(data.name)
        mLevContext.contentText = data.context
        mRvTaskStepAdapter.setNewData(data.taskSteps)
        classList.addAll(data.taskClassifyAppDtos!!)
        mFlowlayout.adapter.notifyDataChanged()
        checkTipVisible()

        data.state?.let {

            //删除任务
            isShow(mBtDelete, it, listOf(TaskState.NEW_CREATE))

            //提交审核
            isShow(mBtSubmitAudit, it, listOf(TaskState.NEW_CREATE,
                    TaskState.HUNTER_REJECT))

            //取消审核
            isShow(mBtCancelAudit, it, listOf(TaskState.AWAIT_AUDIT,
                    TaskState.AUDIT,
                    TaskState.ADMIN_NEGOTIATE,
                    TaskState.COMMIT_AUDIT))
        }

    }

    internal var colors = intArrayOf(Color.parseColor("#90C5ED"),
            Color.parseColor("#92CED6"),
            Color.parseColor("#F69153"),
            Color.parseColor("#BFAED0"),
            Color.parseColor("#E58F8E"),
            Color.parseColor("#66CCB7"),
            Color.parseColor("#F4BB7E"),
            Color.parseColor("#90C5ED"),
            Color.parseColor("#F79153"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_task)
        initView()
    }

    private fun initView() {
        setSupportActionBar(mToolbar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        mode = Mode.valueOf(intent.getStringExtra(MODE))
        if (mode != Mode.CREATE) {
            try {
                taskid = intent.getStringExtra(TASKID)
            } catch (e: Exception) {
                toast("未传递任务id")
                finish()
            }

        }

        //步骤
        mRvStep.layoutManager = LinearLayoutManager(this)

        var list = ArrayList<TaskStep>()
//        list.add(TaskStep("1", 1, "默认标题", "默认内容", ""))

        mRvTaskStepAdapter = RvModifyTaskStepAdapter(list)
        val mItemDragAndSwipeCallback = ItemDragAndSwipeCallback(mRvTaskStepAdapter)
        val mItemTouchHelper = ItemTouchHelper(mItemDragAndSwipeCallback)
        mItemTouchHelper.attachToRecyclerView(mRvStep)

        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)

        mRvTaskStepAdapter.enableSwipeItem()
        mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)

        mRvStep.adapter = mRvTaskStepAdapter

        mBtAddStep.onClick {
            if (mRvTaskStepAdapter.data.size <= 15) {
                mRvTaskStepAdapter.addData(TaskStep("", -1, "", "", ""))
            } else {
                toast("步骤最多不超过15步")
            }
        }

        mRvTaskStepAdapter.setOnItemClickListener { adapter, view, position ->
            if (mode == Mode.LOOK) {
                val dialog = TaskStepDialog.showDialog(supportFragmentManager,
                        adapter.data as ArrayList<TaskStep>,
                        false, position)

                dialog.setOnCloseListener(object : TaskStepDialog.OnCloseListener {
                    override fun onClose() {
                        mRvTaskStepAdapter.notifyDataSetChanged()
                    }
                })
            }

        }

        mRvTaskStepAdapter.setOnItemChildClickListener { adapter, view, position ->
            taskStep = adapter.data[position] as TaskStep

            when (view.id) {
                R.id.mIvIcon -> {
                    if (mode == Mode.LOOK) {
                        if (!taskStep.img.isNullOrEmpty()) {
                            startActivity<ImageActivity>(ImageActivity.IMGURL to taskStep.img)
                        }
                    } else {
                        //调用图片选择器
                        Matisse.from(this@PostTaskActivity)
                                .choose(MimeType.ofImage())//图片类型
                                .countable(false)//true:选中后显示数字;false:选中后显示对号
                                .maxSelectable(1)//可选的最大数
                                .capture(true)//选择照片时，是否显示拍照
                                .captureStrategy(CaptureStrategy(true, "xin.lrvik.taskcicleandroid.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                .imageEngine(Glide4Engine())//图片加载引擎
                                .forResult(REQUEST_CODE_CHOOSE)
                    }


                }
                R.id.mIvFlag -> {
                    alert {
                        customView {
                            title = "填写步骤信息"
                            verticalLayout {
                                val etTitle = editText {
                                    hint = "请填写步骤标题"
                                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
                                    setText(if (!taskStep.title.isNullOrEmpty()) taskStep.title else "")
                                }
                                val etContent = editText {
                                    hint = "请填写步骤内容"
                                    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(255))
                                    setText(if (!taskStep.title.isNullOrEmpty()) taskStep.context else "")
                                }
                                positiveButton("是") {
                                    var title = etTitle.text.toString().trim()
                                    var context = etContent.text.toString().trim()
                                    /*if (title.isEmpty() || title.length > 20) {
                                        toast("标题请填写20字内")
                                        return@positiveButton
                                    }
                                    if (context.isEmpty() || context.length > 255) {
                                        toast("请填写步骤内容（0~255字内）")
                                        return@positiveButton
                                    }*/
                                    taskStep.title = title
                                    taskStep.context = context
                                    mRvTaskStepAdapter.notifyDataSetChanged()
                                }
                                negativeButton("否") { }
                            }
                        }
                    }.show()
                }
                else -> {
                }
            }
        }

        mTvClassTip.onClick {
            createDialog()
        }

        mBtAddClass.onClick {
            createDialog()
        }

        mFlowlayout.adapter = object : TagAdapter<TaskClass>(classList) {

            override fun getView(parent: FlowLayout, position: Int, taskClass: TaskClass): View {
                //动态创建热词布局
                var tv = TextView(this@PostTaskActivity)
                var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.margin = dip(4)
                tv.layoutParams = params
                tv.setPadding(dip(6), dip(4), dip(6), dip(4))
                tv.setBackgroundColor(colors[position % colors.size])
                tv.text = taskClass.name
                return tv
            }
        }

        mFlowlayout.setOnTagClickListener { view, position, parent ->
            classList.removeAt(position)
            mFlowlayout.adapter.notifyDataChanged()
            checkTipVisible()
            true
        }

        mBtnAdd.onClick {
            if (validation()) {
                var classs = classList.flatMap {
                    listOf(it.id)
                }
                mPresenter.addTask(classs, mEtTitle.text.toString(), mLevContext.contentText, mRvTaskStepAdapter.data)
            }
        }

        mBtnSave.onClick {
            var classs = classList.map {
                it.id
            }
            mPresenter.modifyTask(taskid, classs, mEtTitle.text.toString(), mLevContext.contentText, mRvTaskStepAdapter.data)
        }

        //判断模式
        mode = Mode.valueOf(intent.getStringExtra(MODE))

        when (mode) {
            Mode.LOOK -> {
                isModify = false

                mTvClassTip.visibility = View.GONE
                mBtAddStep.visibility = View.GONE
                mBtnAdd.visibility = View.GONE
                mBtnSave.visibility = View.GONE
                mEtTitle.isEnabled = false
                mLevContext.id_et_input.isEnabled = false
                mRvTaskStepAdapter.disableSwipeItem()
                mRvTaskStepAdapter.disableDragItem()
                mPresenter.queryTaskDetail(taskid)
                mRvTaskStepAdapter.isModify = false
                actionBar?.title = "查看任务"

                mFlowlayout.setOnTagClickListener(null)
            }
            Mode.MODIFY -> {
                isModify = true

                mTvClassTip.visibility = View.VISIBLE
                mBtAddStep.visibility = View.VISIBLE
                mBtnAdd.visibility = View.GONE
                mBtnSave.visibility = View.VISIBLE
                mEtTitle.isEnabled = true
                mLevContext.id_et_input.isEnabled = true
                mRvTaskStepAdapter.enableSwipeItem()
                mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)
                mRvTaskStepAdapter.isModify = true
                mPresenter.queryTaskDetail(taskid)

                actionBar?.title = "保存任务"
            }
            Mode.CREATE -> {
                isModify = true
                mTvClassTip.visibility = View.VISIBLE
                mBtAddStep.visibility = View.VISIBLE
                mBtnAdd.visibility = View.VISIBLE
                mBtnSave.visibility = View.GONE
                mEtTitle.isEnabled = true
                mLevContext.id_et_input.isEnabled = true
                mRvTaskStepAdapter.enableSwipeItem()
                mRvTaskStepAdapter.enableDragItem(mItemTouchHelper)
                mRvTaskStepAdapter.isModify = true
                actionBar?.title = "新建任务"
            }
        }

        mBtDelete.onClick {
            alert("是否删除改任务?") {
                positiveButton("是") { mPresenter.deleteTask(taskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtSubmitAudit.onClick {
            alert("是否提交审核?") {
                positiveButton("是") { mPresenter.submitAudit(taskid) }
                negativeButton("否") { }
            }.show()
        }
        mBtCancelAudit.onClick {
            alert("是否取消审核?") {
                positiveButton("是") { mPresenter.cancelAudit(taskid) }
                negativeButton("否") { }
            }.show()
        }
    }

    fun validation(): Boolean {
        if (classList.size !in 1..5) {
            toast("标签数必须在1~5个")
            return false
        }

        if (mEtTitle.text.toString().isEmpty()) {
            toast("任务标题不能为空")
            return false
        }

        if (mLevContext.contentText.isEmpty()) {
            toast("任务描述不能为空")
            return false
        }

        if (mRvTaskStepAdapter.data.size !in 1..15) {
            toast("步骤至少要有1步,最多不超过15步")
            return false
        }

        for ((index, datum) in mRvTaskStepAdapter.data.withIndex()) {
            datum.step = index
            datum.step++
            if (datum.title.isEmpty() || datum.context.isEmpty()) {
                toast("步骤标题和内容不能为空")
                return false
            }
        }

        return true
    }

    lateinit var mSelected: List<Uri>

    val IMAGE_FILE_LOCATION = "file:///" + Environment.getExternalStorageDirectory().getPath() + "/temp.jpg"
    var imageUri: Uri = Uri.parse(IMAGE_FILE_LOCATION)

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data!!)
            var intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(mSelected[0], "image/*")
            // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
            intent.putExtra("crop", "true")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, REQUEST_CODE_CROP)
        } else if (requestCode == REQUEST_CODE_CROP && resultCode == Activity.RESULT_OK) {
            var mCrop = imageUri.path!!
            //taskStep.img = mCrop
            OssUtil.instance.putFile(mCrop, {
                taskStep.img = it
            }, {
                runOnUiThread {
                    toast("上传失败")
                }
            })
            mRvTaskStepAdapter.notifyDataSetChanged()
        }
    }

    private fun createDialog() {
        if (mDialog == null) {
            mPresenter.classData()
            mDialog = ClassificationDialog()
            mDialog!!.showDialog(supportFragmentManager)
            mDialog!!.listener = object : ClassificationDialog.OnClassificationClickListener {
                override fun onClassClick(pPos: Int, cPos: Int, taskClass: TaskClass) {
                    if (!classList.contains(taskClass)) {
                        classList.add(taskClass)
                        mFlowlayout.adapter.notifyDataChanged()
                        checkTipVisible()
                    }
                }
            }
        } else {
            mDialog!!.showDialog(supportFragmentManager)
        }
    }

    fun checkTipVisible() {
        if (mode == Mode.LOOK) {
            mBtAddClass.visibility = View.GONE
            mTvClassTip.visibility = View.GONE
            return
        }

        if (classList.size == 0) {
            mTvClassTip.visibility = View.VISIBLE
            mBtAddClass.visibility = View.GONE
        } else {
            mTvClassTip.visibility = View.GONE
            mBtAddClass.visibility = View.VISIBLE
        }
    }

    //判断界面是否根据权限显示
    private fun <T> isShow(view: View, state: T, list: List<T>) {
        view.visibility = if (list.contains(state)) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
