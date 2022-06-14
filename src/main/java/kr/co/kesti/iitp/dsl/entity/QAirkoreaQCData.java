package kr.co.kesti.iitp.dsl.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.dsl.embed.QAirkoreaQCDataKey;
import kr.co.kesti.iitp.entity.AirkoreaQCData;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAirkoreaQCData is a Querydsl query type for AirkoreaQCData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAirkoreaQCData extends EntityPathBase<AirkoreaQCData> {

    private static final long serialVersionUID = 188537534L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAirkoreaQCData airkoreaQCData = new QAirkoreaQCData("airkoreaQCData");

    public final QAirkoreaQCDataKey airkoreaQCDataKey;

    public final NumberPath<Float> pm10 = createNumber("pm10", Float.class);

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final NumberPath<Short> pm10_state_1 = createNumber("pm10_state_1", Short.class);

    public final NumberPath<Short> pm25_state_1 = createNumber("pm25_state_1", Short.class);

    public final NumberPath<Short> pm10_state_2 = createNumber("pm10_state_2", Short.class);

    public final NumberPath<Short> pm25_state_2 = createNumber("pm25_state_2", Short.class);

    public final NumberPath<Short> pm10_state_3 = createNumber("pm10_state_3", Short.class);

    public final NumberPath<Short> pm25_state_3 = createNumber("pm25_state_3", Short.class);

    public final NumberPath<Short> pm10_state_4 = createNumber("pm10_state_4", Short.class);

    public final NumberPath<Short> pm25_state_4 = createNumber("pm25_state_4", Short.class);

    public final NumberPath<Float> pm10_median = createNumber("pm10_median", Float.class);

    public final NumberPath<Float> pm10_std = createNumber("pm10_std", Float.class);

    public final NumberPath<Float> pm25_median = createNumber("pm25_median", Float.class);

    public final NumberPath<Float> pm25_std = createNumber("pm25_std", Float.class);

    public final NumberPath<Float> pm10_std_rolling = createNumber("pm10_std_rolling", Float.class);

    public final NumberPath<Float> pm10_spatial_upper_bound = createNumber("pm10_spatial_upper_bound", Float.class);

    public final NumberPath<Float> pm10_spatial_lower_bound = createNumber("pm10_spatial_lower_bound", Float.class);

    public final NumberPath<Short> pm10_state_5 = createNumber("pm10_state_5", Short.class);

    public final NumberPath<Float> pm25_std_rolling = createNumber("pm25_std_rolling", Float.class);

    public final NumberPath<Float> pm25_spatial_upper_bound = createNumber("pm25_spatial_upper_bound", Float.class);

    public final NumberPath<Float> pm25_spatial_lower_bound = createNumber("pm25_spatial_lower_bound", Float.class);

    public final NumberPath<Short> pm25_state_5 = createNumber("pm25_state_5", Short.class);

    public final StringPath pm10_isna = createString("pm10_isna");

    public final NumberPath<Float> pm10_qc = createNumber("pm10_qc", Float.class);

    public final StringPath pm25_isna = createString("pm25_isna");

    public final NumberPath<Float> pm25_qc = createNumber("pm25_qc", Float.class);

    public final StringPath run_time = createString("run_time");

    public final NumberPath<Float> so2 = createNumber("so2", Float.class);

    public final NumberPath<Float> co = createNumber("so2", Float.class);

    public final NumberPath<Float> o3 = createNumber("so2", Float.class);

    public final NumberPath<Float> no2 = createNumber("so2", Float.class);

    public QAirkoreaQCData(String variable) {
        this(AirkoreaQCData.class, forVariable(variable), INITS);
    }

    public QAirkoreaQCData(Path<? extends AirkoreaQCData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAirkoreaQCData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAirkoreaQCData(PathMetadata metadata, PathInits inits) {
        this(AirkoreaQCData.class, metadata, inits);
    }

    public QAirkoreaQCData(Class<? extends AirkoreaQCData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.airkoreaQCDataKey = inits.isInitialized("airkoreaQCDataKey") ? new QAirkoreaQCDataKey(forProperty("airkoreaQCDataKey")) : null;
    }

}

