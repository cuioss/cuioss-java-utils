package de.icw.util.formatting.support;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import de.icw.util.formatting.template.FormatterSupport;
import lombok.EqualsAndHashCode;
import lombok.Synchronized;
import lombok.ToString;

/**
 * PersonAddress consolidate several Address Objects by make a one-way
 * transformation.
 * <p/>
 * Supported Classes:
 * <ul>
 * </ul>
 * Mapping of properties will be done during construction. Properties which
 * can't be mapped will be initialize to null.
 */
@ToString
@EqualsAndHashCode
public class PersonAddress implements FormatterSupport, Serializable {

    private static final long serialVersionUID = -5846491132374853787L;

    private String street;

    private String postalCode;

    private String city;

    private String country;

    private String state;

    private String organization;

    private String line1;

    private String line2;

    private String flat;

    private String corpus;

    private String zipCodeExtension;

    private Map<String, Serializable> propertyValues;

    private static final List<String> SUPPORTED_PROP_LIST = new ImmutableList.Builder<String>().add("street")
            .add("postalCode").add("city").add("country").add("state").add("organization").add("line1").add("line2")
            .add("flat").add("corpus").add("zipCodeExtension").build();

    /**
     * provide default constructor
     */
    public PersonAddress() {
        propertyValues = retrieveAvailablePropertyValues();
    }

    /**
     * Use data from address object to copy this to PersonAddress.<br/>
     *
     * @param address
     *            com.icw.pxs.vmr.transfer.AddressDto must not be null.
     */
    public PersonAddress(final TransferAddressDto address) {
        checkNotNull(address, "AddressDto must not be null");
        street = address.getStreetLine();
        line1 = address.getOtherDesignation();
        postalCode = address.getPostalCode();
        city = address.getCity();
        country = address.getCountry();
        state = address.getRegion();
        propertyValues = retrieveAvailablePropertyValues();
    }

    /**
     * Use data from address object to copy this to PersonAddress.<br/>
     * Because this is just a stupid bean and AddressDto use CodeDto for some
     * properties they must be set separate. Otherwise CodeResolverService and
     * locale must be passed throw.
     *
     * @param address
     *            com.icw.ehf.core.transfer.AddressDto must not be null.
     */
    public PersonAddress(final AddressDto address) {
        checkNotNull(address, "AddressDto must not be null");
        street = address.getStreetAddressLine();
        postalCode = address.getPostalCode();
        city = address.getCity();
        // AddressDto use CodeDto for country
        // AddressDto use CodeDto for state
        organization = address.getOrganization();
        line1 = address.getLine1();
        line2 = address.getLine2();
        flat = address.getFlat();
        corpus = address.getCorpus();
        zipCodeExtension = address.getZipCodeExtension();
        propertyValues = retrieveAvailablePropertyValues();
    }

    @Synchronized
    private Map<String, Serializable> retrieveAvailablePropertyValues() {
        final Builder<String, Serializable> builder = ImmutableMap.builder();
        putIfNotNullOrEmpty(builder, "street", street);
        putIfNotNullOrEmpty(builder, "postalCode", postalCode);
        putIfNotNullOrEmpty(builder, "city", city);
        putIfNotNullOrEmpty(builder, "country", country);
        putIfNotNullOrEmpty(builder, "state", state);
        putIfNotNullOrEmpty(builder, "organization", organization);
        putIfNotNullOrEmpty(builder, "line1", line1);
        putIfNotNullOrEmpty(builder, "line2", line2);
        putIfNotNullOrEmpty(builder, "flat", flat);
        putIfNotNullOrEmpty(builder, "corpus", corpus);
        putIfNotNullOrEmpty(builder, "zipCodeExtension", zipCodeExtension);
        return builder.build();
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
        propertyValues = retrieveAvailablePropertyValues();
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
        propertyValues = retrieveAvailablePropertyValues();
    }

    @Synchronized
    @Override
    public Map<String, Serializable> getAvailablePropertyValues() {
        return propertyValues;
    }

    @Override
    public List<String> getSupportedPropertyNames() {
        return SUPPORTED_PROP_LIST;
    }

    private static void putIfNotNullOrEmpty(final Builder<String, Serializable> builder, final String key,
            final String value) {
        if (!isNullOrEmpty(value)) {
            builder.put(key, value);
        }
    }

}
