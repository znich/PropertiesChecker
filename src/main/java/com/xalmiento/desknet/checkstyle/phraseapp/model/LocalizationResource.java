package com.xalmiento.desknet.checkstyle.phraseapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.01.2015 15:52
 *
 * @author a.laguta
 */
@XmlRootElement(name = "resources")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalizationResource {

    @XmlElement(name="string")
    private List<Property> properties;

    public LocalizationResource() {
    }

    public LocalizationResource(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalizationResource that = (LocalizationResource) o;

        //noinspection RedundantIfStatement
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return properties != null ? properties.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "LocalizationResource{" +
                "properties=" + properties +
                '}';
    }
}
