package com.example.cosmicslibrary.domain.model

import com.google.gson.annotations.SerializedName

data class CharacterApiResponse(
    @SerializedName("error") val error: String,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("number_of_page_results") val numberOfPageResults: Int,
    @SerializedName("number_of_total_results") val numberOfTotalResults: Int,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("results") val results: List<Character>,
    @SerializedName("version") val version: String
)

data class Character(
    @SerializedName("aliases") val aliases: String?,
    @SerializedName("api_detail_url") val apiDetailUrl: String,
    @SerializedName("birth") val birth: String?,
    @SerializedName("count_of_issue_appearances") val countOfIssueAppearances: Int,
    @SerializedName("date_added") val dateAdded: String,
    @SerializedName("date_last_updated") val dateLastUpdated: String,
    @SerializedName("deck") val deck: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("first_appeared_in_issue") val firstAppearedInIssue: FirstAppearedInIssue?,
    @SerializedName("gender") val gender: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: ImageUrls,
    @SerializedName("name") val name: String,
    @SerializedName("origin") val origin: Origin?,
    @SerializedName("publisher") val publisher: Publisher?,
    @SerializedName("real_name") val realName: String?,
    @SerializedName("site_detail_url") val siteDetailUrl: String,
    @SerializedName("resource_type") val resourceType: String
)

data class FirstAppearedInIssue(
    @SerializedName("api_detail_url") val apiDetailUrl: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("issue_number") val issueNumber: String
)

data class ImageUrls(
    @SerializedName("icon_url") val iconUrl: String,
    @SerializedName("medium_url") val mediumUrl: String,
    @SerializedName("screen_url") val screenUrl: String,
    @SerializedName("screen_large_url") val screenLargeUrl: String,
    @SerializedName("small_url") val smallUrl: String,
    @SerializedName("super_url") val superUrl: String,
    @SerializedName("thumb_url") val thumbUrl: String,
    @SerializedName("tiny_url") val tinyUrl: String,
    @SerializedName("original_url") val originalUrl: String,
    @SerializedName("image_tags") val imageTags: String
)

data class Origin(
    @SerializedName("api_detail_url") val apiDetailUrl: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class Publisher(
    @SerializedName("api_detail_url") val apiDetailUrl: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
