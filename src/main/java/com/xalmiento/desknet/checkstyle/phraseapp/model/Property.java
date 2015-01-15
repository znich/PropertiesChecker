package com.xalmiento.desknet.checkstyle.phraseapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 15.01.2015 16:06
 *
 * @author a.laguta
 */
@XmlRootElement(name = "string")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property {

    @XmlAttribute(name = "name")
    private String key;

    @XmlValue
    private String value;

    public Property() {
    }

    public Property(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Property property = (Property) o;

        if (key != null ? !key.equals(property.key) : property.key != null) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (value != null ? !value.equals(property.value) : property.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
