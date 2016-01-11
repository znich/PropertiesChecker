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
            +"  \"plural\": true|false,\n"
            +"  \"data_type\": \"string|number|boolean|array\",\n"
            +"  \"tags\": [\"mobile\", \"example-feature\"]*/

    private String id;
    private String name;
    private String description;
    private  boolean plural;
    private String data_type;
    private String[] tags;

    public Key() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean isPlural() {
        return plural;
    }

    public void setPlural(boolean plural) {
        this.plural = plural;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean hasTag(String otherTag) {
        if (this.tags == null || otherTag == null) {
            return false;
        }
        for (String tag: tags) {
            if (tag.equals(otherTag)) {
                return true;
            }
        }
        return false;
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

        if (plural != key.plural) {
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
        if (!Arrays.equals(tags, key.tags)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (plural ? 1 : 0);
        result = 31 * result + (data_type != null ? data_type.hashCode() : 0);
        result = 31 * result + (tags != null ? Arrays.hashCode(tags) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Key{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", plural=" + plural +
                ", data_type='" + data_type + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
