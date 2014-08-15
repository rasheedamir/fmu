package se.inera.fmu.domain.shared;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Base Entity
 *
 * Created by Rasheed on 7/17/14.
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //~ Instance fields ================================================================================================

    @Version
    @Column(name = "version")
    private Long version = 0L;

    //~ Property Methods ===============================================================================================

    public Long getVersion() {
        return version;
    }

    private void setVersion(final Long version){
        this.version = version;
    }

    //~ Common Methods =================================================================================================
}
