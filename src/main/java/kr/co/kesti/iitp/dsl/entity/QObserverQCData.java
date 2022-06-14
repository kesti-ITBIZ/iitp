package kr.co.kesti.iitp.dsl.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.dsl.embed.QObserverDataKey;
import kr.co.kesti.iitp.dsl.embed.QObserverQCDataKey;
import kr.co.kesti.iitp.entity.ObserverData;
import kr.co.kesti.iitp.entity.ObserverQCData;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QObserverQCData is a Querydsl query type for ObserverQCData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QObserverQCData extends EntityPathBase<ObserverQCData> {

    private static final long serialVersionUID = -343024396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QObserverQCData observerQCData = new QObserverQCData("observerQCData");

    public final QObserverQCDataKey observerQCDataKey;

    public final NumberPath<Short> pm25_state_1 = createNumber("pm25_state_1", Short.class);

    public final NumberPath<Short> pm25_state_2 = createNumber("pm25_state_2", Short.class);

    public final NumberPath<Short> pm25_state_3 = createNumber("pm25_state_3", Short.class);

    public final NumberPath<Short> pm25_state_4 = createNumber("pm25_state_4", Short.class);

    public final NumberPath<Short> pm25_state_5 = createNumber("pm25_state_5", Short.class);

    public final NumberPath<Short> pm25_state = createNumber("pm25_state", Short.class);

    public final NumberPath<Float> pm25_median = createNumber("pm25_median", Float.class);

    public final NumberPath<Float> pm25_std = createNumber("pm25_std", Float.class);

    public final NumberPath<Float> pm25_std_rolling = createNumber("pm25_std_rolling", Float.class);

    public final NumberPath<Float> pm25_spatial_upper_bound = createNumber("pm25_spatial_upper_bound", Float.class);

    public final NumberPath<Float> pm25_spatial_lower_bound = createNumber("pm25_spatial_lower_bound", Float.class);

    public final StringPath pm25_isna = createString("pm25_isna");

    public final NumberPath<Float> pm25_qc = createNumber("pm25_qc", Float.class);

    public final NumberPath<Short> pm25_r_state_1 = createNumber("pm25_r_state_1", Short.class);

    public final NumberPath<Short> pm25_r_state_2 = createNumber("pm25_r_state_2", Short.class);

    public final NumberPath<Short> pm25_r_state_3 = createNumber("pm25_r_state_3", Short.class);

    public final NumberPath<Short> pm25_r_state_4 = createNumber("pm25_r_state_4", Short.class);

    public final NumberPath<Short> pm25_r_state_5 = createNumber("pm25_r_state_5", Short.class);

    public final NumberPath<Short> pm25_r_state = createNumber("pm25_r_state", Short.class);

    public final NumberPath<Float> pm25_r_median = createNumber("pm25_r_median", Float.class);

    public final NumberPath<Float> pm25_r_std = createNumber("pm25_r_std", Float.class);

    public final NumberPath<Float> pm25_r_std_rolling = createNumber("pm25_r_std_rolling", Float.class);

    public final NumberPath<Float> pm25_r_spatial_upper_bound = createNumber("pm25_r_spatial_upper_bound", Float.class);

    public final NumberPath<Float> pm25_r_spatial_lower_bound = createNumber("pm25_r_spatial_lower_bound", Float.class);

    public final StringPath pm25_r_isna = createString("pm25_r_isna");

    public final NumberPath<Float> pm25_r_qc = createNumber("pm25_r_qc", Float.class);

    public final NumberPath<Short> tmp_state_1 = createNumber("tmp_state_1", Short.class);

    public final NumberPath<Short> tmp_state_2 = createNumber("tmp_state_2", Short.class);

    public final NumberPath<Short> tmp_state_3 = createNumber("tmp_state_3", Short.class);

    public final NumberPath<Short> tmp_state_4 = createNumber("tmp_state_4", Short.class);

    public final NumberPath<Short> tmp_state_5 = createNumber("tmp_state_5", Short.class);

    public final NumberPath<Short> tmp_state = createNumber("tmp_state", Short.class);

    public final NumberPath<Float> tmp_median = createNumber("tmp_median", Float.class);

    public final NumberPath<Float> tmp_std = createNumber("tmp_std", Float.class);

    public final NumberPath<Float> tmp_std_rolling = createNumber("tmp_std_rolling", Float.class);

    public final NumberPath<Float> tmp_spatial_upper_bound = createNumber("tmp_spatial_upper_bound", Float.class);

    public final NumberPath<Float> tmp_spatial_lower_bound = createNumber("tmp_spatial_lower_bound", Float.class);

    public final StringPath tmp_isna = createString("tmp_isna");

    public final NumberPath<Float> tmp_qc= createNumber("tmp_qc", Float.class);

    public final NumberPath<Short> reh_state_1 = createNumber("reh_state_1", Short.class);

    public final NumberPath<Short> reh_state_2 = createNumber("reh_state_2", Short.class);

    public final NumberPath<Short> reh_state_3 = createNumber("reh_state_3", Short.class);

    public final NumberPath<Short> reh_state_4 = createNumber("reh_state_4", Short.class);

    public final NumberPath<Short> reh_state_5 = createNumber("reh_state_5", Short.class);

    public final NumberPath<Short> reh_state = createNumber("reh_state", Short.class);

    public final NumberPath<Float> reh_median = createNumber("reh_median", Float.class);

    public final NumberPath<Float> reh_std = createNumber("reh_std", Float.class);

    public final NumberPath<Float> reh_std_rolling = createNumber("reh_std_rolling", Float.class);

    public final NumberPath<Float> reh_spatial_upper_bound = createNumber("reh_spatial_upper_bound", Float.class);

    public final NumberPath<Float> reh_spatial_lower_bound = createNumber("reh_spatial_lower_bound", Float.class);

    public final StringPath reh_isna = createString("reh_isna");

    public final NumberPath<Float> reh_qc = createNumber("reh_qc", Float.class);

    public final NumberPath<Float> press = createNumber("press", Float.class);

    public QObserverQCData(String variable) {
        this(ObserverQCData.class, forVariable(variable), INITS);
    }

    public QObserverQCData(Path<? extends ObserverQCData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QObserverQCData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QObserverQCData(PathMetadata metadata, PathInits inits) {
        this(ObserverQCData.class, metadata, inits);
    }

    public QObserverQCData(Class<? extends ObserverQCData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.observerQCDataKey = inits.isInitialized("observerQCDataKey") ? new QObserverQCDataKey(forProperty("observerQCDataKey")) : null;
    }

}

