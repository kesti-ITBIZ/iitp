package kr.co.kesti.iitp.projection;

public interface ObserverDataProjection {
    String getDatetime();
    String getStnNm();
    Float getTemperature();
    Float getHumidity();
    Float getPressure();
    Float getPm25();
}
