package kr.co.kesti.iitp.dsl.embed;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.embed.AirkoreaDataKey;
import kr.co.kesti.iitp.embed.AirkoreaQCDataKey;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAirkoreaDataKey is a Querydsl query type for AirkoreaDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAirkoreaQCDataKey extends BeanPath<AirkoreaQCDataKey> {

    private static final long serialVersionUID = -4288983L;

    public static final QAirkoreaQCDataKey airkoreaQCDataKey = new QAirkoreaQCDataKey("airkoreaDataKey");

    public final StringPath time = createString("time");

    public final StringPath stnId = createString("stnId");

    public QAirkoreaQCDataKey(String variable) {
        super(AirkoreaQCDataKey.class, forVariable(variable));
    }

    public QAirkoreaQCDataKey(Path<? extends AirkoreaQCDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAirkoreaQCDataKey(PathMetadata metadata) {
        super(AirkoreaQCDataKey.class, metadata);
    }

}

