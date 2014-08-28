package se.inera.fmu.interfaces.managing.rest.dto;

import lombok.ToString;
import org.joda.time.DateTime;

/**
 * Created by Rasheed on 8/25/14.
 */
@ToString
public class EavropDTO {

    private String arendeId;

    private String utredningType;

    private DateTime creationTime;

    public EavropDTO() {
    }

    public EavropDTO(String arendeId, String utredningType, DateTime creationTime) {
        this.arendeId = arendeId;
        this.utredningType = utredningType;
        this.creationTime = creationTime;
    }

    public String getArendeId() {
        return arendeId;
    }

    public void setArendeId(String arendeId) {
        this.arendeId = arendeId;
    }

    public String getUtredningType() {
        return utredningType;
    }

    public void setUtredningType(String utredningType) {
        this.utredningType = utredningType;
    }

    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;
    }
}
