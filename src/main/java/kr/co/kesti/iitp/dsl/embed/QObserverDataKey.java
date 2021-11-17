package kr.co.kesti.iitp.dsl.embed;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.embed.ObserverDataKey;


/**
 * QObserverDataKey is a Querydsl query type for ObserverDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QObserverDataKey extends BeanPath<ObserverDataKey> {

    private static final long serialVersionUID = -221325261L;

    public static final QObserverDataKey observerDataKey = new QObserverDataKey("observerDataKey");

    public final DatePath<java.util.Date> dataDate = createDate("dataDate", java.util.Date.class);

    public final TimePath<java.util.Date> dataTime = createTime("dataTime", java.util.Date.class);

    public final StringPath stnSerial = createString("stnSerial");

    public QObserverDataKey(String variable) {
        super(ObserverDataKey.class, forVariable(variable));
    }

    public QObserverDataKey(Path<? extends ObserverDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QObserverDataKey(PathMetadata metadata) {
        super(ObserverDataKey.class, metadata);
    }

}

