package cn.sobne.mytaxticket;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class TaxEditActivity extends SingleFragmentActivity {
    private static final String EXTRA_UUID="cn.sobne.mytaxticket.uuid";
    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, TaxEditActivity.class);
        intent.putExtra(EXTRA_UUID, uuid);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        UUID uuid=(UUID) getIntent().getSerializableExtra(EXTRA_UUID);
        return InvoiceFragment.newInstance(uuid);
    }
}
