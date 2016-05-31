package com.mossle.pim.persistence.manager;

import com.mossle.core.hibernate.HibernateEntityDao;

import com.mossle.pim.persistence.domain.PimScheduleParticipant;

import org.springframework.stereotype.Service;

@Service
public class PimScheduleParticipantManager extends
        HibernateEntityDao<PimScheduleParticipant> {
}
