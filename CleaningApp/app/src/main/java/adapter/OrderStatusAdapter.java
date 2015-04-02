package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.motoharu.cleaningapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import data.OrderStatusItem;

/**
 * Created by Serge on 1/7/2015.
 */
public class OrderStatusAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OrderStatusItem> orderItems;
    private LinearLayout background;
    private ViewParent parent;
    public boolean isChecked;
    public ArrayList<Integer> positionList;
    //public CompoundButton.OnCheckedChangeListener checkedChangeListener;
    private HashMap<Integer, Boolean> checkedPositions = new HashMap<Integer, Boolean>();

    public OrderStatusAdapter(Activity activity, List<OrderStatusItem> feedItems) {
        this.activity = activity;
        this.orderItems = feedItems;
       // this.checkedChangeListener = checkedChangeListener;
    }

    public void setNewSelection(int position, boolean value) {
        checkedPositions.put(position, value);
        notifyDataSetChanged();
    }
    public Set<Integer> getKeySet(){
        return checkedPositions.keySet();
    }
    public boolean isPositionChecked(int position) {
        Boolean result = checkedPositions.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition() {
        return checkedPositions.keySet();
    }

    public void removeSelection(int position) {
        checkedPositions.remove(position);
        notifyDataSetChanged();
    }

    public int getOrderId(int position){
        int orderId = orderItems.get(position).getId();
        return orderId;
    }

    public void clearSelection() {
        checkedPositions = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int location) {
        return orderItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return orderItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.order_status_item, null);
        this.parent = parent;
        background = (LinearLayout) convertView.findViewById(R.id.orderBackground);
        TextView shirtsQ = (TextView) convertView.findViewById(R.id.shirtsQ);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timeOrderMade);
        TextView summ = (TextView) convertView
                .findViewById(R.id.summ);
        TextView statusOrder = (TextView) convertView.findViewById(R.id.statusOfCurrentOrder);
        OrderStatusItem item = orderItems.get(position);

        shirtsQ.setText(item.getShirtsQ());
        timestamp.setText(item.getTimeStamp());
        summ.setText(item.getSumm());
        statusOrder.setText(item.getStatus());

        if (checkedPositions.get(position) != null){
            background.setBackgroundColor(activity.getResources().getColor(R.color.checked_item));
        } else {
            background.setBackgroundColor(activity.getResources().getColor(R.color.ab_background));
        }

        return convertView;
    }

    public void setPositionToBackground(ArrayList<Integer> positionList){
        this.positionList = positionList;
    }

    public void removeItemsById(ArrayList<Integer> idList){
        Iterator<OrderStatusItem> it = orderItems.iterator();
        while(it.hasNext()) {
            OrderStatusItem fe = it.next();
            for (int i = 0; i < idList.size(); i++) {
                if (fe.getId() == idList.get(i)) {
                    it.remove();
                    break;
                }
            }
        }
    }
}
