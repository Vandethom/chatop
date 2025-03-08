package com.chatop.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}