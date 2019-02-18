package cn.sobne.mytaxticket;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import cn.sobne.dal.InvoiceBean;
import cn.sobne.dal.InvoiceDal;

public class InvoiceFragment extends Fragment {
    private static final String ARG_INVOICE_ID="cn.sobne.mytaxticket.invoice_id";
    private InvoiceBean mBean;
    TextView tv_delete;
    EditText et_bankaccount,et_telephone,et_address,et_creditcode,et_titlename;
    Button btn_edit;
    int edit=0;
    UUID uuid;


    public static InvoiceFragment newInstance(UUID Id)
    {
        Bundle args=new Bundle();
        args.putSerializable(ARG_INVOICE_ID,Id);
        InvoiceFragment fragment =new InvoiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uuid=(UUID)getArguments().getSerializable(ARG_INVOICE_ID);
        mBean =InvoiceDal.get(getActivity()).getSingle(uuid);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_taxedit, container, false);

        et_titlename = (EditText) v.findViewById(R.id.et_titlename);
        et_creditcode = (EditText) v.findViewById(R.id.et_creditcode);
        et_address = (EditText) v.findViewById(R.id.et_address);
        et_telephone = (EditText) v.findViewById(R.id.et_telephone);
        et_bankaccount = (EditText) v.findViewById(R.id.et_bankaccount);

        tv_delete = (TextView) v.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(clickListener);
        btn_edit = (Button) v.findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(clickListener);

        if(mBean!=null){
            et_titlename.setText(mBean.getTitleName());
            et_creditcode.setText(mBean.getCreditCode());
            et_address.setText(mBean.getAddress());
            et_telephone.setText(mBean.getTelephone());
            et_bankaccount.setText(mBean.getBankAccount());
            edit=0;
        }else{
            edit=1;
        }
        setEnable();

        return v;
    }
    @Override
    public void onPause() {
        super.onPause();
    }



    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btn_edit) {
                if(edit==0){
                    edit=1;
                    setEnable();
                }else{
                    boolean isNew=false;
                    if(mBean==null){
                        mBean=new InvoiceBean();
                        isNew=true;
                    }
                    mBean.setTitleName(et_titlename.getText().toString());
                    mBean.setCreditCode(et_creditcode.getText().toString());
                    mBean.setAddress(et_address.getText().toString());
                    mBean.setTelephone(et_telephone.getText().toString());
                    mBean.setBankAccount(et_bankaccount.getText().toString());
                    if(isNew){
                        InvoiceDal.get(getActivity()).insert(mBean);
                    }else{
                        InvoiceDal.get(getActivity()).update(mBean);
                    }
                    Toast.makeText(getActivity(), "save successfully", Toast.LENGTH_SHORT).show();
                    edit=0;
                    setEnable();
                }
            }
            else if (i == R.id.tv_delete) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定要删除？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        InvoiceDal.get(getActivity()).delete(uuid);
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_LONG).show();
                        //TODO
                    }
                });
                builder.show();
            }
        }
    };

    private void setEnable()
    {
        boolean enabled=edit==1;
        et_titlename.setEnabled(enabled);
        et_creditcode.setEnabled(enabled);
        et_address.setEnabled(enabled);
        et_telephone.setEnabled(enabled);
        et_bankaccount.setEnabled(enabled);
        if(enabled){
            btn_edit.setText("保存");
            if(mBean==null){
                tv_delete.setVisibility(View.GONE);
            }else{
                tv_delete.setVisibility(View.VISIBLE);
            }
        }else{
            tv_delete.setVisibility(View.GONE);
            btn_edit.setText("编辑");
        }
    }


}