package kr.co.kesti.iitp.dsl.embed;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import kr.co.kesti.iitp.embed.SDoTDataKey;


/**
 * QSDoTDataKey is a Querydsl query type for SDoTDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QSDoTDataKey extends BeanPath<SDoTDataKey> {

    private static final long serialVersionUID = 1451754835L;

    public static final QSDoTDataKey sDoTDataKey = new QSDoTDataKey("sDoTDataKey");

    public final NumberPath<Short> div = createNumber("div", Short.class);

    public final StringPath modelSr = createString("modelSr");

    public final StringPath registTime = createString("registTime");

    public QSDoTDataKey(String variable) {
        super(SDoTDataKey.class, forVariable(variable));
    }

    public QSDoTDataKey(Path<? extends SDoTDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSDoTDataKey(PathMetadata metadata) {
        super(SDoTDataKey.class, metadata);
    }

}

