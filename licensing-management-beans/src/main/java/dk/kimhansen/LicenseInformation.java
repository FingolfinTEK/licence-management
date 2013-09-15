package dk.kimhansen;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DateUtils;

public class LicenseInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uniqueId;
    private String organizationName;
    private LicenseType licenseType;
    private boolean expire;
    private Date expireTimestamp;
    private String contactName;
    private String contactEmail;

    public LicenseInformation() {
        licenseType = LicenseType.TRIAL;
        expire = true;
        expireTimestamp = getThirtyDaysFromNow();
    }

    private Date getThirtyDaysFromNow() {
        Calendar calendar = Calendar.getInstance();
        return DateUtils.addDays(calendar.getTime(), 30);
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(final LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setExpire(final boolean expire) {
        this.expire = expire;
    }

    public Date getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(final Date expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(uniqueId);
        hashCodeBuilder.append(organizationName);
        hashCodeBuilder.append(licenseType);
        hashCodeBuilder.append(expire);
        hashCodeBuilder.append(expireTimestamp);
        hashCodeBuilder.append(contactName);
        hashCodeBuilder.append(contactEmail);
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof LicenseInformation)) {
            return false;
        }
        LicenseInformation other = (LicenseInformation) obj;
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(uniqueId, other.uniqueId);
        equalsBuilder.append(organizationName, other.organizationName);
        equalsBuilder.append(licenseType, other.licenseType);
        equalsBuilder.append(expire, other.expire);
        equalsBuilder.append(expireTimestamp, other.expireTimestamp);
        equalsBuilder.append(contactName, other.contactName);
        equalsBuilder.append(contactEmail, other.contactEmail);
        return equalsBuilder.isEquals();
    }

}
