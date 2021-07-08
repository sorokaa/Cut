package com.cutter.project.dao;

import com.cutter.project.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Link findByOriginalLink(String originalLink);
    Link findByCuttedLink(String cuttedLink);

}
