package cn.sobne.mytaxticket;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

import cn.sobne.dal.InvoiceBean;
import cn.sobne.dal.InvoiceDal;

public class InvoicePagerActivity extends AppCompatActivity {
    private static final String EXTRA_INVOICE_ID = "cn.sobne.mytaxticket.invoice_id";

    private ViewPager mViewPager;
    private List<InvoiceBean> mBeans;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, InvoicePagerActivity.class);
        intent.putExtra(EXTRA_INVOICE_ID, crimeId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoicepager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_INVOICE_ID);

        mViewPager = (ViewPager) findViewById(R.id.invoice_view_pager);

        mBeans = InvoiceDal.get(this).getList();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                InvoiceBean crime = mBeans.get(position);
                return InvoiceFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mBeans.size();
            }
        });

        for (int i = 0; i < mBeans.size(); i++) {
            if (mBeans.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
