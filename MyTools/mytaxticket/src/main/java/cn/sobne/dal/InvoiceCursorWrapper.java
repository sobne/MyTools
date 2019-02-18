package cn.sobne.dal;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

public class InvoiceCursorWrapper extends CursorWrapper {

    public InvoiceCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public InvoiceBean getBean() {
        String uuidString = getString(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.UUID));
        long date = getLong(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.DATE));
        String titlename = getString(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.TITLENAME));
        String creditcode = getString(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.CREDITCODE));
        String address=getString(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.ADDRESS));
        String telephone=getString(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.TELEPHONE));
        String bankaccount=getString(getColumnIndex(InvoiceDbSchema.InvoiceTable.Cols.BANKACCOUNT));

        InvoiceBean bean = new InvoiceBean(UUID.fromString(uuidString));
        bean.setDate(new Date(date));
        bean.setTitleName(titlename);
        bean.setCreditCode(creditcode);
        bean.setAddress(address);
        bean.setTelephone(telephone);
        bean.setBankAccount(bankaccount);

        return bean;
    }
}
