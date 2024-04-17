/**
 * 
 */
package com.project.manage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.manage.Model.MstUserModel;

/**
 * 
 */
@Repository
public interface MstUserRepository extends JpaRepository<MstUserModel, Long> {

}
