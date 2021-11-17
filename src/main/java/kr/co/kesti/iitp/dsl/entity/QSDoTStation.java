package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.entity.SDoTStation;


/**
 * QSDoTStation is a Querydsl query type for SDoTStation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSDoTStation extends EntityPathBase<SDoTStation> {

    private static final long serialVersionUID = -566570806L;

    public static final QSDoTStation sDoTStation = new QSDoTStation("sDoTStation");

    public final StringPath address = createString("address");

    public final NumberPath<Float> height = createNumber("height", Float.class);

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath stnId = createString("stnId");

    public final StringPath year = createString("year");

    public QSDoTStation(String variable) {
        super(SDoTStation.class, forVariable(variable));
    }

    public QSDoTStation(Path<? extends SDoTStation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSDoTStation(PathMetadata metadata) {
        super(SDoTStation.class, metadata);
    }

}

