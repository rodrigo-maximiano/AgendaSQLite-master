package br.edu.ifspsaocarlos.agenda.data;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agendaplus.db";
    static final String DATABASE_TABLE = "contacts";
    static final String ID = "id";
    static final String NAME = "name";
    static final String FONE = "phone";
    static final String EMAIL = "email";
    static final String FAVORITE = "favorite";
    static final String FONE_ONE = "phoneone";
    static final String BIRTHDAY = "birthday";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE +" (" +
            ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            FONE + " TEXT, "  +
            EMAIL + " TEXT, " +
            FONE_ONE + " TEXT, "  +
            BIRTHDAY + " TEXT, "  +
            FAVORITE + " INTEGER);";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql;
        switch (oldVersion){
            case 0:
            case 1:
                sql = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + FAVORITE + " INTEGER";
                db.execSQL(sql);
                ContentValues values = new ContentValues();
                values.put(SQLiteHelper.FAVORITE, Boolean.FALSE);
                db.update(SQLiteHelper.DATABASE_TABLE, values, null, null);
                break;
            case 2:
                sql = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + FONE_ONE + " TEXT";
                db.execSQL(sql);
                break;
            case 3:
                sql= "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " + BIRTHDAY + " TEXT";
                db.execSQL(sql);
                break;

             default:
        }
    }
}

