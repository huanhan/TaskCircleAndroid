package xin.lrvik.taskcicleandroid.data.protocol

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp

import xin.lrvik.taskcicleandroid.data.protocol.enums.AuditState
import xin.lrvik.taskcicleandroid.data.protocol.enums.AuditType

class Audit : Parcelable {
    var id: String? = null

    var adminId: Long? = null

    var idea: String? = null

    var result: AuditState? = null

    var reason: String? = null

    var type: AuditType? = null

    var createTime: Timestamp? = null

    var auditerName: String? = null

    constructor(source: Parcel) : this(
    )

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Audit

        if (id != other.id) return false
        if (adminId != other.adminId) return false
        if (idea != other.idea) return false
        if (result != other.result) return false
        if (reason != other.reason) return false
        if (type != other.type) return false
        if (createTime != other.createTime) return false
        if (auditerName != other.auditerName) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = id?.hashCode() ?: 0
        result1 = 31 * result1 + (adminId?.hashCode() ?: 0)
        result1 = 31 * result1 + (idea?.hashCode() ?: 0)
        result1 = 31 * result1 + (result?.hashCode() ?: 0)
        result1 = 31 * result1 + (reason?.hashCode() ?: 0)
        result1 = 31 * result1 + (type?.hashCode() ?: 0)
        result1 = 31 * result1 + (createTime?.hashCode() ?: 0)
        result1 = 31 * result1 + (auditerName?.hashCode() ?: 0)
        return result1
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Audit> = object : Parcelable.Creator<Audit> {
            override fun createFromParcel(source: Parcel): Audit = Audit(source)
            override fun newArray(size: Int): Array<Audit?> = arrayOfNulls(size)
        }
    }


}
