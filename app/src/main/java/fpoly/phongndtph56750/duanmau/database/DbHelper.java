package fpoly.phongndtph56750.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "PNLIb";
    public static final int DB_VERSION = 10;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // tạo bảng thủ thư
        String createTableThuThu = "create table ThuThu(" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL)";
        db.execSQL(createTableThuThu);
        // tạo bảng thành viên
        String createTableThanhVien = "create table ThanhVien(" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);
        // tạo bảng Loại Sách
        String createTableLoaiSach = "create table LoaiSach(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableLoaiSach);
        // tạo bảng Sách
        String createTableSach = "create table Sach(" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT NOT NULL, " +
                "giaThue INTEGER NOT NULL, " +
                "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        // tạo bảng Phiếu Mượn
        String createTablePhieuMuon = "create table PhieuMuon(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maTT TEXT REFERENCES ThuThu(maTT), " +
                "maTV INTEGER REFERENCES ThanhVien(maTV), " +
                "maSach INTEGER REFERENCES Sach(maSach), " +
                "tienThue INTEGER NOT NULL, " +
                "ngay DATE NOT NULL, " +
                "traSach INTEGER NOT NULL)";
        db.execSQL(createTablePhieuMuon);
        // data mẫu
        db.execSQL("INSERT INTO ThuThu VALUES('admin','Admin','1')," +
                "('admin2','phong','a')");
        db.execSQL("INSERT INTO ThanhVien VALUES(1,'Nguyễn Hoàng Long','2004')," +
                "(2,'Trần Duy Quang','2004')");
        db.execSQL("INSERT INTO LoaiSach VALUES(1,'Thiếu nhi'),(2,'Tình cảm'),(3,'Giáo khoa'),(4,'Sách ngoại')");
        db.execSQL("INSERT INTO Sach VALUES(1,'Bao lâu mới giàu',2500,'4'),(2,'Vua hải tặc',4000,'1'),(3,'Tiếng Anh 12',3000,'3')");
        db.execSQL("INSERT INTO PhieuMuon VALUES(1,'admin','1','1','2000','2023/09/20','1')," +
                "(2,'nhong','2','2','2000','2023/09/23','1')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("drop table if exists ThuThu");
            db.execSQL("drop table if exists ThanhVien");
            db.execSQL("drop table if exists LoaiSach");
            db.execSQL("drop table if exists Sach");
            db.execSQL("drop table if exists PhieuMuon");
            onCreate(db);
        }
    }
}
