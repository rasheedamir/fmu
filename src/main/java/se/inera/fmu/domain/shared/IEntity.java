package se.inera.fmu.domain.shared;

/**
 * Created by Rasheed on 7/6/14.
 *
 * An entity, as explained in the DDD book.
 */
public interface IEntity<T> {

    /**
     * Entities compare by identity, not by attributes.
     *
     * @param other The other entity.
     * @return true if the identities are the same, regardless of other attributes.
     */
    boolean sameIdentityAs(T other);

}
