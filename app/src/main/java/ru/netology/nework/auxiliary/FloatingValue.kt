package ru.netology.nework.auxiliary

import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap


object FloatingValue {
    var textNewPost = ""
    var currentFragment = ""

    fun convertDatePublished(dateString: String): String {

        val date1 = dateString.substring(8..9)+dateString.substring(4..7)+dateString.substring(0..3)
      val date=date1.replace("-", ".")
        //  val date = dateString.substring(0..9)
        val time = dateString.substring(11..18)
        return "$date $time"
    }

    fun getExtensionFromUri(uri: Uri, contentResolver: ContentResolver): String? {
        val mimeType = contentResolver.getType(uri) ?: return null
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
    }

}