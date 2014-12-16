package com.xalmiento.desknet.checkstyle.phraseapp.model;

import java.util.Arrays;

/**
 * Revision Info : $Author$ $Date$
 * Author : a.laguta
 * Created : 16.12.2014 10:13
 *
 * @author a.laguta
 */
public class Key {
   /*           id\": 1,\n"
            +"  \"name\": \"helper.label\",\n"
            +"  \"description\": \"Some description\",\n"
            +"  \"pluralized\": true|false,\n"
            +"  \"data_type\": \"string|number|boolean|array\",\n"
            +"  \"tag_list\": [\"mobile\", \"example-feature\"]*/

    private Long id;
    private String name;
    private String description;
    private  boolean  pluralized;
    private String data_type;
    private String[] tag_list;

    public Key() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPluralized() {
        return pluralized;
    }

    public void setPluralized(boolean pluralized) {
        this.pluralized = pluralized;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String[] getTag_list() {
        return tag_list;
    }

    public void setTag_list(String[] tag_list) {
        this.tag_list = tag_list;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Key key = (Key) o;

        if (pluralized != key.pluralized) {
            return false;
        }
        if (data_type != null ? !data_type.equals(key.data_type) : key.data_type != null) {
            return false;
        }
        if (description != null ? !description.equals(key.description) : key.description != null) {
            return false;
        }
        if (id != null ? !id.equals(key.id) : key.id != null) {
            return false;
        }
        if (name != null ? !name.equals(key.name) : key.name != null) {
            return false;
        }
        //noinspection RedundantIfStatement
        if (!Arrays.equals(tag_list, key.tag_list)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pluralized ? 1 : 0);
        result = 31 * result + (data_type != null ? data_type.hashCode() : 0);
        result = 31 * result + (tag_list != null ? Arrays.hashCode(tag_list) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pluralized=" + pluralized +
                ", data_type='" + data_type + '\'' +
                ", tag_list=" + Arrays.toString(tag_list) +
                '}';
    }
}
