package kr.co.kesti.iitp.dsl.embed;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.embed.SDoTQCDataKey;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSDoTQCDataKey is a Querydsl query type for SDoTQCDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSDoTQCDataKey extends BeanPath<SDoTQCDataKey> {

    private static final long serialVersionUID = 1451754835L;

    public static final QSDoTQCDataKey sDoTQCDataKey = new QSDoTQCDataKey("sDoTQCDataKey");

    public final StringPath time = createString("time");

    public final StringPath stnId = createString("stnId");

    public QSDoTQCDataKey(String variable) {
        super(SDoTQCDataKey.class, forVariable(variable));
    }

    public QSDoTQCDataKey(Path<? extends SDoTQCDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSDoTQCDataKey(PathMetadata metadata) {
        super(SDoTQCDataKey.class, metadata);
    }

}

