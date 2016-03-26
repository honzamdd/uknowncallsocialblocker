package com.mddsummer.uknowncallrecognizer.fragment;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mddsummer.uknowncallrecognizer.R;
import com.mddsummer.uknowncallrecognizer.database.dao.DaoMsisdnHelper;
import com.mddsummer.uknowncallrecognizer.database.model.Msisdn;

import java.util.List;

/**
 * @author {@link "mailto:honzamusil@honzamusil.info" "Honza Musil"} on 26/3/2016
 */
public class MsisdnListFragment extends ListFragment {

    private static final String TAG = MsisdnListFragment.class.getSimpleName();

    private List<Msisdn> msisdnList;

    public static MsisdnListFragment newInstance() {
        MsisdnListFragment fragment = new MsisdnListFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MsisdnListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load data from db
        msisdnList = new DaoMsisdnHelper(getActivity()).getAll();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set empty text (if there's nothing to show in db)
        setEmptyText(getString(R.string.empty_list));

        // Set list adapter
        setListAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, msisdnList));

        // Delete msisdn from DB on click
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {

                final Msisdn msisdn = msisdnList.get(i);

                new AlertDialog.Builder(getActivity())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getString(R.string.deletedialog_title))
                    .setMessage(getString(R.string.deletedialog_content).replace("#MSISDN", msisdn.getMsisdn()))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            new DaoMsisdnHelper(getActivity()).delete(msisdn);
                            msisdnList.remove(msisdn);
                            dialog.dismiss();
                            getListView().invalidateViews();

                            // Invalidate doesn't show/hide emptyView
                            getListView().getEmptyView().setVisibility(getListAdapter().isEmpty() ? View.VISIBLE : View.INVISIBLE);
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
            }
        });
    }
}
