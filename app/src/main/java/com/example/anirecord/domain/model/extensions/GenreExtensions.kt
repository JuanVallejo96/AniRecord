package com.example.anirecord.domain.model.extensions

import com.example.anirecord.data.entity.Genre
import com.example.anirecord.domain.model.GenreModel

fun Genre.toModel() = GenreModel(name)