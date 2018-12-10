package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.agenda.model.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContactDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public static String[] cols = new String[] {
            SQLiteHelper.ID,
            SQLiteHelper.NAME,
            SQLiteHelper.FONE,
            SQLiteHelper.FONE_ONE,
            SQLiteHelper.EMAIL,
            SQLiteHelper.FAVORITE,
            SQLiteHelper.BIRTHDAY
    };

    public  List<Contact> buscaTodosContatos()
    {
        database=dbHelper.getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();

        Cursor cursor;

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null , null,
                null, null, SQLiteHelper.NAME);

        while (cursor.moveToNext())
        {
            contacts.add(prepare(cursor));
        }

        cursor.close();

        database.close();
        return contacts;
    }

    public  List<Contact> buscaContato(String nome)
    {
        database=dbHelper.getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();

        Cursor cursor;

        String where = SQLiteHelper.NAME + " LIKE ? OR " + SQLiteHelper.EMAIL + " LIKE ?";
        String[] argWhere = new String[] {nome + "%", "%" + nome + "%"};

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.NAME);

        while (cursor.moveToNext())
        {
            contacts.add(prepare(cursor));
        }

        cursor.close();

        database.close();
        return contacts;
    }

    public  List<Contact> buscaContatosFavoritos()
    {
        database=dbHelper.getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();

        Cursor cursor;

        String where=SQLiteHelper.FAVORITE + " = ?";
        String[] argWhere=new String[]{"1"};

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.NAME);

        while (cursor.moveToNext())
        {
            contacts.add(prepare(cursor));
        }

        cursor.close();

        database.close();
        return contacts;
    }

    public void salvaContato(Contact c) {
        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.NAME, c.getName());
        values.put(SQLiteHelper.FONE, c.getPhone());
        values.put(SQLiteHelper.FONE_ONE, c.getPhoneOne());
        values.put(SQLiteHelper.EMAIL, c.getEmail());
        values.put(SQLiteHelper.FAVORITE, c.isFavorite());
        values.put(SQLiteHelper.BIRTHDAY, c.getBirthday());

        if (c.getId() > 0)
            database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.ID + "="
                    + c.getId(), null);
        else
            database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

        database.close();
    }

    public void favoritar(Contact c) {
        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.FAVORITE, c.isFavorite());

        database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.ID + "="
                + c.getId(), null);

        database.close();
    }

    public void apagaContato(Contact c)
    {
        database=dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.ID + "="
                + c.getId(), null);
        database.close();
    }

    private Contact prepare(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getInt(0));
        contact.setName(cursor.getString(1));
        contact.setPhone(cursor.getString(2));
        contact.setPhoneOne(cursor.getString(3));
        contact.setEmail(cursor.getString(4));
        contact.setFavorite(cursor.getInt(5) == 1);
        contact.setBirthday(cursor.getString(6));
        return contact;
    }

}