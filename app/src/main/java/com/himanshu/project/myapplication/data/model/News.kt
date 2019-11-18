package com.himanshu.project.myapplication.data.model

data class News (
    var articles: List<Article>?,
    var status: String?,
    var totalResults: Int?
){}