package fpoly.phongndtph56750.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpoly.phongndtph56750.duanmau.R;
import fpoly.phongndtph56750.duanmau.fragment_thanh_vien;
import fpoly.phongndtph56750.duanmau.model.ThanhVien;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    private fragment_thanh_vien fragment;
    private ArrayList<ThanhVien> list;
    TextView tvMaTV, tvTenTV, tvNamSinh;
    ImageView imgDel;
    LinearLayout layoutItem;

    public ThanhVienAdapter(@NonNull Context context, fragment_thanh_vien fragment, ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_thanh_vien, null);
        }
        final ThanhVien item = list.get(position);
        if (item != null) {
            tvMaTV = v.findViewById(R.id.tvMaTV);
            tvMaTV.setText("Mã: " + item.getMaTV());

            tvTenTV = v.findViewById(R.id.tvTenTV);
            tvTenTV.setText("Tên: " + item.getHoTen());

            tvNamSinh = v.findViewById(R.id.tvNamSinh);
            tvNamSinh.setText("Năm sinh: " + item.getNamSinh());

            imgDel = v.findViewById(R.id.imgDeleteLS);
            layoutItem = v.findViewById(R.id.layoutitem);

            imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.xoa(String.valueOf(item.getMaTV()));
                }
            });

            // Khi click vào item → mở dialog sửa thành viên
            layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.openEditDialog(context, item);  // Gọi hàm mở dialog sửa
                }
            });
        }
        return v;
    }
}
