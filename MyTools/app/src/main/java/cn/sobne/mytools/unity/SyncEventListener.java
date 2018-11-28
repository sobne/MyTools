package cn.sobne.mytools.unity;

import java.util.EventListener;

public interface SyncEventListener extends EventListener {
    public void OnSynced(boolean synced);
}
