package com.roshanadke.weekwatch.common


fun getPosterImageUrl(imageEndpoint: String?): String {
    return Constants.POSTER_IMAGE_BASE_URL + imageEndpoint
}

fun getBackDropImage(imageEndpoint: String?): String {
    return Constants.BACKDROP_IMAGE_BASE_URL + imageEndpoint
}