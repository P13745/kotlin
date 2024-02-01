import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.workDataOf
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.workers.BlurWorker
import com.example.bluromatic.workers.CleanupWorker
import com.example.bluromatic.workers.SaveImageToFileWorker
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class WokerInstrumentationTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context =
            ApplicationProvider.getApplicationContext()
    }

    //OneTimeWorkRequestBuilder を使用してワーカーを作成します。Worker のテストには、さまざまな処理ビルダーが必要です。WorkManager API には、次の 2 種類のビルダーが用意されています。
    //
    //TestWorkerBuilder
    //TestListenableWorkerBuilder
    //どちらのビルダーでも、ワーカーのビジネス ロジックをテストできます。
    // CleanupWorker、BlurWorker、SaveImageToFileWorker などの
    // CoroutineWorkers は、複雑なコルーチンのスレッドを扱うため、
    // テストには TestListenableWorkerBuilder を使用します。

    @Test
    fun cleanupWorker_doWork_resultSuccess() {
        val worker = TestListenableWorkerBuilder<CleanupWorker>(context).build()
        runBlocking {
            val result = worker.doWork()
            assertTrue(result is ListenableWorker.Result.Success)
        }
    }


    @Test
    fun blurWorker_doWork_resultSuccessReturnsUri() {
        val worker = TestListenableWorkerBuilder<BlurWorker>(context)
            .setInputData(workDataOf(/*mockUriInput*/
                KEY_IMAGE_URI to "android.resource://com.example.bluromatic/drawable/android_cupcake"
            ))
            .build()
        runBlocking {
            val result = worker.doWork()
            val resultUri = result.outputData.getString(KEY_IMAGE_URI)
            assertTrue(result is ListenableWorker.Result.Success)
            assertTrue(result.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))//URI を含む Key-Value ペアか
            assertTrue(//出力データに文字列 "file:--- の URI が含まれているか
                resultUri?.startsWith("file:///data/user/0/com.example.bluromatic/files/blur_filter_outputs/blur-filter-output-")
                    ?: false
            )


            @Test
            fun saveImageToFileWorker_doWork_resultSuccessReturnsUrl() {
                val worker = TestListenableWorkerBuilder<SaveImageToFileWorker>(context)
                    .setInputData(workDataOf(/*mockUriInput*/
                        KEY_IMAGE_URI to "android.resource://com.example.bluromatic/drawable/android_cupcake"
                    ))
                    .build()
                runBlocking {
                    val result = worker.doWork()
                    val resultUri = result.outputData.getString(KEY_IMAGE_URI)
                    assertTrue(result is ListenableWorker.Result.Success)
                    assertTrue(result.outputData.keyValueMap.containsKey(KEY_IMAGE_URI))
                    assertTrue(
                        resultUri?.startsWith("content://media/external/images/media/")
                            ?: false
                        //注: SaveImageToFileWorker がディスクに保存する画像ファイルの URL は、
                    // "content://media/external/images/media/" という文字列で始まっている必要があります。
                    )
                }
            }
        }


    }





}