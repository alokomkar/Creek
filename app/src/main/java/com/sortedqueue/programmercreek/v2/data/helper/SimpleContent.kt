package com.sortedqueue.programmercreek.v2.data.helper

import android.os.Parcel
import android.os.Parcelable
import java.util.regex.Pattern


class SimpleContent(var contentId: String = "",
                    var contentString: String = "",
                    var contentType: Int = 0,
                    var correctOptions: String = "") : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SimpleContent

        if (contentId != other.contentId) return false

        return true
    }

    override fun hashCode(): Int {
        return contentId.hashCode()
    }

    fun getFormattedContent(): String {
        return if (contentType == content) {
            val formattedContent = contentString
                    .replace(".\n", ".\n\n")
                    .replace(". ", ".\n\n")
            formattedContent
        } else {
            contentString
        }
    }

    fun getFillBlanksOptions(): ArrayList<String> {
        val p = Pattern.compile("<(.*?)>")
        val m = p.matcher(contentString)
        val options = ArrayList<String>()
        while (m.find()) {
            options.add(m.group(1))
        }
        return options
    }

    fun getFillBlanksQuestion(): String {
        var question = ""
        val p = Pattern.compile("<(.*?)>")
        val m = p.matcher(contentString)
        question = contentString
        while (m.find()) {
            question.replace(m.group(1), "________")
        }
        return question
    }

    fun getQuestion() : String = contentString.split("?")[0] + "?"

    fun getQuestionOptions() : ArrayList<String> = ArrayList(contentString.split("?")[1].split("|||"))

    fun getCorrectOptions() : ArrayList<String> = ArrayList(correctOptions.split("|||"))


    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(contentId)
        writeString(contentString)
        writeInt(contentType)
        writeString(correctOptions)
    }

    companion object {
        const val header = 0

        const val content = 1

        const val bullets = 2

        const val code = 3

        const val image = 4

        const val mcq = 5

        const val rearrange = 6

        const val fillBlanks = 7

        @JvmField
        val CREATOR: Parcelable.Creator<SimpleContent> = object : Parcelable.Creator<SimpleContent> {
            override fun createFromParcel(source: Parcel): SimpleContent = SimpleContent(source)
            override fun newArray(size: Int): Array<SimpleContent?> = arrayOfNulls(size)
        }
    }
}