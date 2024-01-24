package com.example.busschedule

import android.app.Application
import com.example.busschedule.data.BusScheduleDatabase

class BusScheduleApplication: Application() {
    val database: BusScheduleDatabase by lazy { BusScheduleDatabase.getDatabase(this) }
}
/*
database プロパティは、by lazy デリゲートを使用して遅延初期化されています。
これにより、database が初めてアクセスされるときに初期化が行われます。

BusScheduleDatabase.getDatabase(this) は、BusScheduleDatabase クラス内の
companion object で定義された getDatabase メソッドを呼び出しています。
このメソッドは、データベースのインスタンスを取得します。
また、このメソッド内でデータベースのインスタンスが初めて作成されると、
そのインスタンスを INSTANCE にキャッシュしています。

このような構造にすることで、アプリケーション内のどの部分からでも
BusScheduleApplication クラスを介して同じデータベースインスタンスにアクセスできます。
 */