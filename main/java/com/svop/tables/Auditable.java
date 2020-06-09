package com.svop.tables;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.time.LocalTime;

@MappedSuperclass
@Audited
    @EntityListeners(AuditingEntityListener.class)
    public abstract class Auditable<U> {

        @CreatedBy
        protected U createdBy;

        @CreatedDate
        protected LocalTime creationDate;

        @LastModifiedBy
        protected U lastModifiedBy;

        @LastModifiedDate
        protected LocalTime lastModifiedDate;

}
