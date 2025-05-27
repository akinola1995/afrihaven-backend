package com.caremyhome.repository;

import com.caremyhome.model.Admin;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {}

