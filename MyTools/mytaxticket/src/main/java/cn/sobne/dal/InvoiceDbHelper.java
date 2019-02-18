package cn.sobne.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InvoiceDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "invoice.db";
    public InvoiceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + InvoiceDbSchema.InvoiceTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                InvoiceDbSchema.InvoiceTable.Cols.UUID + ", " +
                InvoiceDbSchema.InvoiceTable.Cols.DATE + ", " +
                InvoiceDbSchema.InvoiceTable.Cols.TITLENAME + ", " +
                InvoiceDbSchema.InvoiceTable.Cols.CREDITCODE +", " +
                InvoiceDbSchema.InvoiceTable.Cols.ADDRESS +", " +
                InvoiceDbSchema.InvoiceTable.Cols.TELEPHONE +", " +
                InvoiceDbSchema.InvoiceTable.Cols.BANKACCOUNT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
