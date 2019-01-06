package xin.lrvik.taskcicleandroid.data.protocol

import android.os.Parcel
import android.os.Parcelable

/**
 * Author by 豢涵, Email huanhanfu@126.com, Date on 2019/1/6.
 */
data class TaskStep(var taskId: String,
                    var step: Int,
                    var title: String,
                    var context: String,
                    var img: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(taskId)
        writeInt(step)
        writeString(title)
        writeString(context)
        writeString(img)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TaskStep> = object : Parcelable.Creator<TaskStep> {
            override fun createFromParcel(source: Parcel): TaskStep = TaskStep(source)
            override fun newArray(size: Int): Array<TaskStep?> = arrayOfNulls(size)
        }
    }
}
