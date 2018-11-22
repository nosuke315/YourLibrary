package com.example.baseball.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baseball.domain.Player;
import com.example.baseball.repository.PlayerRepository;

@Service
public class PlayerService {
    // ①
    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findOne(Long id) {
    	return playerRepository.findById(id).orElse(null);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public void delete(Long id) {
    	playerRepository.deleteById(id);
    }
}