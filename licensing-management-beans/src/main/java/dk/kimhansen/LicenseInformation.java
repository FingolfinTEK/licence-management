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

    public LicenseInformation(final String uniqueId, final String organizationName, final LicenseType licenseType, final boolean expire,
            final Date expireTimestamp, final String contactName, final String contactEmail) {
        this.uniqueId = uniqueId;
        this.organizationName = organizationName;
        this.licenseType = licenseType;
        this.expire = expire;
        this.expireTimestamp = expireTimestamp;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(uniqueId);
        builder.append("§");
        builder.append(organizationName);
        builder.append("§");
        builder.append(licenseType);
        builder.append("§");
        builder.append(expire);
        builder.append("§");
        builder.append(expireTimestamp == null ? null : expireTimestamp.getTime());
        builder.append("§");
        builder.append(contactName);
        builder.append("§");
        builder.append(contactEmail);
        return builder.toString();
    }

    public static LicenseInformation fromString(final String utf8String) {
        final String[] data = utf8String.split("§");
        Long timestamp = Long.parseLong(data[4]);
        Date expireTimestamp = timestamp == null ? null : new Date(timestamp);
        return new LicenseInformation(data[0], data[1], LicenseType.fromName(data[2]), Boolean.parseBoolean(data[3]), expireTimestamp,
                data[5], data[6]);
    }

}
