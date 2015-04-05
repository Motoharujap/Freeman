package com.motoharu.cleaningapp;

import Database.DBHelper;
import Database.DBOperationCreator;
import ObjectModel.User;
import adapter.OrderStatusAdapter;
import core.Freeman;
import data.*;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class HomeFragment extends Fragment implements OnRefreshListener {
    //Context menu items
    private static final int DELETE_CONTEXT_ITEM = 10;


    private ListView listView;
    private OrderStatusAdapter listAdapter;
    public List<OrderStatusItem> feedItems;
    private String xmlUrl = "http://syarkov.wix.com/stevia/feed.xml";
    private PullToRefreshLayout mPullToRefreshLayout;
    private DBHelper mDBhelper;
    private DBOperationCreator dboc;
    private User user;
    long userId;
    private ActionMode mActionMode;
    public int itemPosition;
    private ArrayList<Integer> idList = new ArrayList<Integer>();
    private ArrayList<Integer> idListforDB = new ArrayList<Integer>();

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onStart() {
        initControls();
        super.onStart();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    @SuppressLint("NewApi")
    public void initControls() {
        dboc = new DBOperationCreator(getActivity().getApplicationContext());
        mDBhelper = new DBHelper(getActivity().getApplicationContext());
        user = User.getInstance();
        listView = (ListView) getActivity().findViewById(R.id.list);
        feedItems = dboc.getItems(user.getuserId());
        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listView.setItemChecked(listView.getCheckedItemPosition(), isChecked);
            }
        };
        listAdapter = new OrderStatusAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                //horrible workaround which sets background color of the current item properly
                listAdapter.setPositionToBackground(idList);
                if (checked) {
                    listAdapter.setNewSelection(position, checked);
                } else {
                    listAdapter.removeSelection(position);
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                menu.add(0, DELETE_CONTEXT_ITEM, 0, "Delete")
                        .setIcon(getResources().getDrawable(android.R.drawable.ic_menu_delete))
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case DELETE_CONTEXT_ITEM: {
                        Set<Integer> dbOrder = listAdapter.getKeySet();
                        ArrayList<Integer> orderID = new ArrayList<Integer>(dbOrder);
                        for (int i : orderID){
                            int position = feedItems.get(i).getId();
                            idListforDB.add(position);
                        }
                        mDBhelper.deleteOrdersById(idListforDB);
                        listAdapter.removeItemsById(idListforDB);
                        listAdapter.clearSelection();
                        listAdapter.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    }
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listAdapter.clearSelection();
                listAdapter.notifyDataSetChanged();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), OrderDetails.class);
                int orderID = listAdapter.getOrderId(position);
                intent.putExtra(DBHelper.KEY_CORDER_ID, orderID);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setItemChecked(position, !listAdapter.isPositionChecked(position));
                return false;
            }
        });
        listAdapter.notifyDataSetChanged();
        mPullToRefreshLayout = (PullToRefreshLayout) getActivity().findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(this)
                .setup(mPullToRefreshLayout);

        // We first check for cached request
        Cache cache = Freeman.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(xmlUrl);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    xmlUrl, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d("Some volley error", "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Some volley error", "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            Freeman.getInstance().addToRequestQueue(jsonReq);
        }
        /*
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    ItemListReturner irl = new ItemListReturner(xmlUrl);
                    ArrayList<SAXGettersSetters> list = irl.getItemList(data);
                    Log.d("response", data);
                    for (int i = 0; i < list.size(); i++) {
                        SAXGettersSetters item = list.get(i);
                        FeedItem fi = new FeedItem();
                        fi.setName(item.getTitle());
                        fi.setStatus(item.getDescription());
                        fi.setTimeStamp(item.getDate());
                        feedItems.add(fi);
                        listAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // making fresh volley request and getting xml
            StringRequest request = new StringRequest(Request.Method.GET, xmlUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // converting the String response to XML
                            ItemListReturner irl = new ItemListReturner(xmlUrl);
                            ArrayList<SAXGettersSetters> list = irl.getItemList(response);
                            Log.d("response", response);
                            for (int i = 0; i < list.size(); i++) {
                                SAXGettersSetters item = list.get(i);
                                OrderStatusItem fi = new OrderStatusItem();
                                fi.setShirtsQ(item.get);
                                feedItems.add(fi);
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("NEW WAY TO PARSE", error.toString());
                        }
                    }
            );
            AppControler.getInstance().addToRequestQueue(request);
        }
          */
    }


    @Override
    public void onRefreshStarted(View view) {
            listAdapter.notifyDataSetChanged();
    }
    /*
    public void test(){
        StringRequest request = new StringRequest(Request.Method.GET, xmlUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        feedItems.clear();
                        ItemListReturner irl = new ItemListReturner(xmlUrl);
                        ArrayList<SAXGettersSetters> list = irl.getItemList(response);
                        for (int i = 0; i < list.size(); i++) {
                                SAXGettersSetters item = list.get(i);
                                FeedItem fi = new FeedItem();
                                fi.setName(item.getTitle());
                                fi.setStatus(item.getDescription());
                                fi.setTimeStamp(item.getDate());
                                feedItems.add(fi);
                                listAdapter.notifyDataSetChanged();
                                if (list.size() - i == 1) {
                                    mPullToRefreshLayout.setRefreshComplete();
                                }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY ERROR", error.toString());
                        mPullToRefreshLayout.setRefreshComplete();
                        Toast.makeText(getActivity().getApplicationContext(), "Проверьте подключение к интернету", Toast.LENGTH_LONG).show();
                    }
                }
        );
        AppControler.getInstance().addToRequestQueue(request);
    }
    */
    /**
 * Parsing json reponse and passing the data to feed view list adapter
 * */
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");
                 for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);
                    OrderStatusItem item = new OrderStatusItem();
                    item.setId(feedObj.getInt("id"));
                    item.setShirtsQ(feedObj.getString("shirtsQ"));
                    item.setStatus(feedObj.getString("status"));
                    item.setSumm(feedObj.getString("summ"));
                    item.setTimeStamp(feedObj.getString("timeStamp"));
                    feedItems.add(item);
        }
        // notify data changes to list adapater
        listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
        e.printStackTrace();
             }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
        }

}


