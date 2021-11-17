package kr.co.kesti.iitp.projection;

public interface KTDataProjection {
    String getDatetime();
    String getStnNm();
    Float getTemperature();
    Float getHumidity();
    Float getPm10();
    Float getPm25();
}
