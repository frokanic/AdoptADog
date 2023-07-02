package com.example.adoptadog.domain.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


open class Dog : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId.invoke()
    var breed: String = ""
    var imageUrl: String = ""
}


