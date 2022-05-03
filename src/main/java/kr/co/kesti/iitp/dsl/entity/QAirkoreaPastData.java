package kr.co.kesti.iitp.dsl.entity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.dsl.embed.QAirkoreaPastDataKey;
import kr.co.kesti.iitp.entity.AirkoreaPastData;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAirkoreaData is a Querydsl query type for AirkoreaData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAirkoreaPastData extends EntityPathBase<AirkoreaPastData> {

    private static final long serialVersionUID = 188537534L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAirkoreaPastData airkoreaPastData = new QAirkoreaPastData("airkoreaPastData");

    public final QAirkoreaPastDataKey airkoreaPastDataKey;

    public final StringPath region = createString("region");

    public final NumberPath<Float> so2 = createNumber("so2", Float.class);

    public final NumberPath<Float> co = createNumber("co", Float.class);

    public final NumberPath<Float> o3 = createNumber("o3", Float.class);

    public final NumberPath<Float> no2 = createNumber("no2", Float.class);

    public final NumberPath<Float> pm10 = createNumber("pm10", Float.class);

    public final NumberPath<Float> pm25 = createNumber("pm25", Float.class);

    public final StringPath mangNm = createString("mangNm");

    public QAirkoreaPastData(String variable) {
        this(AirkoreaPastData.class, forVariable(variable), INITS);
    }

    public QAirkoreaPastData(Path<? extends AirkoreaPastData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAirkoreaPastData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAirkoreaPastData(PathMetadata metadata, PathInits inits) {
        this(AirkoreaPastData.class, metadata, inits);
    }

    public QAirkoreaPastData(Class<? extends AirkoreaPastData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.airkoreaPastDataKey = inits.isInitialized("airkoreaPastDataKey") ? new QAirkoreaPastDataKey(forProperty("airkoreaPastDataKey")) : null;
    }

}

