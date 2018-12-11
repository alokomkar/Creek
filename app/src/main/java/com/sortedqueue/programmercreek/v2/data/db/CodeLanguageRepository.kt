package com.sortedqueue.programmercreek.v2.data.db

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sortedqueue.programmercreek.v2.data.api.CodeLanguageAPI
import com.sortedqueue.programmercreek.v2.data.model.CodeLanguage
import com.sortedqueue.programmercreek.v2.data.remote.PCFirebaseHandler
import java.util.*

class CodeLanguageRepository( application: Application ) : CodeLanguageAPI {

    override fun fetchLiveCodeLanguages(): LiveData<List<CodeLanguage>>
            = api.fetchLiveCodeLanguages()

    override fun fetchLiveCodeLanguageById(id: String): LiveData<CodeLanguage>
            = api.fetchLiveCodeLanguageById(id)

    private val api = PCFirebaseHandler.getAPI( application )

    override fun insertOrUpdate(obj: CodeLanguage) = api.insertOrUpdate(obj)

    override fun insertOrUpdate(vararg obj: CodeLanguage) {}

    override fun insertOrUpdate(obj: ArrayList<out CodeLanguage>)
            = api.insertOrUpdate(obj)

    init {

        /*val codeLanguages = ArrayList<CodeLanguage>()

        codeLanguages.add( CodeLanguage(
                "",
                "C",
                "C is a high-level and general purpose programming " +
                        "language that is ideal for " +
                        "developing firmware or portable applications.",
                ".c",
                System.currentTimeMillis(),
                System.currentTimeMillis()))

        codeLanguages.add( CodeLanguage(
                "",
                "C++",
                "C++ is a general-purpose, statically typed, " +
                        "free-form, multi-paradigm programming language " +
                        "supporting procedural programming, " +
                        "data abstraction, and generic programming.",
                ".cpp",
                System.currentTimeMillis(),
                System.currentTimeMillis()))

        codeLanguages.add( CodeLanguage(
                "",
                "Java",
                "Java is a general-purpose computer " +
                        "programming language that is concurrent, " +
                        "class-based, object-oriented, and specifically " +
                        "designed to have as few implementation " +
                        "dependencies as possible.",
                ".java",
                System.currentTimeMillis(),
                System.currentTimeMillis()))

        *//*codeLanguages.add( CodeLanguage(
                "",
                "MySQL",
                "Java is a general-purpose computer " +
                        "programming language that is concurrent, " +
                        "class-based, object-oriented, and specifically " +
                        "designed to have as few implementation " +
                        "dependencies as possible.",
                ".sql",
                System.currentTimeMillis(),
                System.currentTimeMillis()))*//*



        insertOrUpdate( codeLanguages )*/

    }


}