package xin.lrvik.taskcicleandroid.data.protocol

import android.os.Parcel
import android.os.Parcelable
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskState
import xin.lrvik.taskcicleandroid.data.protocol.enums.TaskType
import java.sql.Timestamp

class Task : Parcelable {
    var id: String? = null

    val userId: Long? = null

    var name: String? = null

    var context: String? = null

    var username: String? = null

    var headImg: String? = null

    var money: Float? = null

    var originalMoney: Float? = null

    val state: TaskState? = null

    val type: TaskType? = null

    var longitude: Double? = null

    var latitude: Double? = null

    val peopleNumber: Int? = null

    val beginTime: Timestamp? = null

    val deadline: Timestamp? = null

    val audits: ArrayList<Audit>? = null

    constructor(source: Parcel) : this(
    )

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (name != other.name) return false
        if (context != other.context) return false
        if (username != other.username) return false
        if (headImg != other.headImg) return false
        if (money != other.money) return false
        if (originalMoney != other.originalMoney) return false
        if (state != other.state) return false
        if (type != other.type) return false
        if (longitude != other.longitude) return false
        if (latitude != other.latitude) return false
        if (peopleNumber != other.peopleNumber) return false
        if (beginTime != other.beginTime) return false
        if (deadline != other.deadline) return false
        if (audits != other.audits) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (userId?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (context?.hashCode() ?: 0)
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (headImg?.hashCode() ?: 0)
        result = 31 * result + (money?.hashCode() ?: 0)
        result = 31 * result + (originalMoney?.hashCode() ?: 0)
        result = 31 * result + (state?.hashCode() ?: 0)
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (longitude?.hashCode() ?: 0)
        result = 31 * result + (latitude?.hashCode() ?: 0)
        result = 31 * result + (peopleNumber ?: 0)
        result = 31 * result + (beginTime?.hashCode() ?: 0)
        result = 31 * result + (deadline?.hashCode() ?: 0)
        result = 31 * result + (audits?.hashCode() ?: 0)
        return result
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Task> = object : Parcelable.Creator<Task> {
            override fun createFromParcel(source: Parcel): Task = Task(source)
            override fun newArray(size: Int): Array<Task?> = arrayOfNulls(size)
        }
    }


}
