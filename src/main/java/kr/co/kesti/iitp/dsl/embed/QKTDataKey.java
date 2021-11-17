package kr.co.kesti.iitp.dsl.embed;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.embed.KTDataKey;


/**
 * QKTDataKey is a Querydsl query type for KTDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QKTDataKey extends BeanPath<KTDataKey> {

    private static final long serialVersionUID = -1053702816L;

    public static final QKTDataKey kTDataKey = new QKTDataKey("kTDataKey");

    public final StringPath devId = createString("devId");

    public final DateTimePath<java.util.Date> equipDate = createDateTime("equipDate", java.util.Date.class);

    public QKTDataKey(String variable) {
        super(KTDataKey.class, forVariable(variable));
    }

    public QKTDataKey(Path<? extends KTDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKTDataKey(PathMetadata metadata) {
        super(KTDataKey.class, metadata);
    }

}

