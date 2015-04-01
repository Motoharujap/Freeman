package Logic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import com.motoharu.cleaningapp.R;

/**
 * Created by Serge on 2/23/2015.
 */
public class Dialogs extends Dialog {


    public Dialogs(Context context) {
        super(context);
    }

    protected Dialogs(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static void makeYesNoDialog(Context c, String title, String message, InterfaceDialogCallback yesButtonClick, InterfaceDialogCallback noButtonClick){
        AlertDialog.Builder adb = new AlertDialog.Builder(c);
        if (title!=null) {
            adb.setTitle(title);
        }
        adb.setMessage(message);
        adb.setPositiveButton(R.string.yes, yesButtonClick);
        if (noButtonClick!=null) {
            adb.setNegativeButton(R.string.no, noButtonClick);
        }
        adb.show();
    }
}