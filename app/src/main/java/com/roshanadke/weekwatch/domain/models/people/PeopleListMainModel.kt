package com.roshanadke.weekwatch.domain.models.people

import com.roshanadke.weekwatch.data.network.dto.people.PersonDto

data class PeopleListMainModel(
    val page: Int?,
    val personDtoList: List<PersonModel>?,
)
