package wenran.com.module_player.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by Crowhine on 2019/4/11
 *
 * @author Crowhine
 */
public class PlayerServiceConnection  implements ServiceConnection {
    AudioPlayerService audioPlayerService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        audioPlayerService = ((AudioPlayerService.PlayerBinder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        audioPlayerService = null;
    }

    public AudioPlayerService getPlayerService() {
        return audioPlayerService;
    }
}
