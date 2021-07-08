package com.cutter.project.model;

import com.sun.istack.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Entity
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String originalLink;

    @NotNull
    private String cuttedLink;

    private Long useCount;

    public Link() {}

    public Link(String originalLink, String cuttedLink) {
        this.originalLink = originalLink;
        this.cuttedLink = cuttedLink;
        this.useCount = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public String getCuttedLink() {
        return cuttedLink;
    }

    public void setCuttedLink(String cuttedLink) {
        this.cuttedLink = cuttedLink;
    }

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }
}
