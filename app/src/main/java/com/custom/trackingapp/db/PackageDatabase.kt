package com.custom.trackingapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.custom.trackingapp.models.parcel.PackageModel

@Database(entities = [PackageModel::class], version = 1, exportSchema = false)
abstract  class PackageDatabase : RoomDatabase() {

    abstract fun getPackageDao() : PackageDb
}