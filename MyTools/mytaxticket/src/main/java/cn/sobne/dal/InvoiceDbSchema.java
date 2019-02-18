package cn.sobne.dal;

public class InvoiceDbSchema {
    public static final class InvoiceTable{
        public static final String NAME="invoice";
        public static final class Cols{
            public static final String UUID="uuid";
            public static final String DATE="date";
            public static final String TITLENAME="titlename";
            public static final String CREDITCODE="creditcode";
            public static final String ADDRESS="address";
            public static final String TELEPHONE="telephone";
            public static final String BANKACCOUNT="bankaccount";
        }
    }
}
