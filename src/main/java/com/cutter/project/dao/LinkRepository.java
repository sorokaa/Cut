package com.cutter.project.dao;

import com.cutter.project.model.Link;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {

    Link getLinkById(Long id);
    Link findByOriginalLink(String originalLink);
    Link findByCuttedLink(String cuttedLink);

}
