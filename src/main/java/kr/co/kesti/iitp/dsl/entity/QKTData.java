package kr.co.kesti.iitp.dsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import kr.co.kesti.iitp.dsl.embed.QKTDataKey;
import kr.co.kesti.iitp.entity.KTData;


/**
 * QKTData is a Querydsl query type for KTData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKTData extends EntityPathBase<KTData> {

    private static final long serialVersionUID = -1863404313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKTData kTData = new QKTData("kTData");

    public final NumberPath<Float> humidity = createNumber("humidity", Float.class);

    public final NumberPath<Float> pm10 = createNumber("pm10", Float.class);

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final QKTDataKey ktDataKey;

    public final NumberPath<Float> temperature = createNumber("temperature", Float.class);

    public QKTData(String variable) {
        this(KTData.class, forVariable(variable), INITS);
    }

    public QKTData(Path<? extends KTData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKTData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKTData(PathMetadata metadata, PathInits inits) {
        this(KTData.class, metadata, inits);
    }

    public QKTData(Class<? extends KTData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ktDataKey = inits.isInitialized("tbKtDataRawKey") ? new QKTDataKey(forProperty("tbKtDataRawKey")) : null;
    }

}

