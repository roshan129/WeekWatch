package com.roshanadke.weekwatch.domain.models.people

import com.google.gson.annotations.SerializedName
import com.roshanadke.weekwatch.data.network.dto.people.KnownForDto

data class PersonModel(
    val adult: Boolean?,
    val gender: Int?,
    val id: Int?,
    val knownForDtoList: List<KnownForModel>?,
    val knownForDepartment: String?,
    val name: String?,
    val originalName: String?,
    val popularity: Double?,
    val profilePath: String?
)
