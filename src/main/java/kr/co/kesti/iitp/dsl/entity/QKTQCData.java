package kr.co.kesti.iitp.dsl.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.dsl.embed.QKTQCDataKey;
import kr.co.kesti.iitp.entity.KTQCData;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QKTQCData is a Querydsl query type for KTQCData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKTQCData extends EntityPathBase<KTQCData> {

    private static final long serialVersionUID = -1863404313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKTQCData kTQCData = new QKTQCData("kTQCData");

    public final QKTQCDataKey ktQCDataKey;

    public final NumberPath<Float> pm10 = createNumber("pm10", Float.class);

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final NumberPath<Float> tmp = createNumber("tmp", Float.class);

    public final NumberPath<Float> reh = createNumber("reh", Float.class);

    public final NumberPath<Short> pm10_state_1 = createNumber("pm10_state_1", Short.class);

    public final NumberPath<Short> pm25_state_1 = createNumber("pm25_state_1", Short.class);

    public final NumberPath<Short> tmp_state_1 = createNumber("tmp_state_1", Short.class);

    public final NumberPath<Short> reh_state_1 = createNumber("reh_state_1", Short.class);

    public final NumberPath<Short> pm10_state_2 = createNumber("pm10_state_2", Short.class);

    public final NumberPath<Short> pm25_state_2 = createNumber("pm25_state_2", Short.class);

    public final NumberPath<Short> tmp_state_2 = createNumber("tmp_state_2", Short.class);

    public final NumberPath<Short> reh_state_2 = createNumber("reh_state_2", Short.class);

    public final NumberPath<Short> pm10_state_3 = createNumber("pm10_state_3", Short.class);

    public final NumberPath<Short> pm25_state_3 = createNumber("pm25_state_3", Short.class);

    public final NumberPath<Short> tmp_state_3 = createNumber("tmp_state_3", Short.class);

    public final NumberPath<Short> reh_state_3 = createNumber("reh_state_3", Short.class);

    public final NumberPath<Short> pm10_state_4 = createNumber("pm10_state_4", Short.class);

    public final NumberPath<Short> pm25_state_4 = createNumber("pm25_state_4", Short.class);

    public final NumberPath<Short> tmp_state_4 = createNumber("tmp_state_4", Short.class);

    public final NumberPath<Short> reh_state_4 = createNumber("reh_state_4", Short.class);

    public final NumberPath<Float> pm10_median = createNumber("pm10_median", Float.class);

    public final NumberPath<Float> pm10_std = createNumber("pm10_std", Float.class);

    public final NumberPath<Float> pm25_median = createNumber("pm25_median", Float.class);

    public final NumberPath<Float> pm25_std = createNumber("pm25_std", Float.class);

    public final NumberPath<Float> tmp_median = createNumber("tmp_median", Float.class);

    public final NumberPath<Float> tmp_std = createNumber("tmp_std", Float.class);

    public final NumberPath<Float> reh_median = createNumber("reh_median", Float.class);

    public final NumberPath<Float> reh_std = createNumber("reh_std", Float.class);

    public final NumberPath<Float> pm10_std_rolling = createNumber("pm10_std_rolling", Float.class);

    public final NumberPath<Float> pm10_spatial_upper_bound = createNumber("pm10_spatial_upper_bound", Float.class);

    public final NumberPath<Float> pm10_spatial_lower_bound = createNumber("pm10_spatial_lower_bound", Float.class);

    public final NumberPath<Short> pm10_state_5 = createNumber("pm10_state_5", Short.class);

    public final NumberPath<Float> pm25_std_rolling = createNumber("pm25_std_rolling", Float.class);

    public final NumberPath<Float> pm25_spatial_upper_bound = createNumber("pm25_spatial_upper_bound", Float.class);

    public final NumberPath<Float> pm25_spatial_lower_bound = createNumber("pm25_spatial_lower_bound", Float.class);

    public final NumberPath<Short> pm25_state_5 = createNumber("pm25_state_5", Short.class);

    public final NumberPath<Float> tmp_std_rolling = createNumber("tmp_std_rolling", Float.class);

    public final NumberPath<Float> tmp_spatial_upper_bound = createNumber("tmp_spatial_upper_bound", Float.class);

    public final NumberPath<Float> tmp_spatial_lower_bound = createNumber("tmp_spatial_lower_bound", Float.class);

    public final NumberPath<Short> tmp_state_5 = createNumber("tmp_state_5", Short.class);

    public final NumberPath<Float> reh_std_rolling = createNumber("reh_std_rolling", Float.class);

    public final NumberPath<Float> reh_spatial_upper_bound = createNumber("reh_spatial_upper_bound", Float.class);

    public final NumberPath<Float> reh_spatial_lower_bound = createNumber("reh_spatial_lower_bound", Float.class);

    public final NumberPath<Short> reh_state_5 = createNumber("reh_state_5", Short.class);

    public final NumberPath<Float> pm10_state = createNumber("pm10_state", Float.class);

    public final NumberPath<Float> pm25_state = createNumber("pm25_state", Float.class);

    public final NumberPath<Float> tmp_state = createNumber("tmp_state", Float.class);

    public final NumberPath<Float> reh_state = createNumber("reh_state", Float.class);

    public final StringPath pm10_isna = createString("pm10_isna");

    public final NumberPath<Float> pm10_qc = createNumber("pm10_qc", Float.class);

    public final StringPath pm25_isna = createString("pm25_isna");

    public final NumberPath<Float> pm25_qc = createNumber("pm25_qc", Float.class);

    public final StringPath tmp_isna = createString("tmp_isna");

    public final NumberPath<Float> tmp_qc = createNumber("tmp_qc", Float.class);

    public final StringPath reh_isna = createString("reh_isna");

    public final NumberPath<Float> reh_qc = createNumber("reh_qc", Float.class);

    public QKTQCData(String variable) {
        this(KTQCData.class, forVariable(variable), INITS);
    }

    public QKTQCData(Path<? extends KTQCData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKTQCData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKTQCData(PathMetadata metadata, PathInits inits) {
        this(KTQCData.class, metadata, inits);
    }

    public QKTQCData(Class<? extends KTQCData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ktQCDataKey = inits.isInitialized("ktQCDataKey") ? new QKTQCDataKey(forProperty("ktQCDataKey")) : null;
    }

}

