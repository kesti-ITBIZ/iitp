package kr.co.kesti.iitp.projection;

public interface StationProjection {
    String getCategory();
    Float getLatitude();
    Float getLongitude();
    String getStnId();
    String getName();
    String getAddress();
    Float getPm25();
}
