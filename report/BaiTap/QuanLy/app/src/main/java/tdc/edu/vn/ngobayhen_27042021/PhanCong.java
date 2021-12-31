package tdc.edu.vn.ngobayhen_27042021;

import java.util.Date;

public class PhanCong {
    public String MaNV,MaDA;
    public int TuNgay,DeNgay;
    public PhanCong(String maNV, String maDA, int tuNgay, int deNgay) {
        MaNV = maNV;
        MaDA = maDA;
        TuNgay = tuNgay;
        DeNgay = deNgay;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMaDA() {
        return MaDA;
    }

    public void setMaDA(String maDA) {
        MaDA = maDA;
    }

    public int getTuNgay() {
        return TuNgay;
    }

    public void setTuNgay(int tuNgay) {
        TuNgay = tuNgay;
    }

    public int getDeNgay() {
        return DeNgay;
    }

    public void setDeNgay(int deNgay) {
        DeNgay = deNgay;
    }

    public PhanCong(){

    }

}
