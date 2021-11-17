package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.entity.AirkoreaStation;


/**
 * QAirkoreaStation is a Querydsl query type for AirkoreaStation
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAirkoreaStation extends EntityPathBase<AirkoreaStation> {

    private static final long serialVersionUID = -140816992L;

    public static final QAirkoreaStation airkoreaStation = new QAirkoreaStation("airkoreaStation");

    public final StringPath addr = createString("addr");

    public final StringPath item = createString("item");

    public final NumberPath<Float> latitude = createNumber("latitude", Float.class);

    public final NumberPath<Float> longitude = createNumber("longitude", Float.class);

    public final StringPath mangNm = createString("mangNm");

    public final StringPath runTime = createString("runTime");

    public final StringPath stnNm = createString("stnNm");

    public final StringPath year = createString("year");

    public QAirkoreaStation(String variable) {
        super(AirkoreaStation.class, forVariable(variable));
    }

    public QAirkoreaStation(Path<? extends AirkoreaStation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAirkoreaStation(PathMetadata metadata) {
        super(AirkoreaStation.class, metadata);
    }

}

