package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.entity.KTStation;


/**
 * QKTStation is a Querydsl query type for KTStation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKTStation extends EntityPathBase<KTStation> {

    private static final long serialVersionUID = 729142999L;

    public static final QKTStation kTStation = new QKTStation("kTStation");

    public final StringPath address = createString("address");

    public final StringPath devId = createString("devId");

    public final StringPath devNm = createString("devNm");

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath newAddress = createString("newAddress");

    public final StringPath registTime = createString("registTime");

    public QKTStation(String variable) {
        super(KTStation.class, forVariable(variable));
    }

    public QKTStation(Path<? extends KTStation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKTStation(PathMetadata metadata) {
        super(KTStation.class, metadata);
    }

}

