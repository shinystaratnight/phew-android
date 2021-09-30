package com.app.phew.models.searchResponse

data class SearchResponse(
    val `data`: ArrayList<SearchModel>,
    val message: String,
    val status: String
)