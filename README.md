# Room Database
<img width="613" alt="Screen Shot 2023-04-04 at 6 36 09 AM" src="https://user-images.githubusercontent.com/29136735/230316313-3f626d73-dafa-452b-ac7e-68ad34c45422.png">


## Description
- This is a sample Android app that demonstrates the use of Room, a SQLite object mapping library, to perform CRUD (Create, Read, Update, Delete) operations on a local database.

- The app allows the user to add new items, view all saved items, update existing items, and delete items from the database. It uses only Room to handle all database operations, without the use of any other database libraries or tools. This completely for beginners.

## Features
- Add new items to the database
- View a list of all items saved in the database
- Update existing items in the database
- Delete items from the database

## Technologies Used
- Room: A SQLite object mapping library
- LiveData: A lifecycle-aware observable data holder

## Installation
```
def room_version = "2.5.1"
implementation "androidx.room:room-runtime:$room_version"
implementation "androidx.room:room-ktx:$room_version"
kapt "androidx.room:room-compiler:$room_version"
```

If you're using Mac M1 you may encountered randomly error, add this to fix.
```
kapt "org.xerial:sqlite-jdbc:3.36.0" // Only for computer with M1 CPU
```

## Usage
### Major 3 components in Room
- `Database class`

  - The `Database class` in Room is responsible for managing the creation and upgrading of the local database, providing access to DAOs, and defining callback methods. It is an abstract class that extends `RoomDatabase` and is annotated with `@Database`. The class has abstract methods that return `DAO` instances annotated with `@Dao`. The Database class provides an easy-to-use way to manage and access local data in Android apps.
     ```
     @Database(entities = [Contact::class], version = 1, exportSchema = true)
     abstract class AppDatabase : RoomDatabase() {
      ...
     }
     ```
  - `entities`: This parameter specifies a list of entity classes that will be used to create tables in the database.
  - `version`: This parameter specifies the version number of the database. Whenever you change the schema of the database, you should increment this number. Room uses this number to handle database migrations automatically.
  - `exportSchema`: This parameter is a boolean flag that specifies whether to generate a schema file for the database. This file can be useful for debugging and version control, as it contains the schema of the database in a human-readable format.
  

- `Data entity`

  - That represent tables in your app's database.
     ```
     @Entity(tableName = "contacts")
     data class Contact (
       @PrimaryKey(autoGenerate = true)
       val id: Int? = null,
    
       @ColumnInfo(name = "contact_name")
       val name: String? = null,
    
       val phone: String? = null
     )
     ```
  - You define each `Room entity` as a class that is annotated with `@Entity`
  - `tableName` is optional if you don't set it, Room will auto take and named your table same as class
  - If you don't annotated at least one column with `@PrimaryKey` you may encountered error: *An entity must have at least 1 field annotated with @PrimaryKey*
  - `autoGenerate = true` means you let Room to auto assign value to your `PrimaryKey` column
  - `@ColumnInfo` when you wish to named any name you like to set. If not Room will auto named variable & column same. Ex: `val phone: String? = null` in table in db will appear phone as its name.
  - And there are many more `annotations` to learn about data entities in Room, see [Defining data using Room entities](https://developer.android.com/training/data-storage/room/defining-data).


- `DAO (Data access object)` 
  - That provide methods that your app can use to query, update, insert, and delete data in the database.
    ```
    @Dao
    interface ContactDao {
      ...
    }
    ```
  - You define each `DAOs` as a interface that is annotated with `@Dao`
  
  ### Methods
  There are 4 methods: `@Insert`, `@Update`, `Delete` and `@Query`

  - `@Insert` allows you to insert one or more entities into the database.
    ```
    @Dao
    interface ContactDao {
       @Insert
       fun insert(contact: Contact)

       @Insert
       fun insertAll(contacts: List<Contact>)
    }
    ```
    `Conflict Strategy `: Room allow you to determine when inserting or updating operation encounters a conflict with an existing row.
    
    The available strategies are:
    - `OnConflictStrategy.REPLACE` This strategy replaces the old row with the new row being inserted or updated.
    - `OnConflictStrategy.IGNORE` This strategy ignores the new row being inserted or updated and keeps the old row.
    - `OnConflictStrategy.ABORT` This strategy cancels the current transaction and rolls back the changes made so far.

    ```
    @Dao
    interface ContactDao {
       @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insertUser(contact: Contact)
    }
    ```

  - `@Update` allow you to update one or more entities in the database.
    ```
    @Dao
    interface ContactDao {
       @Update
       fun update(contact: Contact)

       @Update
       fun updateAll(contacts: List<Contact>)
    }
    ```
    Same to `@Insert` on Conflict Strategy 

  - `@Delete` allow you to delete one or more entities in the database.
    ```
    @Dao
    interface ContactDao {
       @Delete
       fun delete(contact: Contact)

       @Delete
       fun deleteAll(contacts: List<Contact>)
    }
    ```

  - `@Query` allow you to retrieve data from the database using various types of queries. And perform same as these 3 build-in method `@Insert`, `@Update` and `@Delete`
    ```
    @Dao
    interface ContactDao {
      @Query("UPDATE contacts SET name = :name, phone = :phone WHERE id = :id")
      fun update(id: Int, name: String, phone: String)
    
      // Out of project sample
      @Query("SELECT * FROM users WHERE id = :userId")
      fun getUserById(userId: Int): User
    
      @Query("SELECT * FROM users WHERE name LIKE :name")
      fun getUsersByName(name: String): List<User>
    }
    ```
  
  I believe that's all for major components which important in `Room`, please clone and explore more on this example. Thanks, please have a good day.

  
