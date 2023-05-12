package bookmarks.lumen.data

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.id.IdentifierGenerator;

public class PositionSequenceGenerator extends SequenceStyleGenerator implements Configurable, IdentifierGenerator {

    private String sequenceCallSyntax;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        sequenceCallSyntax = "SELECT nextval(${params.getProperty("sequence-generator")})";
        super.configure(type, params, serviceRegistry);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);

        if (id != null && Integer.valueOf(id.toString()) > 0) {
            return id;
        } else {        
            Long seqValue = ((Number) Session.class.cast(s)
                .createSQLQuery(sequenceCallSyntax)
                .uniqueResult()).intValue();
            return seqValue;
        }
    }


}