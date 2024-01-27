package com.roshanadke.weekwatch.data.network.dto.people

import com.google.gson.annotations.SerializedName
import com.roshanadke.weekwatch.domain.models.people.PeopleListMainModel

data class PeopleListMainDto(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val personDtoList: List<PersonDto>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
) {

    fun toPeopleList(): PeopleListMainModel  {
        return PeopleListMainModel(
            page = page,
            personDtoList = personDtoList?.map { it.toPersonModel() }
        )
    }
}
