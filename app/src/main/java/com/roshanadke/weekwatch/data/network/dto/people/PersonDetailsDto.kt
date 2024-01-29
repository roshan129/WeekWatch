package com.roshanadke.weekwatch.data.network.dto.people

import com.google.gson.annotations.SerializedName
import com.roshanadke.weekwatch.domain.models.people.PersonDetailsModel

data class PersonDetailsDto(
    val adult: Boolean?,
    @SerializedName("also_known_as") val alsoKnownAs: List<String>?,
    val biography: String?,
    val birthday: String?,
    @SerializedName("deathday") val deathDay: String?,
    val gender: Int?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    val name: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    val popularity: Double?,
    @SerializedName("profile_path") val profilePath: String?
) {

    fun toPersonDetailsModel(): PersonDetailsModel {
        return PersonDetailsModel(
            adult = adult,
            alsoKnownAs = alsoKnownAs,
            biography = biography,
            birthday = birthday,
            deathDay = deathDay,
            gender = gender,
            homepage = homepage,
            id = id,
            imdbId = imdbId,
            knownForDepartment = knownForDepartment,
            name = name,
            placeOfBirth = placeOfBirth,
            popularity = popularity,
            profilePath = profilePath
        )
    }
}
