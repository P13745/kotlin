package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ Vertex::class, Edge::class], version = 1)
abstract class GraphDatabase: RoomDatabase() {

    abstract fun vertexDao(): VertexDao
    abstract fun edgeDao(): EdgeDao
    /*
    suspend fun getEdges(): List<Edge> {
        return edgeDao().getAll().firstOrNull() ?: emptyList()
    }
    suspend fun getVertices(): List<Vertex> {
        return  return vertexDao().getAll().firstOrNull() ?: emptyList()
    }

 */


    companion object {
        @Volatile
        private var INSTANCE: GraphDatabase? = null

        fun getDatabase(context: Context): GraphDatabase {
            val x = context.applicationContext.filesDir
            return INSTANCE ?: synchronized(this) {
                //Log.v("### External ###", "${context.applicationContext}")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GraphDatabase::class.java,
                    "graph_test.db"
                )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    // DAOのインスタンスを取得する拡張関数
    fun GraphDatabase.getVertexDao(): VertexDao {
        return vertexDao()
    }

    fun GraphDatabase.getEdgeDao(): EdgeDao {
        return edgeDao()
    }







        /*
        fun getDatabase(context: Context): GraphDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GraphDatabase::class.java,
                    "graph.db"
                )
                    .createFromAsset("database/graph.db")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // 新しいデータベースファイルを作成し、テーブルを作成する
                            val databaseFile = File(context.getDatabasePath("graph.db").path)
                            val newDatabase = SQLiteDatabase.openOrCreateDatabase(databaseFile, null)

                            // "vertex"テーブルを作成するSQLクエリ
                            val createVertexTableQuery = "CREATE TABLE IF NOT EXISTS vertex (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "x REAL NOT NULL," +
                                    "y REAL NOT NULL" +
                                    ")"

                            // "edge"テーブルを作成するSQLクエリ
                            val createEdgeTableQuery = "CREATE TABLE IF NOT EXISTS edge (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "start_id INTEGER NOT NULL," +
                                    "end_id INTEGER NOT NULL" +
                                    ")"

                            // テーブルを作成する
                            newDatabase.execSQL(createVertexTableQuery)
                            newDatabase.execSQL(createEdgeTableQuery)

                            // データベースファイルを保存する
                            newDatabase.close()
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }

         */




/*
        fun getDatabase(
            context: Context
        ): GraphDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GraphDatabase::class.java,
                    "graph.db"
                )
                    .createFromAsset("database/graph.db")
                    .addCallback(object : RoomDatabase.Callback() { //初期データの追加
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // デフォルトのデータベースファイルが存在しない場合は、初期データを挿入する
                            val vertexList = getDefaultVertexList()
                            val edgeList = getDefaultEdgeList()

                            Executors.newSingleThreadExecutor().execute {
                                val vertexDao = GraphDatabase.getDatabase(context).vertexDao()
                                CoroutineScope(Dispatchers.IO).launch {
                                    vertexDao.insertAll(vertexList)
                                }


                                val edgeDao = GraphDatabase.getDatabase(context).edgeDao()
                                CoroutineScope(Dispatchers.IO).launch {
                                    edgeDao.insertAll(edgeList)
                                }

                            }
                        }
                    })
                    .build()
                INSTANCE = instance

                instance
            }
        }

 */



        /*
        fun getDatabase(
            context: Context
        ): GraphDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GraphDatabase::class.java,
                    "graph.db"
                )
                    .createFromAsset("database/graph.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }
        */
        private fun getDefaultVertexList(): List<Vertex> {
            return listOf(
                Vertex(0, 500f, 900f),
                Vertex(1 ,400f, 150f),
                Vertex(2, 600f, 400f),
                Vertex(3, 300f, 700f),
                Vertex(4, 900f, 500f))
        }



        private fun getDefaultEdgeList(): List<Edge> {
            return listOf(
                Edge(0,0, 1),
                Edge(1,0, 2),
                Edge(2,2, 1),
                Edge(3,0, 3),
                Edge(4,1, 3),
                Edge(5,2, 4),
                Edge(6,4, 4),
                Edge(7,0, 4))
        }


}