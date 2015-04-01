package Logic;

import android.content.DialogInterface;

/**
 * Created by Serge on 2/23/2015.
 */
public interface InterfaceDialogCallback extends DialogInterface.OnClickListener {

    @Override
    void onClick(DialogInterface dialog, int which);

}
