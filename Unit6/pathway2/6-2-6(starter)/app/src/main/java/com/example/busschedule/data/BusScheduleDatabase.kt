package com.example.busschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



//これはRoomを使うための設定
/*
1@Database アノテーションでデータベースの情報を指定しています。
entities パラメータはデータベース内で使用されるエンティティ（テーブル）を指定し、
version パラメータはデータベースのバージョンを指定しています。

AppDatabase クラスは RoomDatabase を継承しています。
このクラスはデータベースのインスタンスを作成し、データベースへのアクセスを提供します。

busScheduleDao() メソッドは、BusScheduleDao インターフェースへのアクセスを提供します。
この DAO（Data Access Object）はデータベースへのクエリを実行するためのメソッドを含んでいます。

companion object 内にはデータベースのインスタンスを取得するためのメソッド getDatabase() が定義されています。
このメソッドでは、データベースのインスタンスがまだ作成されていない場合に、
同期化されたブロック内で新しいデータベースのビルダーが作成され、データベースがビルドされます。
また、createFromAsset("database/bus_schedule.db") はアプリに同梱された bus_schedule.db という名前のデータベースファイルを使用するように設定しています。

fallbackToDestructiveMigration() は、データベースのバージョンが上がった場合に
マイグレーションが定義されていない場合、データベースを破壊的な方法で再構築するオプションです。

このクラスは、アプリ内で唯一のデータベースインスタンスを提供し、
アプリケーション内の異なる部分からデータベースにアクセスするために使用されます。
同時に、@Volatile を使用してスレッドセーフなダブルチェックロッキングを実現し、データベースインスタンスが一意であることを保証しています。
 */
@Database(entities = [BusSchedule::class], version = 1)
abstract class BusScheduleDatabase: RoomDatabase() {
    abstract fun busScheduleDao(): BusScheduleDao

    companion object {
        @Volatile
        private var Instance: BusScheduleDatabase? = null

        fun getDatabase(context: Context): BusScheduleDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BusScheduleDatabase::class.java, "app_database")
                    .createFromAsset("database/bus_schedule.db") //ファイルを指定
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}