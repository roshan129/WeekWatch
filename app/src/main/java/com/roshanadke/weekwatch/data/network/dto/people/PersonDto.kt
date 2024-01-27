package com.roshanadke.weekwatch.data.network.dto.people

import com.google.gson.annotations.SerializedName
import com.roshanadke.weekwatch.domain.models.people.PersonModel

data class PersonDto(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("id") val id: Int?,
    @SerializedName("known_for") val knownForDtoList: List<KnownForDto>?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?
) {

    fun toPersonModel(): PersonModel {
        return PersonModel(
            adult = adult,
            gender = gender,
            id = id,
            knownForDtoList = knownForDtoList?.map { it.toKnownForModel() },
            knownForDepartment = knownForDepartment,
            name = name,
            originalName = originalName,
            popularity = popularity,
            profilePath = profilePath
        )
    }
}
