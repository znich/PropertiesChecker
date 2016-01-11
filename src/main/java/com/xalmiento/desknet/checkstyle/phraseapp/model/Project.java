package com.xalmiento.desknet.checkstyle.phraseapp.model;

import java.io.Serializable;

/**
 * Created by Anatoly on 21.11.2015.
 */

/*{
        "id": "abcd1234cdef1234abcd1234cdef1234",
        "name": "My Android Project",
        "main_format": "xml",
        "created_at": "2015-01-28T09:52:53Z",
        "updated_at": "2015-01-28T09:52:53Z"
}*/
public class Project implements Serializable{

    private String id;
    private String name;
    private String main_format;

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

    public String getMain_format() {
        return main_format;
    }

    public void setMain_format(String main_format) {
        this.main_format = main_format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (main_format != null ? !main_format.equals(project.main_format) : project.main_format != null) return false;
        //noinspection RedundantIfStatement
        if (name != null ? !name.equals(project.name) : project.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (main_format != null ? main_format.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", main_format='" + main_format + '\'' +
                '}';
    }
}
