package cn.sobne.mytools.unity;

import android.content.Context;

public class MainUtil {
    private Context mContext;
    public MainUtil(Context context){
        mContext = context.getApplicationContext();
    }

    private SyncEventListener syncListener;
    public void addSyncEventListener(SyncEventListener syncEventListener){
        syncListener=syncEventListener;
    }
    private void notifyEvent(boolean synced){
        syncListener.OnSynced(synced);
    }



    public void callServer(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyEvent(false);
    }
}
