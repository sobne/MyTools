package cn.sobne.mytaxticket;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.sobne.dal.InvoiceBean;
import cn.sobne.dal.InvoiceDal;

public class TaxTicketActivity extends AppCompatActivity {
    private static final String TAG = "TaxTicketActivity";
    TextView tv_deleteAll,tv_back,tv_null_text;
    Button btn_finish;
    ImageView iv_add;
    RecyclerView recyclerview;
    private RecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxticket);

        tv_null_text = (TextView) findViewById(R.id.null_text);
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_deleteAll = (TextView) findViewById(R.id.tv_deleteAll);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        iv_add = (ImageView) findViewById(R.id.iv_add);

        tv_back.setOnClickListener(clickListener);
        tv_deleteAll.setOnClickListener(clickListener);
        btn_finish.setOnClickListener(clickListener);
        iv_add.setOnClickListener(clickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(layoutManager);

        updateUI();
//        mAdapter = new RecyclerAdapter( beans);
//        recyclerview.setAdapter(mAdapter);
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() called");
        super.onResume();
        updateUI();

    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.iv_add || i==R.id.btn_finish) {
                startActivity(new Intent(getApplicationContext(), TaxEditActivity.class));
//                InvoiceBean bean = new InvoiceBean();
//                InvoiceDal.get(TaxTicketActivity.this).insert(bean);
//                Intent intent = InvoicePagerActivity.newIntent(TaxTicketActivity.this, bean.getId());
//                startActivity(intent);
            }
            else if (i == R.id.tv_back) {
                finish();
            }
            else if (i == R.id.tv_deleteAll) {
                deleteAllClick();
            }
        }
    };

    public void deleteAllClick() {
        if(recyclerview.getChildCount()<1){
            Toast.makeText(this, "没有数据,无需删除 ", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要全部删除？");
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                InvoiceDal.get(TaxTicketActivity.this).clear();
                updateUI();
            }
        });
        builder.show();
    }


    private void updateUI() {
        InvoiceDal dal = InvoiceDal.get(this);
        List<InvoiceBean> list = dal.getList();
        //list=dal.loadTest();//test

        if (mAdapter == null) {
            mAdapter = new RecyclerAdapter(list);
            recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.setList(list);
            mAdapter.notifyDataSetChanged();
        }
        if(list.size()>0){
            tv_null_text.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
        }
    }



    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<InvoiceBean> list;

        public RecyclerAdapter(List<InvoiceBean> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_taxticket, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            InvoiceBean bean = list.get(position);
            holder.bind(bean);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public void setList(List<InvoiceBean> list) {
            this.list = list;
        }

        class ViewHolder extends RecyclerView.ViewHolder  {
            private InvoiceBean mBean;
            TextView tv_titlename;
            TextView tv_item_detail;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_titlename=itemView.findViewById(R.id.tv_item_title);
                tv_item_detail=itemView.findViewById(R.id.tv_item_detail);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
//                        int position = getAdapterPosition();
//                        Log.d(TAG,"position"+position);
                        Intent intent = InvoicePagerActivity.newIntent(TaxTicketActivity.this, mBean.getId());
                        startActivity(intent);
                    }
                });
            }
            public void bind(InvoiceBean bean) {
                mBean = bean;
                if (bean != null) {
                    tv_titlename.setText(bean.getTitleName());
                    tv_item_detail.setText(bean.getCreditCode());
                    //mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
                }

            }
        }

    }

}
