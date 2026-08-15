package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "favorites")
data class FavoriteEntity(
  @PrimaryKey val id: String,
  val itemType: String, // "FIGURE", "BATTLE", "DYNASTY", "QUOTE"
  val title: String,
  val subtitle: String,
  val category: String,
  val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_notes")
data class StudyNoteEntity(
  @PrimaryKey(autoGenerate = true) val noteId: Long = 0,
  val targetItemId: String,
  val targetTitle: String,
  val noteText: String,
  val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface FavoriteDao {
  @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
  fun getAllFavorites(): Flow<List<FavoriteEntity>>

  @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
  fun isFavorite(id: String): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFavorite(favorite: FavoriteEntity)

  @Query("DELETE FROM favorites WHERE id = :id")
  suspend fun deleteFavoriteById(id: String)
}

@Dao
interface StudyNoteDao {
  @Query("SELECT * FROM study_notes WHERE targetItemId = :targetItemId ORDER BY createdAt DESC")
  fun getNotesForTarget(targetItemId: String): Flow<List<StudyNoteEntity>>

  @Query("SELECT * FROM study_notes ORDER BY createdAt DESC")
  fun getAllNotes(): Flow<List<StudyNoteEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNote(note: StudyNoteEntity): Long

  @Delete
  suspend fun deleteNote(note: StudyNoteEntity)
}

@Database(
  entities = [FavoriteEntity::class, StudyNoteEntity::class],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun favoriteDao(): FavoriteDao
  abstract fun studyNoteDao(): StudyNoteDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "islamic_history_db"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
