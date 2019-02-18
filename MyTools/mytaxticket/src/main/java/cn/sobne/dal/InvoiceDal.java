package cn.sobne.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.sobne.dal.InvoiceDbSchema.InvoiceTable.Cols.*;

public class InvoiceDal {
    private static InvoiceDal sInvoiceDal;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static InvoiceDal get(Context context) {
        if (sInvoiceDal == null) {
            sInvoiceDal = new InvoiceDal(context);
        }

        return sInvoiceDal;
    }

    private InvoiceDal(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new InvoiceDbHelper(mContext)
                .getWritableDatabase();

    }

    public void insert(InvoiceBean c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(InvoiceDbSchema.InvoiceTable.NAME, null, values);
    }

    public List<InvoiceBean> loadTest(){
        List<InvoiceBean> beans = new ArrayList<>();
        InvoiceBean bean=new InvoiceBean();
        bean.setTitleName("民航成都物流技术有限公司");
        bean.setCreditCode("9151 0132 5875 9968 0C");
        bean.setAddress("四川省成都市新津工业园区新材料产业功能区新材28路南侧");
        bean.setTelephone("028-82595855");
        bean.setBankAccount("中国银行股份有限公司新津支行125266779486");
        beans.add(bean);
        beans.add(bean);
        return beans;

    }

    public List<InvoiceBean> getList() {
        List<InvoiceBean> crimes = new ArrayList<>();
        InvoiceCursorWrapper cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getBean());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public InvoiceBean getSingle(UUID id) {
        if(id==null) return null;
        InvoiceCursorWrapper cursor = query(
                InvoiceDbSchema.InvoiceTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getBean();
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
        }
    }

    public void update(InvoiceBean bean) {
        String uuidString = bean.getId().toString();
        ContentValues values = getContentValues(bean);
        mDatabase.update(InvoiceDbSchema.InvoiceTable.NAME, values,
                InvoiceDbSchema.InvoiceTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }
    public void delete(UUID id) {
        try {
            this.delete(InvoiceDbSchema.InvoiceTable.Cols.UUID + " = ?", new String[]{id.toString()});
        } catch (Exception e) {
        } finally {

        }
    }
    public void delete(String whereClause, String[] whereArgs) {
        int count = 0;
        try {
            count = mDatabase.delete(InvoiceDbSchema.InvoiceTable.NAME, whereClause, whereArgs);
            //flag = (count > 0 ? true : false);
        } catch (Exception e) {
        } finally {

        }
    }
    public void clear() {
        this.delete(null, null);
//        String sql = "DELETE FROM " + InvoiceDbSchema.InvoiceTable.NAME + ";";
//        mDatabase.execSQL(sql);
    }

    private InvoiceCursorWrapper query(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                InvoiceDbSchema.InvoiceTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new InvoiceCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(InvoiceBean bean) {
        ContentValues values = new ContentValues();
        values.put(UUID, bean.getId().toString());
        values.put(DATE, bean.getDate().getTime());
        values.put(TITLENAME, bean.getTitleName());
        values.put(CREDITCODE,bean.getCreditCode());
        values.put(ADDRESS,bean.getAddress());
        values.put(TELEPHONE,bean.getTelephone());
        values.put(BANKACCOUNT,bean.getBankAccount());
        return values;
    }
}
