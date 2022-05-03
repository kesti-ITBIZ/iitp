package kr.co.kesti.iitp.dsl.embed;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.StringPath;
import kr.co.kesti.iitp.embed.AirkoreaPastDataKey;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAirkoreaPastDataKey is a Querydsl query type for AirkoreaPastDataKey
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QAirkoreaPastDataKey extends BeanPath<AirkoreaPastDataKey> {

    private static final long serialVersionUID = -4288983L;

    public static final QAirkoreaPastDataKey airkoreaDataKey = new QAirkoreaPastDataKey("airkoreaPastDataKey");

    public final StringPath stnCode = createString("stnCode");

    public final StringPath stnNm = createString("stnNm");

    public final StringPath dataDatetime = createString("dataDatetime");

    public QAirkoreaPastDataKey(String variable) {
        super(AirkoreaPastDataKey.class, forVariable(variable));
    }

    public QAirkoreaPastDataKey(Path<? extends AirkoreaPastDataKey> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAirkoreaPastDataKey(PathMetadata metadata) {
        super(AirkoreaPastDataKey.class, metadata);
    }

}

