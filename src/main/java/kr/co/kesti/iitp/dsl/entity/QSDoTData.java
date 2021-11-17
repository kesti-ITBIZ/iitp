package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import kr.co.kesti.iitp.dsl.embed.QSDoTDataKey;
import kr.co.kesti.iitp.entity.SDoTData;


/**
 * QSDoTData is a Querydsl query type for SDoTData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSDoTData extends EntityPathBase<SDoTData> {

    private static final long serialVersionUID = 1229862868L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSDoTData sDoTData = new QSDoTData("sDoTData");

    public final NumberPath<Float> bbtemp = createNumber("bbtemp", Float.class);

    public final NumberPath<Short> dataNo = createNumber("dataNo", Short.class);

    public final NumberPath<Float> gwDir = createNumber("gwDir", Float.class);

    public final NumberPath<Float> gwSpd = createNumber("gwSpd", Float.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Float> lux = createNumber("lux", Float.class);

    public final NumberPath<Float> maxvibX = createNumber("maxvibX", Float.class);

    public final NumberPath<Float> maxvibY = createNumber("maxvibY", Float.class);

    public final NumberPath<Float> maxvibZ = createNumber("maxvibZ", Float.class);

    public final StringPath modelNm = createString("modelNm");

    public final NumberPath<Float> noise = createNumber("noise", Float.class);

    public final StringPath organNm = createString("organNm");

    public final NumberPath<Float> pm10 = createNumber("pm10", Float.class);

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final NumberPath<Float> relativeHumidity = createNumber("relativeHumidity", Float.class);

    public final NumberPath<Float> rPm10 = createNumber("rPm10", Float.class);

    public final NumberPath<Float> rPm25 = createNumber("rPm25", Float.class);

    public final StringPath runTime = createString("runTime");

    public final QSDoTDataKey sDoTDataKey;

    public final NumberPath<Float> temperature = createNumber("temperature", Float.class);

    public final NumberPath<Short> transmitServerNo = createNumber("transmitServerNo", Short.class);

    public final NumberPath<Float> uv = createNumber("uv", Float.class);

    public final NumberPath<Float> vibX = createNumber("vibX", Float.class);

    public final NumberPath<Float> vibY = createNumber("vibY", Float.class);

    public final NumberPath<Float> vibZ = createNumber("vibZ", Float.class);

    public final NumberPath<Float> windDirection = createNumber("windDirection", Float.class);

    public final NumberPath<Float> windSpeed = createNumber("windSpeed", Float.class);

    public QSDoTData(String variable) {
        this(SDoTData.class, forVariable(variable), INITS);
    }

    public QSDoTData(Path<? extends SDoTData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSDoTData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSDoTData(PathMetadata metadata, PathInits inits) {
        this(SDoTData.class, metadata, inits);
    }

    public QSDoTData(Class<? extends SDoTData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.sDoTDataKey = inits.isInitialized("sDoTDataKey") ? new QSDoTDataKey(forProperty("sDoTDataKey")) : null;
    }

}

