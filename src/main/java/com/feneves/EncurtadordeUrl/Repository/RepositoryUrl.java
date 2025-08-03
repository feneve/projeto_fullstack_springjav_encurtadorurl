package com.feneves.EncurtadordeUrl.Repository;

import com.feneves.EncurtadordeUrl.Entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepositoryUrl extends JpaRepository<Url,Long> {
}
