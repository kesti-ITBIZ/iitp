package kr.co.kesti.iitp.dsl.embed;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.TimePath;
import kr.co.kesti.iitp.embed.ObserverQCDataKey;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QObserverQCDataKey is a Querydsl query type for ObserverQCDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QObserverQCDataKey extends BeanPath<ObserverQCDataKey> {

    private static final long serialVersionUID = -221325261L;

    public static final QObserverQCDataKey observerDataKey = new QObserverQCDataKey("observerQCDataKey");

    public final StringPath time = createString("time");

    public final StringPath stnId = createString("stnId");

    public QObserverQCDataKey(String variable) {
        super(ObserverQCDataKey.class, forVariable(variable));
    }

    public QObserverQCDataKey(Path<? extends ObserverQCDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObserverQCDataKey(PathMetadata metadata) {
        super(ObserverQCDataKey.class, metadata);
    }

}

