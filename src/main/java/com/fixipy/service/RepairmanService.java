package com.fixipy.service;

import com.fixipy.entity.Repairman;
import com.fixipy.repository.RepairmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepairmanService {

    @Autowired
    private RepairmanRepository repairmanRepository;

    public Repairman registerRepairman(Repairman repairman) {
        return repairmanRepository.save(repairman);
    }
}
