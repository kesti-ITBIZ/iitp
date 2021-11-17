package kr.co.kesti.iitp.projection;

public interface SDoTDataProjection {
    String getDatetime();
    String getStnNm();
    Float getTemperature();
    Float getHumidity();
    Float getWindDirection();
    Float getWindSpeed();
    Float getPm10();
    Float getPm25();
}
