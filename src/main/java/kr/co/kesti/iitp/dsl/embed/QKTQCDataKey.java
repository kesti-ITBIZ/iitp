package kr.co.kesti.iitp.dsl.embed;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.embed.KTQCDataKey;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QKTQCDataKey is a Querydsl query type for KTQCDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QKTQCDataKey extends BeanPath<KTQCDataKey> {

    private static final long serialVersionUID = -1053702816L;

    public static final QKTQCDataKey kTQCDataKey = new QKTQCDataKey("kTQCDataKey");

    public final StringPath time = createString("time");

    public final StringPath stnId = createString("stnId");

    public QKTQCDataKey(String variable) {
        super(KTQCDataKey.class, forVariable(variable));
    }

    public QKTQCDataKey(Path<? extends KTQCDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKTQCDataKey(PathMetadata metadata) {
        super(KTQCDataKey.class, metadata);
    }

}

