package tdc.edu.vn.ngobayhen_27042021;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView lvPhanCong;
    EditText edtMNV, edtMDA, edtTN, edtDN;
    Button btnThem, btnSua, btnXoa, btnThoat;
    ArrayList<PhanCong> data = new ArrayList<>();
    PhanCong pc = new PhanCong();
    MyRecyclerViewAdapter adapter;
    int idrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        data.add(new PhanCong("123", "DeAn1", 1, 2));
        data.add(new PhanCong("124", "DeAn2", 1, 2));
        data.add(new PhanCong("125", "DeAn3", 1, 2));
        data.add(new PhanCong("126", "DeAn4", 1, 2));
        // adapter = new CustomAdapter(this, R.layout.listview_main, data);
        adapter = new MyRecyclerViewAdapter(this, R.layout.listview_main, data);
        lvPhanCong.setAdapter(adapter);
//        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        lvPhanCong.setLayoutManager(layoutManager);

        adapter.setDelegation(new MyRecyclerViewAdapter.MyItemClickListenner() {
            @Override
            public void getPermisstion(PhanCong phanCong) {
                Toast.makeText(MainActivity.this, "toast", Toast.LENGTH_SHORT).show();
            }
        });






//        lvPhanCong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                edtMNV.setText(data.get(position).getMaNV());
//                edtMDA.setText(data.get(position).getMaDA());
//                edtTN.setText(data.get(position).getTuNgay() + "");
//                edtDN.setText(data.get(position).getDeNgay() + "");
//                pc = data.get(position);
//                idrm = position;
//            }
//        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = edtMNV.getText().toString();
                String maDA = edtMDA.getText().toString();
                int tNgay = Integer.parseInt(edtTN.getText().toString());
                int dNgay = Integer.parseInt(edtDN.getText().toString());
                PhanCong pc = new PhanCong(maNV, maDA, tNgay, dNgay);
                data.add(pc);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Thêm Thành Công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMNV.setText("");
                edtMDA.setText("");
                edtTN.setText("");
                edtDN.setText("");
                data.remove(idrm);
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
            }
        });
        //Nhấn để xóa
//        lvPhanCong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                data.remove(position);
//                adapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhanCong pc = data.get(idrm);
                pc.setDeNgay(Integer.parseInt(edtDN.getText().toString()));
                pc.setTuNgay(Integer.parseInt(edtTN.getText().toString()));
                pc.setMaDA(edtMDA.getText().toString());
                pc.setMaNV(edtMNV.getText().toString());

                edtMNV.setText("");
                edtMDA.setText("");
                edtTN.setText("");
                edtDN.setText("");
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Sửa Thành Công!", Toast.LENGTH_SHORT).show();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void setControl() {
        edtMNV = findViewById(R.id.edtMNV);
        edtMDA = findViewById(R.id.edtMDA);
        edtTN = findViewById(R.id.edtTN);
        edtDN = findViewById(R.id.edtDN);
        btnSua = findViewById(R.id.btnSua);
        btnThem = findViewById(R.id.btnThem);
        lvPhanCong = findViewById(R.id.lvPhanCong);
        btnXoa = findViewById(R.id.btnXoa);
        btnThoat = findViewById(R.id.btnThoat);


    }

}
