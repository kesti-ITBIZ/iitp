/* AIRKOREA 측정시간별 데이터 */
SELECT
    TO_CHAR(TO_TIMESTAMP(a.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD') AS datetime,
    a.stn_nm,
    a.mang_nm,
    a.sido_nm,
    AVG(a.so2) AS so2,
    AVG(a.co) AS co,
    AVG(a.o3) AS o3,
    AVG(a.no2) AS no2,
    AVG(a.pm10) AS pm10,
    AVG(a.pm25) AS pm25
FROM "TB_AIRKOREA_DATA_RAW" a
JOIN "TB_AIRKOREA_STN_RAW" b
ON a.stn_nm = b.stn_nm
WHERE a.time
    BETWEEN '2021-11-11 23:00'
    AND '2021-11-15 07:00'
AND a.stn_nm = '외연도'
AND a.pm10 BETWEEN 31 AND 80
AND a.pm25 BETWEEN 16 AND 35
GROUP BY
    TO_CHAR(TO_TIMESTAMP(a.time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDD'),
    a.stn_nm,
    a.mang_nm,
    a.sido_nm;

/* AIRKOREA 측정시간 외 항목별 데이터 */
SELECT
    a.stn_nm,
    a.mang_nm,
    a.sido_nm,
    a.so2,
    a.co,
    a.o3,
    a.no2,
    a.pm10,
    a.pm25
FROM "TB_AIRKOREA_DATA_RAW" a
JOIN "TB_AIRKOREA_STN_RAW" b
ON a.stn_nm = b.stn_nm
WHERE a.time
    BETWEEN '2021-11-11 23:00'
    AND '2021-11-15 07:00'
AND a.stn_nm = '외연도'
AND a.pm10 BETWEEN 31 AND 80
AND a.pm25 BETWEEN 16 AND 35;

/* KT 측정시간별 데이터 */
SELECT
    a.equip_date,
    b.dev_nm,
    AVG(a.temp) AS temp,
    AVG(a.hum) AS hum,
    AVG(a.pm10) AS pm10,
    AVG(a.pm25) AS pm25
FROM "TB_KT_DATA_RAW" a
JOIN "TB_KT_STN_RAW" b
ON a.dev_id = b.dev_id
WHERE a.equip_date
    BETWEEN '2018-07-01 00:00:00'
    AND '2018-09-01 00:00:00'
AND b.dev_nm = '장대동 월드컵경기장8문앞'
AND a.pm10 BETWEEN 31 AND 80
AND a.pm25 BETWEEN 16 AND 35;

/* KT 측정시간 외 항목별 데이터 */
SELECT
    b.dev_nm,
    a.temp,
    a.hum,
    a.pm10,
    a.pm25
FROM "TB_KT_DATA_RAW" a
JOIN "TB_KT_STN_RAW" b
ON a.dev_id = b.dev_id
WHERE a.equip_date
    BETWEEN '2018-07-01 00:00:00'
    AND '2018-09-01 00:00:00'
AND b.dev_nm = '장대동 월드컵경기장8문앞'
AND a.pm10 BETWEEN 31 AND 80
AND a.pm25 BETWEEN 16 AND 35;

/* S-DoT 측정시간별 데이터 */
SELECT
    a.regist_time,
    a.organ_nm,
    a.model_nm,
    a.model_sr,
    AVG(a.div) AS div,
    AVG(a.temp) AS temp,
    AVG(a.rhd) AS rhd,
    AVG(a.w_dir) AS w_dir,
    AVG(a.w_spd) AS w_spd,
    AVG(a.pm10) AS pm10,
    AVG(a.pm25) AS pm25
FROM "TB_SDOT_DATA_RAW" a
JOIN "TB_SDOT_STN_RAW" b
ON a.model_sr = b.stn_id
WHERE a.regist_time
    BETWEEN '2021-10-25 01:00'
    AND '2021-10-28 08:00'
AND a.model_sr = 'V02Q1940786'
AND a.div = 1
AND a.pm10 BETWEEN 31 AND 80
AND a.pm25 BETWEEN 16 AND 35
GROUP BY
    a.regist_time,
    a.organ_nm,
    a.model_nm,
    a.model_sr;

/* S-DoT 측정시간 외 항목별 데이터 */
SELECT
    a.regist_time,
    a.organ_nm,
    a.model_nm,
    a.model_sr,
    a.div,
    a.temp,
    a.rhd,
    a.w_dir,
    a.w_spd,
    a.pm10,
    a.pm25
FROM "TB_SDOT_DATA_RAW" a
JOIN "TB_SDOT_STN_RAW" b
ON a.model_sr = b.stn_id
WHERE a.regist_time
    BETWEEN '2021-10-25 01:00'
    AND '2021-10-28 08:00'
AND a.model_sr = 'V02Q1940786'
AND a.div = 1
AND a.pm10 BETWEEN 31 AND 80
AND a.pm25 BETWEEN 16 AND 35;

/* OBSERVER 측정시간별 데이터 */
SELECT
    CONCAT(TO_CHAR(data_time, 'YYYY-MM-DD'), ' ', TO_CHAR(data_time, 'HH24:MI:SS')) AS datetime,
    a.stn_serial,
    b.stn_nm,
    AVG(a.temp) AS temp,
    AVG(a.hum) AS hum,
    AVG(a.press) AS press,
    AVG(a.pm25) AS pm25
FROM "TB_OBSERVER_DATA_RAW" a
JOIN "TB_OBSERVER_STN_RAW" b
ON a.stn_serial = b.stn_serial
WHERE a.data_time
    BETWEEN '2020-10-30 00:08:01'
    AND '2020-10-30 02:21:44'
AND b.stn_nm = 'GSN-3'
AND a.pm25 BETWEEN 16 AND 35
GROUP BY
    CONCAT(TO_CHAR(data_time, 'YYYY-MM-DD'), ' ', TO_CHAR(data_time, 'HH24:MI:SS')),
    a.stn_serial,
    b.stn_nm;

/* OBSERVER 측정시간 외 항목별 데이터 */
SELECT
    a.stn_serial,
    b.stn_nm,
    a.temp,
    a.hum,
    a.press,
    a.pm25
FROM "TB_OBSERVER_DATA_RAW" a
JOIN "TB_OBSERVER_STN_RAW" b
ON a.stn_serial = b.stn_serial
WHERE a.data_time
    BETWEEN '2020-10-30 00:08:01'
    AND '2020-10-30 02:21:44'
AND b.stn_nm = 'GSN-3'
AND a.pm25 BETWEEN 16 AND 35;

/* 고농도일수 통계 데이터 */
SELECT * FROM "TB_DNSTY_DATA_SUMMARY"
WHERE category = 'KT'
AND (
    stcs_time
        BETWEEN '201807'
        AND '201912'
    OR stcs_time = SUBSTR('201807', 1, 4))
AND stn_nm = '송당마을';

/* AIRKOREA-KT 비교 및 상관분석 데이터 */
SELECT * FROM (
    SELECT
        TO_CHAR(TO_TIMESTAMP(a."time", 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS std_datetime,
        a.stn_nm AS std_stn_id,
        a.stn_nm AS std_stn_nm,
        a.pm10 AS std_pm10,
        a.pm25 AS std_pm25
    FROM "TB_AIRKOREA_DATA_RAW" a
    WHERE a."time"
        BETWEEN '2019-09-29 15:00'
        AND '2019-10-11 10:00'
    AND a.stn_nm = '덕적도') c
JOIN (
    SELECT
        TO_CHAR(a.equip_date, 'YYYYMMDDHH24') AS comp_datetime,
        a.dev_id AS comp_stn_id,
        b.dev_nm AS comp_stn_nm,
        a.pm10 AS comp_pm10,
        a.pm25 AS comp_pm25
    FROM "TB_KT_DATA_RAW" a
    JOIN "TB_KT_STN_RAW" b
    ON a.dev_id = b.dev_id
    WHERE a.equip_date
        BETWEEN '2019-09-29 15:00:00'
        AND '2019-10-11 10:00:00'
    AND a.dev_id = 'V01O1621322') d
ON c.std_datetime = d.comp_datetime;

/* AIRKOREA-SDOT 비교 및 상관분석 데이터 */
SELECT * FROM (
    SELECT
        TO_CHAR(TO_TIMESTAMP(a."time", 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS std_datetime,
        a.stn_nm AS std_stn_id,
        a.stn_nm AS std_stn_nm,
        a.pm10 AS std_pm10,
        a.pm25 AS std_pm25
    FROM "TB_AIRKOREA_DATA_RAW" a
    WHERE a."time"
        BETWEEN '2021-11-01 00:00'
        AND '2021-12-10 05:00'
    AND a.stn_nm = '덕적도') c
JOIN (
    SELECT
        TO_CHAR(TO_TIMESTAMP(a.regist_time, 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS comp_datetime,
        a.model_sr AS comp_stn_id,
        b.addr AS comp_stn_nm,
        a.pm10 AS comp_pm10,
        a.pm25 AS comp_pm25
    FROM "TB_SDOT_DATA_RAW" a
    JOIN "TB_SDOT_STN_RAW" b
    ON a.model_sr = b.stn_id
    WHERE a.regist_time
        BETWEEN '2021-11-01 00:00'
        AND '2021-12-10 05:00'
    AND a.model_sr = 'OC3CL200046') d
ON c.std_datetime = d.comp_datetime;

/* AIRKOREA-OBSERVER 비교 및 상관분석 데이터 */
SELECT * FROM (
    SELECT
        TO_CHAR(TO_TIMESTAMP(a."time", 'YYYY-MM-DD HH24:MI'), 'YYYYMMDDHH24') AS std_datetime,
        a.stn_nm AS std_stn_id,
        a.stn_nm AS std_stn_nm,
        a.pm10 AS std_pm10,
        a.pm25 AS std_pm25
    FROM "TB_AIRKOREA_DATA_RAW" a
    WHERE a."time"
        BETWEEN '2021-05-26 00:00:00'
        AND '2021-05-29 05:00:00'
    AND a.stn_nm = '덕적도') c
JOIN (
    SELECT
        TO_CHAR(a.data_time, 'YYYYMMDDHH24') AS comp_datetime,
        a.stn_serial AS comp_stn_id,
        b.stn_nm AS comp_stn_nm,
        NULL AS comp_pm10,
        a.pm25 AS comp_pm25
    FROM "TB_OBSERVER_DATA_RAW" a
    JOIN "TB_OBSERVER_STN_RAW" b
    ON a.stn_serial = b.stn_serial
    WHERE a.data_time
        BETWEEN '2021-05-26 00:00:00'
        AND '2021-05-29 05:00:00'
    AND a.stn_serial = 'OC3CL200046') d
ON c.std_datetime = d.comp_datetime
