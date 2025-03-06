package com.network.logger.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigration {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                ALTER TABLE t_log ADD COLUMN created_at INTEGER DEFAULT 0
            """)
        }
    }
}