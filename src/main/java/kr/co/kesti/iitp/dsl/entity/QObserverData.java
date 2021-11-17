package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import kr.co.kesti.iitp.dsl.embed.QObserverDataKey;
import kr.co.kesti.iitp.entity.ObserverData;


/**
 * QObserverData is a Querydsl query type for ObserverData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QObserverData extends EntityPathBase<ObserverData> {

    private static final long serialVersionUID = -343024396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObserverData observerData = new QObserverData("observerData");

    public final NumberPath<Float> humidity = createNumber("humidity", Float.class);

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final QObserverDataKey observerDataKey;

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final NumberPath<Float> pm25_R = createNumber("pm25_R", Float.class);

    public final NumberPath<Float> pressure = createNumber("pressure", Float.class);

    public final NumberPath<Float> pty = createNumber("pty", Float.class);

    public final StringPath ptyR = createString("ptyR");

    public final StringPath qcHumidity = createString("qcHumidity");

    public final StringPath qcPm25 = createString("qcPm25");

    public final StringPath qcPressure = createString("qcPressure");

    public final StringPath qcTemperature = createString("qcTemperature");

    public final NumberPath<Float> temperature = createNumber("temperature", Float.class);

    public QObserverData(String variable) {
        this(ObserverData.class, forVariable(variable), INITS);
    }

    public QObserverData(Path<? extends ObserverData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObserverData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObserverData(PathMetadata metadata, PathInits inits) {
        this(ObserverData.class, metadata, inits);
    }

    public QObserverData(Class<? extends ObserverData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.observerDataKey = inits.isInitialized("observerDataKey") ? new QObserverDataKey(forProperty("observerDataKey")) : null;
    }

}

