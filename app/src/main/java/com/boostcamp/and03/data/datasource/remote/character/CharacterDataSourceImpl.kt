package com.boostcamp.and03.data.datasource.remote.character

import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject

class CharacterDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
): CharacterDataSource {
}
