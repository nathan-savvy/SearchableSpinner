package com.toptoche.searchablespinnerlibrary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class SearchableListDialog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String ITEMS = "items";

    private FontArrayAdapter listAdapter;

    private ListView _listViewItems;

    private SearchableItem _searchableItem;

    private OnSearchTextChanged _onSearchTextChanged;

    private SearchView _searchView;

    private String _strTitle;

    private View _customTitle;

    private String _strPositiveButtonText;

    private float _positiveTextSize;

    private Typeface _positiveTypeFace;

    private Drawable _searchViewDrawable;

    private int _dividerColor;

    private int _positiveButtonTextColor;

    private Typeface _listTypeFace;

    private DialogInterface.OnClickListener _onClickListener;

    public SearchableListDialog() {

    }

    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new
                SearchableListDialog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        multiSelectExpandableFragment.setArguments(args);

        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        if (getDialog().isShowing()) {
            getDialog().hide();
        }
        super.onStart();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        // Crash on orientation change #7
        // Change Start
        // Description: As the instance was re initializing to null on rotating the device,
        // getting the instance from the saved instance
        if (null != savedInstanceState) {
            _searchableItem = (SearchableItem) savedInstanceState.getSerializable("item");
        }
        // Change End

        View rootView = inflater.inflate(R.layout.searchable_list_dialog, null);
        setData(rootView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strPositiveButton = _strPositiveButtonText == null ? "CLOSE" : _strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, _onClickListener);
        if (_customTitle == null) {
            String strTitle = _strTitle == null ? "Select Item" : _strTitle;
            alertDialog.setTitle(strTitle);
        } else {
            alertDialog.setCustomTitle(_customTitle);
        }

        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE);
                if (_positiveTextSize > 0) {
                    btnPositive.setTextSize(_positiveTextSize);
                }

                if (_positiveTypeFace != null) {
                    btnPositive.setTypeface(_positiveTypeFace);
                }

                if (_positiveButtonTextColor != 0) {
                    btnPositive.setTextColor(_positiveButtonTextColor);
                }

                if (_dividerColor != 0) {
                    int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
                    View divider = dialog.findViewById(dividerId);
                    if (divider != null) {
                        divider.setBackgroundColor(_dividerColor);
                    }
                }
            }
        });

        return dialog;
    }

    // Crash on orientation change #7
    // Change Start
    // Description: Saving the instance of searchable item instance.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", _searchableItem);
        super.onSaveInstanceState(outState);
    }
    // Change End

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setCustomTitle(View customTitle) {
        _customTitle = customTitle;
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {
        _strPositiveButtonText = strPositiveButtonText;
        _onClickListener = onClickListener;
    }

    public void setPositiveButtonTextSize(float textSize) {
        _positiveTextSize = textSize;
    }

    public void setPositiveButtonTypeface(Typeface typeface) {
        _positiveTypeFace = typeface;
    }

    public void setPositiveButtonTextColor(int color) {
        _positiveButtonTextColor = color;
    }

    public void setSearchViewBackground(Drawable drawable) {
        _searchViewDrawable = drawable;

        if (_searchViewDrawable != null && _searchView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            _searchView.setBackground(_searchViewDrawable);
        }
    }

    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    public void setDialogDividerColor(int color) {
        _dividerColor = color;
    }

    private void setData(View rootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);

        _searchView = (SearchView) rootView.findViewById(R.id.search);
        _searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName
                ()));
        _searchView.setIconifiedByDefault(false);
        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();
        if (_searchViewDrawable != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            _searchView.setBackground(_searchViewDrawable);
        }
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);


        List items = (List) getArguments().getSerializable(ITEMS);

        _listViewItems = (ListView) rootView.findViewById(R.id.listItems);

        //create the adapter by passing your ArrayList data
        listAdapter = new FontArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                items);
        if (_listTypeFace != null) {
            listAdapter.setAdapterTypeFace(_listTypeFace);
        }
        //attach the adapter to the list
        _listViewItems.setAdapter(listAdapter);

        _listViewItems.setTextFilterEnabled(true);

        _listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
                getDialog().dismiss();
            }
        });
    }

    public void setlistTypeFace(Typeface listTypeFace) {
        this._listTypeFace = listTypeFace;
    }

    private class FontArrayAdapter extends ArrayAdapter<String> {

        private Typeface _adapterTypeFace;

        public FontArrayAdapter(Context context, int resource) {
            super(context, resource);
        }

        public FontArrayAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        public FontArrayAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        public FontArrayAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public FontArrayAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        public FontArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public void setAdapterTypeFace(Typeface adapterTypeFace) {
            this._adapterTypeFace = adapterTypeFace;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = super.getView(position, convertView, parent);
            }
            TextView textView = (TextView)convertView;
            if (_adapterTypeFace != null) {
                textView.setTypeface(_adapterTypeFace);
            }
            textView.setText(getItem(position));
            return textView;
        }
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        _searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        listAdapter.filterData(s);
        if (TextUtils.isEmpty(s)) {
//                _listViewItems.clearTextFilter();
            ((ArrayAdapter) _listViewItems.getAdapter()).getFilter().filter(null);
        } else {
            ((ArrayAdapter) _listViewItems.getAdapter()).getFilter().filter(s);
        }
        if (null != _onSearchTextChanged) {
            _onSearchTextChanged.onSearchTextChanged(s);
        }
        return true;
    }

    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T item, int position);
    }

    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }
}
