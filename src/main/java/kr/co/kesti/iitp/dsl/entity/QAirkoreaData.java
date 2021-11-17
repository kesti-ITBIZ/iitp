package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import kr.co.kesti.iitp.dsl.embed.QAirkoreaDataKey;
import kr.co.kesti.iitp.entity.AirkoreaData;


/**
 * QAirkoreaData is a Querydsl query type for AirkoreaData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAirkoreaData extends EntityPathBase<AirkoreaData> {

    private static final long serialVersionUID = 188537534L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAirkoreaData airkoreaData = new QAirkoreaData("airkoreaData");

    public final QAirkoreaDataKey airkoreaDataKey;

    public final NumberPath<Float> co = createNumber("co", Float.class);

    public final StringPath coFlag = createString("coFlag");

    public final NumberPath<Short> coGrd = createNumber("coGrd", Short.class);

    public final NumberPath<Float> khai = createNumber("khai", Float.class);

    public final NumberPath<Short> khaiGrd = createNumber("khaiGrd", Short.class);

    public final StringPath mangNm = createString("mangNm");

    public final NumberPath<Float> no2 = createNumber("no2", Float.class);

    public final StringPath no2_Flag = createString("no2_Flag");

    public final NumberPath<Short> no2_Grd = createNumber("no2_Grd", Short.class);

    public final NumberPath<Float> o3 = createNumber("o3", Float.class);

    public final StringPath o3_Flag = createString("o3_Flag");

    public final NumberPath<Short> o3_Grd = createNumber("o3_Grd", Short.class);

    public final NumberPath<Float> pm10 = createNumber("pm10", Float.class);

    public final NumberPath<Float> pm10_24h = createNumber("pm10_24h", Float.class);

    public final StringPath pm10_Flag = createString("pm10_Flag");

    public final NumberPath<Short> pm10_Grd = createNumber("pm10_Grd", Short.class);

    public final NumberPath<Short> pm10_Grd1h = createNumber("pm10_Grd1h", Short.class);

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final NumberPath<Float> pm25_24h = createNumber("pm25_24h", Float.class);

    public final StringPath pm25_Flag = createString("pm25_Flag");

    public final NumberPath<Short> pm25_Grd = createNumber("pm25_Grd", Short.class);

    public final NumberPath<Short> pm25_Grd1h = createNumber("pm25_Grd1h", Short.class);

    public final StringPath runTime = createString("runTime");

    public final StringPath sidoNm = createString("sidoNm");

    public final NumberPath<Float> so2 = createNumber("so2", Float.class);

    public final StringPath so2_Flag = createString("so2_Flag");

    public final NumberPath<Short> so2Grd = createNumber("so2Grd", Short.class);

    public QAirkoreaData(String variable) {
        this(AirkoreaData.class, forVariable(variable), INITS);
    }

    public QAirkoreaData(Path<? extends AirkoreaData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAirkoreaData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAirkoreaData(PathMetadata metadata, PathInits inits) {
        this(AirkoreaData.class, metadata, inits);
    }

    public QAirkoreaData(Class<? extends AirkoreaData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.airkoreaDataKey = inits.isInitialized("airkoreaDataKey") ? new QAirkoreaDataKey(forProperty("airkoreaDataKey")) : null;
    }

}

