package fpoly.phongndtph56750.duanmau;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.phongndtph56750.duanmau.DAO.ThanhVienDAO;
import fpoly.phongndtph56750.duanmau.adapter.ThanhVienAdapter;
import fpoly.phongndtph56750.duanmau.model.ThanhVien;

public class fragment_thanh_vien extends Fragment {
    ListView lvThanhVien;
    ArrayList<ThanhVien> list;
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    ThanhVien item;
    FloatingActionButton addThanhVien;

    Dialog dialog;
    EditText edMaTV, edTenTV, edNamSinh;
    Button btnSave, btnCancel;

    public fragment_thanh_vien() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lvThanhVien = v.findViewById(R.id.lvThanhVien);
        addThanhVien = v.findViewById(R.id.addThanhVien);
        dao = new ThanhVienDAO(getActivity());
        capNhatLv();

        addThanhVien.setOnClickListener(v1 -> openDialog(getActivity(), 0));

        return v;
    }

    void capNhatLv() {
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(), this, list);
        lvThanhVien.setAdapter(adapter);
    }

    public void xoa(final String Id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa thành viên");
        builder.setMessage("Bạn có muốn xóa thành viên này không?");
        builder.setCancelable(true);

        builder.setPositiveButton("Có", (dialog, which) -> {
            dao.delete(Id);
            capNhatLv();
            Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Không", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    public void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thanh_vien);
        edMaTV = dialog.findViewById(R.id.edMaTV);
        edTenTV = dialog.findViewById(R.id.edTenTV);
        edNamSinh = dialog.findViewById(R.id.edNamSinh);
        btnCancel = dialog.findViewById(R.id.btnCancelTV);
        btnSave = dialog.findViewById(R.id.btnSaveTV);

        edMaTV.setEnabled(false);
        if (type != 0) {
            edMaTV.setText(String.valueOf(item.getMaTV()));
            edTenTV.setText(item.getHoTen());
            edNamSinh.setText(item.getNamSinh());
        }
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSave.setOnClickListener(v -> {
            item = new ThanhVien();
            item.setHoTen(edTenTV.getText().toString());
            item.setNamSinh(edNamSinh.getText().toString());
            if (validate(edTenTV,edNamSinh) > 0) {
                if (type == 0) {
                    if (dao.insert(item) > 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Lỗi thêm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    item.setMaTV(Integer.parseInt(edMaTV.getText().toString()));
                    if (dao.update(item) > 0) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void openEditDialog(final Context context, ThanhVien tv) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sua_thanh_vien);

        EditText edSuaTenTV = dialog.findViewById(R.id.edSuaTenTV);
        EditText edSuaNamSinh = dialog.findViewById(R.id.edSuaNamSinh);
        Button btnSua = dialog.findViewById(R.id.btnSuaTV);
        Button btnHuy = dialog.findViewById(R.id.btnHuySuaTV);

        edSuaTenTV.setText(tv.getHoTen());
        edSuaNamSinh.setText(tv.getNamSinh());

        btnHuy.setOnClickListener(v -> dialog.dismiss());

        btnSua.setOnClickListener(v -> {
            tv.setHoTen(edSuaTenTV.getText().toString());
            tv.setNamSinh(edSuaNamSinh.getText().toString());

            if (validate(edSuaTenTV, edSuaNamSinh)>0) {
                if (dao.update(tv) > 0) {
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    capNhatLv();
                } else {
                    Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public int validate(EditText edTenTV, EditText edNamSinh) {
        int check = 1;
        if (edTenTV.getText().toString().trim().isEmpty() || edNamSinh.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

}
