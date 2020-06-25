package com.social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.domain.FbConnections;

public interface FbConnectRepo extends JpaRepository<FbConnections, Long> {

}