package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.entity.ObserverStation;


/**
 * QObserverStation is a Querydsl query type for ObserverStation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QObserverStation extends EntityPathBase<ObserverStation> {

    private static final long serialVersionUID = -357853270L;

    public static final QObserverStation observerStation = new QObserverStation("observerStation");

    public final StringPath address = createString("address");

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath registTime = createString("registTime");

    public final StringPath stnNm = createString("stnNm");

    public final StringPath stnSerial = createString("stnSerial");

    public QObserverStation(String variable) {
        super(ObserverStation.class, forVariable(variable));
    }

    public QObserverStation(Path<? extends ObserverStation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObserverStation(PathMetadata metadata) {
        super(ObserverStation.class, metadata);
    }

}

