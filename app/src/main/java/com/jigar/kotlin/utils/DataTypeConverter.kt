package com.jigar.kotlin.utils

import com.google.gson.Gson

class DataTypeConverter {
    private val gson = Gson()

//    @TypeConverter
//    fun stringToAttributeList(data: String): List<Attribute> {
//        val listType = object : TypeToken<List<Attribute>>() {}.type
//        return gson.fromJson(data, listType)
//    }
//
//    @TypeConverter
//    fun AttributeListToString(someObjects: List<Attribute>): String {
//        return gson.toJson(someObjects)
//    }
}