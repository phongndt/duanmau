package fpoly.phongndtph56750.duanmau;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import fpoly.phongndtph56750.duanmau.DAO.PhieuMuonDAO;
import fpoly.phongndtph56750.duanmau.DAO.ThongKeDAO;
import fpoly.phongndtph56750.duanmau.adapter.TopAdapter;
import fpoly.phongndtph56750.duanmau.model.Top;


public class fragment_top extends Fragment {
    ListView lvTop;
    ArrayList<Top> list;
    TopAdapter adapter;
    ThongKeDAO dao;


    public fragment_top() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        lvTop = v.findViewById(R.id.lvTop);
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getActivity());
        list = (ArrayList<Top>) phieuMuonDAO.getTop();
        adapter = new TopAdapter(getActivity(),this,list);
        lvTop.setAdapter(adapter);
        return v;
    }
}