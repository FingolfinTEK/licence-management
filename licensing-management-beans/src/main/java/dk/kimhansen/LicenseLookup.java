package dk.kimhansen;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class LicenseLookup implements Serializable {

    private static final long serialVersionUID = 1L;

    public LicenseInformation lookup(final LicenseInformation licenseInformation) {
        // TODO implement lookup
        return licenseInformation;
    }

}
