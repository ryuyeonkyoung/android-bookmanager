package dduw.com.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BookDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                publisher TEXT,
                summary TEXT,
                price INTEGER,
                imageUri TEXT,
                publishedDate TEXT
            )
        """)
        // 샘플 데이터 5권 삽입 (imageUri, publishedDate는 null)
        db.execSQL("INSERT INTO $TABLE_NAME (title, author, publisher, summary, price) VALUES ('어린왕자', '생텍쥐페리', '문학동네', '어린 왕자의 모험', 12000)")
        db.execSQL("INSERT INTO $TABLE_NAME (title, author, publisher, summary, price) VALUES ('데미안', '헤르만 헤세', '민음사', '자아를 찾아가는 이야기', 13000)")
        db.execSQL("INSERT INTO $TABLE_NAME (title, author, publisher, summary, price) VALUES ('1984', '조지 오웰', '민음사', '디스토피아 소설', 14000)")
        db.execSQL("INSERT INTO $TABLE_NAME (title, author, publisher, summary, price) VALUES ('죄와 벌', '도스토예프스키', '열린책들', '인간 심리의 깊이', 15000)")
        db.execSQL("INSERT INTO $TABLE_NAME (title, author, publisher, summary, price) VALUES ('삼국지', '나관중', '민음사', '중국 고전 소설', 20000)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN imageUri TEXT")
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN publishedDate TEXT")
        }
    }

    companion object {
        const val DB_NAME = "book_db"
        const val DB_VERSION = 2
        const val TABLE_NAME = "book_table"
    }
}
