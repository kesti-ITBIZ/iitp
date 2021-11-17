package kr.co.kesti.iitp.dsl.embed;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.embed.AirkoreaDataKey;


/**
 * QAirkoreaDataKey is a Querydsl query type for AirkoreaDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAirkoreaDataKey extends BeanPath<AirkoreaDataKey> {

    private static final long serialVersionUID = -4288983L;

    public static final QAirkoreaDataKey airkoreaDataKey = new QAirkoreaDataKey("airkoreaDataKey");

    public final StringPath stnNm = createString("stnNm");

    public final StringPath time = createString("time");

    public QAirkoreaDataKey(String variable) {
        super(AirkoreaDataKey.class, forVariable(variable));
    }

    public QAirkoreaDataKey(Path<? extends AirkoreaDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAirkoreaDataKey(PathMetadata metadata) {
        super(AirkoreaDataKey.class, metadata);
    }

}

